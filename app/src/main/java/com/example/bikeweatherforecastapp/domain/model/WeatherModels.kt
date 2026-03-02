package com.example.bikeweatherforecastapp.domain.model


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val city: City,
    val list: List<WeatherItem>,
    val daily: List<Forecast> = emptyList()
)

data class GeoResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String
)

data class City(
    val id: Int,
    val name: String,
    val country: String,
    val coord: Coordinates
)

data class Coordinates(
    val lat: Double,
    val lon: Double
)

data class CityLocation(
    val name: String,
    val country: String,
    val coordinates: Coordinates
)

data class WeatherItem(
    @SerializedName("dt")
    val date: Long,
    val main: MainWeather,
    val weather: List<Weather>,
    val wind: Wind,
    @SerializedName("pop")
    val precipitationPredictability: Double,
    val visibility: Double = 10000.0 // Default 10km visibility in meters
)

data class MainWeather(
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val humidity: Int
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Forecast(
    val date: Long,
    val temperature: Temperature,
    val weather: List<Weather>,
    val humidity: Int,
    val windSpeed: Double,
    val precipitationPredictability: Double,
    val visibility: Double = 10000.0 // Default 10km visibility in meters
)

data class Temperature(
    val min: Double,
    val max: Double
)

data class WeatherState(
    val isLoading: Boolean = false,
    val weatherData: WeatherResponse? = null,
    val hourlyForecasts: List<Forecast> = emptyList(),
    val error: String? = null,
    val isMetric: Boolean = true,
    val selectedDay: Boolean?=false
)

