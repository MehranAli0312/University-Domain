package com.example.genesis.di

import com.example.genesis.repositories.ApiRepository
import com.example.genesis.viewmodel.CountryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val countryModule = module {
    factory { ApiRepository(get()) }
    viewModel { CountryViewModel(get()) }
}