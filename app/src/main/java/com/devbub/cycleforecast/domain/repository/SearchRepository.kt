package com.devbub.cycleforecast.domain.repository

import com.devbub.cycleforecast.domain.model.CityLocation

interface SearchRepository {
    suspend fun searchCity(city: String): CityLocation?
}