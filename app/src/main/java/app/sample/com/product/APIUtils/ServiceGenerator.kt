package app.sample.com.product.APIUtils

import java.io.IOException

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by arunprasad on 3/14/19.
 */

object ServiceGenerator {
    val API_DEFAULT_PAGE_KEY = 1
    private val BASE_URL = "https://jl-nonprod-syst.apigee.net/"
    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    internal var retrofit = builder.build()

    private val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val httpClient = OkHttpClient.Builder()

    private val apiKeyInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url()
        val url = originalHttpUrl.newBuilder()
                .build()
        val request = originalRequest.newBuilder().url(url).build()
        chain.proceed(request)
    }


    fun <S> createService(serviceClass: Class<S>): S {
        if (!httpClient.interceptors().contains(logging)) {
            //httpClient.addInterceptor(logging);
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        if (!httpClient.interceptors().contains(apiKeyInterceptor)) {
            httpClient.addInterceptor(apiKeyInterceptor)
            builder.client(httpClient.build())
            retrofit = builder.build()
        }
        return retrofit.create(serviceClass)
    }

}
