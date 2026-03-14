package com.devbub.cycleforecast.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Open-Meteo Weather API Response DTOs
 */
data class OpenMeteoWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    val elevation: Double,
    @SerializedName("hourly_units")
    val hourlyUnits: HourlyUnits?,
    val hourly: HourlyData?,
    @SerializedName("daily_units")
    val dailyUnits: DailyUnits?,
    val daily: DailyData?
)

data class HourlyUnits(
    val time: String,
    @SerializedName("temperature_2m")
    val temperature2m: String,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: String,
    @SerializedName("weather_code")
    val weatherCode: String,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: String,
    @SerializedName("precipitation_probability")
    val precipitationProbability: String
)

data class HourlyData(
    val time: List<String>,
    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,
    @SerializedName("relative_humidity_2m")
    val relativeHumidity2m: List<Int>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
    @SerializedName("wind_speed_10m")
    val windSpeed10m: List<Double>,
    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>
)

data class DailyUnits(
    val time: String,
    @SerializedName("weather_code")
    val weatherCode: String,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: String,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: String,
    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: String,
    @SerializedName("wind_speed_10m_max")
    val windSpeed10mMax: String
)

data class DailyData(
    val time: List<String>,
    @SerializedName("weather_code")
    val weatherCode: List<Int>,
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>,
    @SerializedName("wind_speed_10m_max")
    val windSpeed10mMax: List<Double>
)

/**
 * Open-Meteo Geocoding API Response DTOs
 */
data class OpenMeteoGeoResponse(
    val results: List<GeocodingResult>?
)

data class GeocodingResult(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val elevation: Double?,
    val country: String?,
    @SerializedName("country_code")
    val countryCode: String?,
    val timezone: String?,
    @SerializedName("admin1")
    val admin1: String?, // State/Province
    @SerializedName("admin2")
    val admin2: String?, // County/District
    @SerializedName("admin3")
    val admin3: String?  // City/Town
)

