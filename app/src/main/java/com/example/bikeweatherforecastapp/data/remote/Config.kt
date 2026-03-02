package com.example.bikeweatherforecastapp.data.remote

import com.example.bikeweatherforecastapp.BuildConfig
object Config {
    // BuildConfig.API_KEY is generated at build time, so keep this as val (not const).
    val OPENWEATHER_API_KEY = BuildConfig.API_KEY

    // Legacy OpenWeatherMap URLs (kept for reference/fallback)
    const val BASE_URL = "https://api.openweathermap.org/"
    const val WEATHER_ICON_BASE_URL = "https://openweathermap.org/img/wn/"

    // Open-Meteo API URLs (free, no API key required)
    const val OPEN_METEO_BASE_URL = "https://api.open-meteo.com/"
    const val OPEN_METEO_GEOCODING_URL = "https://geocoding-api.open-meteo.com/"
}