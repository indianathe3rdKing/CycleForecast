package com.example.bikeweatherforecastapp.di

import com.example.bikeweatherforecastapp.data.local.DataStoreManager
import com.example.bikeweatherforecastapp.data.remote.Config
import com.example.bikeweatherforecastapp.data.remote.OpenMeteoApiService
import com.example.bikeweatherforecastapp.data.remote.OpenMeteoGeocodingService
import com.example.bikeweatherforecastapp.data.repository.SearchRepositoryImpl
import com.example.bikeweatherforecastapp.data.repository.WeatherRepositoryImpl
import com.example.bikeweatherforecastapp.domain.repository.SearchRepository
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository
import com.example.bikeweatherforecastapp.domain.usecase.CalculateBikeRidingScoreUseCase
import com.example.bikeweatherforecastapp.domain.usecase.GetWeatherUseCase
import com.example.bikeweatherforecastapp.domain.usecase.SearchCityUseCase
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val appModule = module {

    // DataStore
    single { DataStoreManager(get()) }

    // Open-Meteo Weather API Retrofit instance
    single(named("openMeteo")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.OPEN_METEO_BASE_URL)
            .build()
    }

    // Open-Meteo Geocoding API Retrofit instance
    single(named("openMeteoGeocoding")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Config.OPEN_METEO_GEOCODING_URL)
            .build()
    }

    // Open-Meteo API Services
    single { get<Retrofit>(named("openMeteo")).create(OpenMeteoApiService::class.java) }
    single { get<Retrofit>(named("openMeteoGeocoding")).create(OpenMeteoGeocodingService::class.java) }

    single<WeatherRepository> {
        WeatherRepositoryImpl(get())
    }

    single {
        GetWeatherUseCase(get())
    }

    single {
        CalculateBikeRidingScoreUseCase()
    }

    viewModel { WeatherViewModel(get(), get(), get(), get(), get()) }

    single<SearchRepository> { SearchRepositoryImpl(get()) }

    factory { SearchCityUseCase(get()) }
}