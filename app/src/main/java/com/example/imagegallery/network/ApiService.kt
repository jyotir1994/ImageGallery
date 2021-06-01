package com.example.imagegallery.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

interface ApiService {
    companion object {
        operator fun invoke(): ApiEndPoints {
            return createEndPoints()
        }

        private fun getRetrofit(): Retrofit {
            Timber.d("getRetrofit()")

            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

            val interceptor = HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .client(okHttpClient)
                .addConverterFactory(
                    MoshiConverterFactory.create(moshi)
                )
                .baseUrl(Url.BASE_URL)
                .build()
        }

        fun createEndPoints(): ApiEndPoints {
            val retrofitCall = getRetrofit()
            return retrofitCall.create(ApiEndPoints::class.java)
        }
    }
}