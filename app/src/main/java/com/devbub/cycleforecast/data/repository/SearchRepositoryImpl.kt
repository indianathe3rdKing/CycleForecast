package com.devbub.cycleforecast.data.repository

import android.util.Log
import com.devbub.cycleforecast.data.remote.OpenMeteoGeocodingService
import com.devbub.cycleforecast.domain.model.CityLocation
import com.devbub.cycleforecast.domain.model.Coordinates
import com.devbub.cycleforecast.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val geocodingService: OpenMeteoGeocodingService
) : SearchRepository {
    override suspend fun searchCity(city: String): CityLocation? {
        return try {
            Log.d(TAG, "Searching for city: $city")
            val response = geocodingService.searchCity(name = city)
            Log.d(TAG, "Geo API response: $response")

            val firstResult = response.results?.firstOrNull()

            firstResult?.let {
                Log.d(TAG, "Found: ${it.name}, ${it.country} at ${it.latitude}, ${it.longitude}")
                CityLocation(
                    name = it.name,
                    country = it.country ?: "",
                    coordinates = Coordinates(
                        lat = it.latitude,
                        lon = it.longitude
                    )
                )
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error searching city: ${e.message}", e)
            null
        }
    }

    companion object {
        private const val TAG = "SearchRepositoryImpl"
    }
}