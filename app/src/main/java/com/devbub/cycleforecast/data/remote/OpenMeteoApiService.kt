package com.devbub.cycleforecast.data.remote

import com.devbub.cycleforecast.data.remote.dto.OpenMeteoGeoResponse
import com.devbub.cycleforecast.data.remote.dto.OpenMeteoWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {

    @GET("v1/forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m,precipitation_probability",
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max,wind_speed_10m_max",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 7
    ): OpenMeteoWeatherResponse
}

interface OpenMeteoGeocodingService {

    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 5,
        @Query("language") language: String = "en",
        @Query("format") format: String = "json"
    ): OpenMeteoGeoResponse
}

