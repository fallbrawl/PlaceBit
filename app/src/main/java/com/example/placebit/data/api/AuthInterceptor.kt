package com.example.placebit.data.api

import com.example.placebit.BuildConfig
import com.example.placebit.util.Logs
import com.example.placebit.util.Utils
import com.google.common.net.HttpHeaders.REFRESH
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        val builder = request.newBuilder()
        builder.header("Accept", "application/json")

        if (UserPrefs.authToken.isNotEmpty())
            setAuthHeader(builder, UserPrefs.authToken)

        val newRequest = builder.build()

        var response = chain.proceed(newRequest)

        Logs.d(" MY INTERUPTER response.code() " + response.code())

        if (response.code() == 401 && UserPrefs.userId!=0.toLong() ) {
            synchronized(this) {

                val code = refreshToken() / 100

                if (code != 2) {
                    if (code == 4 || code == 5) {

                        Logs.d("Logout")
                        return response
                    }
                }


                if (UserPrefs.authToken.isNotEmpty()){
                    setAuthHeader(builder, UserPrefs.authToken)
                    request = builder.build()

                    return chain.proceed(request)
                }
            }
        }
        return response

    }

    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        if (token != null) {
            builder.header("Authorization", String.format("Bearer %s", token))
            Logs.d(String.format("Bearer %s", token))
        }
    }

    private fun refreshToken(): Int {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val jsonType = MediaType.parse("application/json; charset=utf-8")
        val oAuth = OAuth(REFRESH)
        if (UserPrefs.userId != 0.toLong())
            oAuth.setRefreshToken(UserPrefs.refreshToken)
        val json = Utils.serialize(oAuth)
        val body = RequestBody.create(jsonType, json.toString())
        val request = Request.Builder()
            .url("${BuildConfig.BASE_URL}oauth/refresh-token")
            .post(body)
            .build()

        var response: Response? = null
        var code = 0

        try {
            response = client.newCall(request).execute()

            if (response != null) {
                code = response.code()
                Logs.d("refreshToken code response Code $code")
                when (code) {
                    200 -> {
                        val authToken = GsonBuilder().create()
                            .fromJson(response.body()!!.string(), AppResponse::class.java)
                        UserPrefs.authToken = authToken.accessToken
                        UserPrefs.refreshToken = authToken.refreshToken
                    }
                }
                response.body()!!.close()
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return code
    }
}