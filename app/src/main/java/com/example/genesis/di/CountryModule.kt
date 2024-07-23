package com.example.genesis.di

import com.example.genesis.repositories.ApiRepository
import com.example.genesis.viewmodel.CountryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// Define a Koin module for dependency injection
val countryModule = module {
    // Define a factory for creating ApiRepository instances
    // The `get()` function is used to resolve dependencies required by ApiRepository
    factory { ApiRepository(get()) }

    // Define a ViewModel for the CountryViewModel
    // The `get()` function is used to resolve dependencies required by CountryViewModel
    viewModel { CountryViewModel(get()) }
}
