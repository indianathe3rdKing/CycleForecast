package com.devbub.cycleforecast.domain.repository

import com.devbub.cycleforecast.domain.model.WeatherResponse

interface WeatherRepository {
    suspend fun getWeatherForecast(lat: Double, lon: Double): Result<WeatherResponse>
}