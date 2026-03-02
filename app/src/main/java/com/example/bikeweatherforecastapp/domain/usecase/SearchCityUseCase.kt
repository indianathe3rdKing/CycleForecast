package com.example.bikeweatherforecastapp.domain.usecase

import com.example.bikeweatherforecastapp.domain.model.CityLocation
import com.example.bikeweatherforecastapp.domain.repository.SearchRepository

class SearchCityUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(city: String): CityLocation? = repository.searchCity(city)
}