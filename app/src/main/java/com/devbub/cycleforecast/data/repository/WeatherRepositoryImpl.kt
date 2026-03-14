package com.devbub.cycleforecast.data.repository

import com.devbub.cycleforecast.data.remote.OpenMeteoApiService
import com.devbub.cycleforecast.data.remote.mapper.OpenMeteoMapper
import com.devbub.cycleforecast.domain.model.WeatherResponse
import com.devbub.cycleforecast.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val openMeteoApiService: OpenMeteoApiService
) : WeatherRepository {

    override suspend fun getWeatherForecast(
        lat: Double,
        lon: Double
    ): Result<WeatherResponse> {
        return try {
            val response = openMeteoApiService.getWeatherForecast(
                latitude = lat,
                longitude = lon
            )
            val weatherResponse = OpenMeteoMapper.mapToWeatherResponse(response)
            Result.success(weatherResponse)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}