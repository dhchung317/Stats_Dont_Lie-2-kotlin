package com.example.statsdontlie.network

import com.example.statsdontlie.constants.BDLAppConstants
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitSingleton {
    private var singleInstance: Retrofit? = null
        private get() {
            if (field == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                val client = OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.SECONDS)
                        .readTimeout(100, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(interceptor).build()
                field = Retrofit.Builder()
                        .baseUrl(BDLAppConstants.BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .build()
            }
            return field
        }
    var singleService: BDLService? = null
        get() {
            if (field == null) {
                field = singleInstance!!.create(BDLService::class.java)
            }
            return field
        }
        private set

}