package com.example.bikeweatherforecastapp.data.remote.mapper

import com.example.bikeweatherforecastapp.data.remote.dto.OpenMeteoWeatherResponse
import com.example.bikeweatherforecastapp.domain.model.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Maps Open-Meteo weather codes to our Weather domain model
 * See: https://open-meteo.com/en/docs#weathervariables
 */
object WeatherCodeMapper {

    fun mapToWeather(code: Int): Weather {
        return when (code) {
            0 -> Weather(800, "Clear", "clear sky", "01d")
            1 -> Weather(801, "Clouds", "mainly clear", "02d")
            2 -> Weather(802, "Clouds", "partly cloudy", "03d")
            3 -> Weather(803, "Clouds", "overcast", "04d")
            45, 48 -> Weather(741, "Fog", "fog", "50d")
            51 -> Weather(300, "Drizzle", "light drizzle", "09d")
            53 -> Weather(301, "Drizzle", "moderate drizzle", "09d")
            55 -> Weather(302, "Drizzle", "dense drizzle", "09d")
            56, 57 -> Weather(311, "Drizzle", "freezing drizzle", "09d")
            61 -> Weather(500, "Rain", "slight rain", "10d")
            63 -> Weather(501, "Rain", "moderate rain", "10d")
            65 -> Weather(502, "Rain", "heavy rain", "10d")
            66, 67 -> Weather(511, "Rain", "freezing rain", "13d")
            71 -> Weather(600, "Snow", "slight snow", "13d")
            73 -> Weather(601, "Snow", "moderate snow", "13d")
            75 -> Weather(602, "Snow", "heavy snow", "13d")
            77 -> Weather(611, "Snow", "snow grains", "13d")
            80 -> Weather(520, "Rain", "slight rain showers", "09d")
            81 -> Weather(521, "Rain", "moderate rain showers", "09d")
            82 -> Weather(522, "Rain", "violent rain showers", "09d")
            85 -> Weather(620, "Snow", "slight snow showers", "13d")
            86 -> Weather(621, "Snow", "heavy snow showers", "13d")
            95 -> Weather(200, "Thunderstorm", "thunderstorm", "11d")
            96, 99 -> Weather(202, "Thunderstorm", "thunderstorm with hail", "11d")
            else -> Weather(800, "Clear", "unknown", "01d")
        }
    }
}

/**
 * Maps Open-Meteo response to our domain WeatherResponse
 */
object OpenMeteoMapper {

    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun mapToWeatherResponse(response: OpenMeteoWeatherResponse): WeatherResponse {
        val hourlyData = response.hourly
        val dailyData = response.daily

        // Create WeatherItems from hourly data (for compatibility)
        val weatherItems = hourlyData?.let { hourly ->
            hourly.time.mapIndexed { index, timeString ->
                val dateTime = LocalDateTime.parse(timeString, dateTimeFormatter)
                val epochSeconds = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond()

                WeatherItem(
                    date = epochSeconds,
                    main = MainWeather(
                        temp = hourly.temperature2m.getOrElse(index) { 0.0 },
                        tempMin = hourly.temperature2m.getOrElse(index) { 0.0 },
                        tempMax = hourly.temperature2m.getOrElse(index) { 0.0 },
                        humidity = hourly.relativeHumidity2m.getOrElse(index) { 0 }
                    ),
                    weather = listOf(
                        WeatherCodeMapper.mapToWeather(hourly.weatherCode.getOrElse(index) { 0 })
                    ),
                    wind = Wind(
                        speed = hourly.windSpeed10m.getOrElse(index) { 0.0 } / 3.6 // Convert km/h to m/s
                    ),
                    precipitationPredictability = (hourly.precipitationProbability.getOrElse(index) { 0 }) / 100.0
                )
            }
        } ?: emptyList()

        // Create DailyForecasts from daily data
        val dailyForecasts = dailyData?.let { daily ->
            daily.time.mapIndexed { index, dateString ->
                val date = java.time.LocalDate.parse(dateString, dateFormatter)
                val epochSeconds = date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond()

                val maxTemp = daily.temperature2mMax.getOrElse(index) { 0.0 }
                val minTemp = daily.temperature2mMin.getOrElse(index) { 0.0 }

                Forecast(
                    date = epochSeconds,
                    temperature = Temperature(
                        min = minTemp,
                        max = maxTemp
                    ),
                    weather = listOf(
                        WeatherCodeMapper.mapToWeather(daily.weatherCode.getOrElse(index) { 0 })
                    ),
                    humidity = 0, // Will be filled from hourly average if needed
                    windSpeed = daily.windSpeed10mMax.getOrElse(index) { 0.0 } / 3.6, // Convert km/h to m/s
                    precipitationPredictability = (daily.precipitationProbabilityMax.getOrElse(index) { 0 }) / 100.0
                )
            }
        } ?: emptyList()

        return WeatherResponse(
            city = City(
                id = 0,
                name = "",
                country = "",
                coord = Coordinates(response.latitude, response.longitude)
            ),
            list = weatherItems,
            daily = dailyForecasts
        )
    }

}

