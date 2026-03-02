package com.example.bikeweatherforecastapp.data.repository

import com.example.bikeweatherforecastapp.data.remote.OpenMeteoApiService
import com.example.bikeweatherforecastapp.data.remote.mapper.OpenMeteoMapper
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.domain.repository.WeatherRepository

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