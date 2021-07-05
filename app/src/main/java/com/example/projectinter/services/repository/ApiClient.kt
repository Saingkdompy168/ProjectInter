package com.chipmong.dms.services.repository

import android.os.Build
import com.chipmong.dms.utils.ServerConfig
import com.example.projectinter.BuildConfig
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiClient {
    private var retrofit: Retrofit? = null
//    private const val TIME_OUT: Long = 120
    private const val TIME_OUT: Long = 60
//    private const val TIME_OUT_CONNECT: Long = 60
    private const val TIME_OUT_CONNECT: Long = 30

    private var okHttpClient: OkHttpClient? = null

    /**
     * @param baseUrl url
     */
    fun getClient(baseUrl: String): Retrofit {
//        if (okHttpClient == null) {
//            initOkHttp("")
//        }
        if (retrofit == null) {
            retrofit =
                createRetrofit(baseUrl)
        } else {
            if (!baseUrl.equals(retrofit!!.baseUrl().host(), ignoreCase = true)) {
                retrofit =
                    createRetrofit(baseUrl)
            }
        }
        return retrofit!!
    }

    /**
     * Use for whole project with base url
     */
    fun getDefaultClient(): Retrofit {
        return getClient("")
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient!!)
            .build()
    }

    //Initial when app open
    fun initOkHttp(deviceId : String) {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(TIME_OUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        httpClient.hostnameVerifier { hostname, session -> true }

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            httpClient.addInterceptor(interceptor)
        }


        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val connection = chain.connection()
            connection?.socket()?.keepAlive = true
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Request-Type", "Android")
                .addHeader("Content-Type", "application/json")
                .addHeader("connection", "keep-alive")
                .addHeader("x-app-version", BuildConfig.VERSION_NAME)
                .addHeader("x-os-version", "${Build.VERSION.SDK_INT}")
                .addHeader("x-timezone", TimeZone.getDefault().id)
                .addHeader("x-platform" , "ANDROID")
                .addHeader("x-udid" , deviceId)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
    }
}