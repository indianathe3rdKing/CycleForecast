package com.example.bikeweatherforecastapp.domain.repository

import com.example.bikeweatherforecastapp.domain.model.CityLocation

interface SearchRepository {
    suspend fun searchCity(city: String): CityLocation?
}