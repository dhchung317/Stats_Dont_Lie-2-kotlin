package com.hyunki.statsdontlie2.di

import com.hyunki.statsdontlie2.constants.NetworkConstants
import com.hyunki.statsdontlie2.localdb.BDLDatabase
import com.hyunki.statsdontlie2.localdb.BDLDatabaseRepository
import com.hyunki.statsdontlie2.localdb.BDLDatabaseRepositoryImpl
import com.hyunki.statsdontlie2.network.BDLService
import com.hyunki.statsdontlie2.repository.Repository
import com.hyunki.statsdontlie2.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor).build()
        return Retrofit.Builder()
                .baseUrl(NetworkConstants.BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): BDLService {
        return retrofit.create(BDLService::class.java)
    }

    @Provides
    @Singleton
    fun provideBDLDatabaseRepository(database: BDLDatabase): BDLDatabaseRepository {
        return BDLDatabaseRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideRepository(service: BDLService): Repository {
        return RepositoryImpl(service)
    }
}