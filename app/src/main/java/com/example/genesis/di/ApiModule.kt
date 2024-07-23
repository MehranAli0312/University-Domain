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

// Constants for the base URL and network timeout
const val baseUrl = BASE_URL
const val networkTime = NETWORK_TIMEOUT

// Function to provide an OkHttpClient instance with logging interceptors
fun provideOkHttpClient(): OkHttpClient {

    // Create a logging interceptor for HTTP requests and responses
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS) // Log headers
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY) // Log body

    // Build and return the OkHttpClient instance with the logging interceptor
    return OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

// Function to provide a Retrofit instance with the given base URL and OkHttpClient
fun provideRetrofit(baseUrl: String, client: OkHttpClient): ApiServices =
    Retrofit.Builder()
        .baseUrl(baseUrl) // Set the base URL for the API
        .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON conversion
        .client(client) // Use the provided OkHttpClient instance
        .build()
        .create(ApiServices::class.java) // Create and return the ApiServices implementation

// Koin module for providing network-related dependencies
val apiModule = module {
    single { baseUrl } // Provide the base URL as a singleton
    single { networkTime } // Provide the network timeout as a singleton
    single { provideOkHttpClient() } // Provide the OkHttpClient as a singleton
    single { provideRetrofit(get(), get()) } // Provide the Retrofit instance as a singleton
}

