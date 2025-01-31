package com.freez.cats.di

import com.freez.cat.core.util.Constant
import com.freez.cat.data.remote.theCatApi.CatApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val newUrl = originalRequest.url.newBuilder()
                .build()
            val newRequest = originalRequest.newBuilder().url(newUrl)
                .addHeader("x-api-key", Constant.THE_CAT_API_KEY)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(newRequest)
        }.addInterceptor(logger).build()

        return Retrofit.Builder()
            .baseUrl(Constant.THE_CAT_API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): CatApiService {
        return retrofit.create(CatApiService::class.java)
    }
}
