package com.devbub.cycleforecast.di

import com.devbub.cycleforecast.data.local.DataStoreManager
import com.devbub.cycleforecast.data.remote.Config
import com.devbub.cycleforecast.data.remote.OpenMeteoApiService
import com.devbub.cycleforecast.data.remote.OpenMeteoGeocodingService
import com.devbub.cycleforecast.data.repository.SearchRepositoryImpl
import com.devbub.cycleforecast.data.repository.WeatherRepositoryImpl
import com.devbub.cycleforecast.domain.repository.SearchRepository
import com.devbub.cycleforecast.domain.repository.WeatherRepository
import com.devbub.cycleforecast.domain.usecase.CalculateBikeRidingScoreUseCase
import com.devbub.cycleforecast.domain.usecase.GetWeatherUseCase
import com.devbub.cycleforecast.domain.usecase.SearchCityUseCase
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel
import org.koin.core.module.dsl.viewModel
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