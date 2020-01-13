package com.example.placebit.util

import android.content.Context
import android.net.ConnectivityManager
import com.example.placebit.R
import com.example.placebit.data.api.ErrorResponse
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

object Utils {

    fun serialize(`object`: Any): JsonObject {
        val builder = GsonBuilder().addSerializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.getAnnotation(Serialization.Exclude::class.java) != null
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        })

        return builder.create().toJsonTree(`object`).asJsonObject
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getErrorMessage(error: Throwable, context: Context): String? {
        var message: String? = ""
        if (!isNetworkAvailable(context)) {
            return context.getString(R.string.no_internet_connection)
        }

        try {
            when (error) {
                is HttpException -> {
                    val errorCode = error.code()
                    message = error.response()?.errorBody()!!.string()
                    val response = GsonBuilder().create()
                        .fromJson<ErrorResponse>(message, ErrorResponse::class.java)
                    message = response.errors!![0].errors!![0]
                }
                is SocketTimeoutException -> message = "Timeout"
            }
        } catch (e: ClassCastException) {
            e.printStackTrace()
            Logs.d("catch error:$e")
            return message
        }
        return message
    }

}