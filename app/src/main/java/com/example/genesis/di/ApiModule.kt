package com.example.genesis.di

import com.example.genesis.api.ApiServices
import com.example.genesis.utils.Constants.BASE_URL
import com.example.genesis.utils.Constants.NETWORK_TIMEOUT
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val baseUrl = BASE_URL

const val networkTime = NETWORK_TIMEOUT


fun provideOkHttpClient(): OkHttpClient {

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}


fun provideRetrofit(baseUrl: String, client: OkHttpClient): ApiServices =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(ApiServices::class.java)


val apiModule = module {
    single { baseUrl }
    single { networkTime }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
}
