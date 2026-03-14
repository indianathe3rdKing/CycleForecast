package com.devbub.cycleforecast.domain.usecase

import com.devbub.cycleforecast.domain.model.CityLocation
import com.devbub.cycleforecast.domain.repository.SearchRepository

class SearchCityUseCase(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(city: String): CityLocation? = repository.searchCity(city)
}