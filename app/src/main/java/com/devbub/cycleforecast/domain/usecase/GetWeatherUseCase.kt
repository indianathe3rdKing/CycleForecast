package com.devbub.cycleforecast.domain.usecase

import com.devbub.cycleforecast.domain.model.WeatherResponse
import com.devbub.cycleforecast.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double,lon: Double): Result<WeatherResponse>{
        return repository.getWeatherForecast(lat,lon)
    }
}