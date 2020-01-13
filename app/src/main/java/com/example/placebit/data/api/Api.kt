import com.example.placebit.data.api.AuthInterceptor
import com.example.placebit.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

object Api {

    private const val baseUrl = BuildConfig.BASE_URL

    private var retrofitClient by Delegates.notNull<Retrofit>()

    init {

        val file = File("/cache/")
        val size: Long = 1024 * 1024
        val store = Cache(file, size)

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor())
            .addInterceptor(loggingInterceptor)
            .cache(store)
            .build()

        retrofitClient = Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val client: Retrofit
        get() {
            return retrofitClient
        }
}