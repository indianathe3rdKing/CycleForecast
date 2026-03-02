package com.example.bikeweatherforecastapp.domain.usecase

import com.example.bikeweatherforecastapp.domain.model.BikeRidingFactor
import com.example.bikeweatherforecastapp.domain.model.BikeRidingRecommendation
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.Forecast
import com.example.bikeweatherforecastapp.domain.model.Weather

class CalculateBikeRidingScoreUseCase {

    operator fun invoke(forecast: Forecast, showNumericValues: Boolean = false): BikeRidingScore {
        val factors = mutableListOf<BikeRidingFactor>()

        val tempScore = calculateTemperatureScore(forecast.temperature.max)
        factors.add(
            BikeRidingFactor(
                name = "Temperature",
                score = tempScore,
                weight = 0.20,
                description = if (showNumericValues) "${forecast.temperature.max.toInt()}°C" else getTemperatureDescription(forecast.temperature.max),
                icon = if (showNumericValues) "🌡️" else getTemperatureIcon(forecast.temperature.max)
            )
        )

        val windScore = calculateWindScore(forecast.windSpeed)
        factors.add(
            BikeRidingFactor(
                name = "Wind",
                score = windScore,
                weight = 0.18,
                description = if (showNumericValues) "${(forecast.windSpeed * 3.6).toInt()} km/h" else getWindDescription(forecast.windSpeed),
                icon = if (showNumericValues) "🌬️" else getWindIcon(forecast.windSpeed)
            )
        )

        val humidityScore = calculateHumidityScore(forecast.humidity)
        factors.add(
            BikeRidingFactor(
                name = "Humidity",
                score = humidityScore,
                weight = 0.15,
                description = if (showNumericValues) "${forecast.humidity}%" else getHumidityDescription(forecast.humidity),
                icon = if (showNumericValues) "💧" else getHumidityIcon(forecast.humidity)
            )
        )

        val weatherScore = calculateWeatherScore(forecast.weather.firstOrNull())
        factors.add(
            BikeRidingFactor(
                name = "Weather",
                score = weatherScore,
                weight = 0.17,
                description = if (showNumericValues) forecast.weather.firstOrNull()?.main ?: "Clear" else getWeatherDescription(forecast.weather.firstOrNull()),
                icon = if (showNumericValues) "☁️" else getWeatherIcon(forecast.weather.firstOrNull())
            )
        )

        val precipitationScore = calculatePrecipitationScore(forecast.precipitationPredictability)
        factors.add(
            BikeRidingFactor(
                name = "Precipitation",
                score = precipitationScore,
                weight = 0.20,
                description = if (showNumericValues) "${(forecast.precipitationPredictability * 100).toInt()}%" else getPrecipitationDescription(forecast.precipitationPredictability),
                icon = if (showNumericValues) "🌧️" else getPrecipitationIcon(forecast.precipitationPredictability)
            )
        )

        val visibilityScore = calculateVisibilityScore(forecast.visibility)
        factors.add(
            BikeRidingFactor(
                name = "Visibility",
                score = visibilityScore,
                weight = 0.10,
                description = if (showNumericValues) "${(forecast.visibility / 1000).toInt()} km" else getVisibilityDescription(forecast.visibility),
                icon = if (showNumericValues) "🧭" else getVisibilityIcon(forecast.visibility)
            )
        )

        val totalScore = factors.sumOf { it.score * it.weight }.toInt()

        return BikeRidingScore(
            score = totalScore,
            recommendation = getRecommendation(totalScore),
            factors = factors,
            overallRating = getOverallRating(totalScore)
        )
    }
}

private fun calculatePrecipitationScore(probability: Double): Int {
    return when {
        probability < 0.1 -> 100
        probability < 0.2 -> 80
        probability < 0.3 -> 60
        probability < 0.5 -> 40
        probability < 0.7 -> 20
        else -> 0
    }
}

private fun calculateWeatherScore(weather: Weather?): Int {
    val weatherId = weather?.id ?: 800
    return when {
        weatherId in 200..232 -> 0
        weatherId in 300..321 -> 20
        weatherId in 500..531 -> 30
        weatherId in 600..622 -> 40
        weatherId in 701..781 -> 60
        weatherId == 800 -> 100
        weatherId in 801..804 -> 80
        else -> 50
    }
}

private fun calculateTemperatureScore(temp: Double): Int {
    return when {
        temp < -10 -> 0
        temp < 0 -> 20
        temp < 10 -> 60
        temp in 15.0..25.0 -> 100
        temp < 30 -> 80
        temp < 35 -> 40
        else -> 0
    }
}

private fun calculateWindScore(windSpeed: Double): Int {
    val windKmh = windSpeed * 3.6
    return when {
        windKmh < 10 -> 100
        windKmh < 15 -> 80
        windKmh < 20 -> 60
        windKmh < 25 -> 40
        windKmh < 30 -> 20
        else -> 0
    }
}

private fun calculateHumidityScore(humidity: Int): Int {
    return when {
        humidity < 30 -> 60
        humidity in 40..60 -> 100
        humidity < 70 -> 80
        humidity < 80 -> 60
        else -> 40
    }
}

private fun getRecommendation(score: Int): BikeRidingRecommendation {
    return when {
        score >= 85 -> BikeRidingRecommendation.EXCELLENT
        score >= 70 -> BikeRidingRecommendation.GOOD
        score >= 50 -> BikeRidingRecommendation.MODERATE
        score >= 30 -> BikeRidingRecommendation.POOR
        else -> BikeRidingRecommendation.DANGEROUS
    }
}

private fun getOverallRating(score: Int): String {
    return when {
        score >= 85 -> "Perfect for cycling! 🚴🏻"
        score >= 70 -> "Great conditions for cycling! 🚴🏻"
        score >= 50 -> "Moderate conditions be cautious! 🚨"
        score >= 30 -> "Challenging conditions! ⚠️"
        else -> "Dangerous conditions! 💀"
    }
}

private fun getTemperatureDescription(temp: Double): String {
    return when {
        temp < 0 -> "Very cold, wear warm gear"
        temp < 10 -> "Cold, layer up"
        temp in 15.0..25.0 -> "Perfect temperature for cycling"
        temp < 30 -> "Warm, stay hydrated"
        else -> "Very hot, avoid peak hours"
    }
}

private fun getWindDescription(windSpeed: Double): String {
    val windKmh = windSpeed * 3.6
    return when {
        windKmh < 10 -> "Light breeze, perfect"
        windKmh < 15 -> "Moderate wind"
        windKmh < 20 -> "Strong wind, challenging"
        windKmh < 25 -> "Very windy, difficult"
        else -> "Extremely windy, dangerous"
    }
}

private fun getPrecipitationDescription(probability: Double): String {
    return when {
        probability < 0.1 -> "No rain expected"
        probability < 0.2 -> "Low chance of rain"
        probability < 0.3 -> "Some chance of rain"
        probability < 0.5 -> "Moderate chance of rain"
        probability < 0.7 -> "High chance of rain"
        else -> "Very likely to rain"
    }
}

private fun getWeatherDescription(weather: Weather?): String {
    return weather?.description?.replaceFirstChar { it.uppercase() } ?: "Clear conditions"
}

private fun getHumidityDescription(humidity: Int): String {
    return when {
        humidity < 30 -> "Very dry air"
        humidity in 40..60 -> "Comfortable humidity"
        humidity < 70 -> "Moderate humidity"
        humidity < 80 -> "High humidity"
        else -> "Extremely humid"
    }
}

private fun getTemperatureIcon(temp: Double): String {
    return when {
        temp < 0 -> "❄️"
        temp < 10 -> "🥶"
        temp in 15.0..25.0 -> "😎"
        temp < 30 -> "🥵"
        else -> "🌞"
    }
}

private fun getPrecipitationIcon(probability: Double): String {
    return when {
        probability < 0.1 -> "☀️"
        probability < 0.2 -> "🌤️"
        probability < 0.3 -> "⛅"
        probability < 0.5 -> "🌥️"
        probability < 0.7 -> "🌧️"
        else -> "⛈️"
    }
}

private fun getWindIcon(windSpeed: Double): String {
    val windKmh = windSpeed * 3.6
    return when {
        windKmh < 10 -> "🌬🍃"
        windKmh < 15 -> "🌬️💨"
        windKmh < 20 -> "🌪️"
        windKmh < 25 -> "💨💨"
        else -> "🌪️💨"
    }
}

private fun getWeatherIcon(weather: Weather?): String {
    return when (weather?.id) {
        in 200..232 -> "⛈️"
        in 300..321 -> "🌦️"
        in 500..531 -> "🌧️"
        in 600..622 -> "❄️"
        in 700..781 -> "🌫️"
        800 -> "☀️"
        in 801..804 -> "☁️"
        else -> "🌤️"
    }
}

private fun getHumidityIcon(humidity: Int): String {
    return when {
        humidity < 30 -> "🏜️"
        humidity in 40..60 -> "🌤️"
        humidity < 70 -> "💧"
        humidity < 80 -> "💧💧"
        else -> "💧💧💧"
    }
}

// Visibility helper functions
private fun calculateVisibilityScore(visibility: Double): Int {
    val visibilityKm = visibility / 1000.0
    return when {
        visibilityKm >= 10 -> 100  // Excellent visibility
        visibilityKm >= 5 -> 80    // Good visibility
        visibilityKm >= 2 -> 60    // Moderate visibility
        visibilityKm >= 1 -> 40    // Poor visibility
        visibilityKm >= 0.5 -> 20  // Very poor visibility
        else -> 0                   // Dangerous - fog/mist
    }
}

private fun getVisibilityDescription(visibility: Double): String {
    val visibilityKm = visibility / 1000.0
    return when {
        visibilityKm >= 10 -> "Excellent visibility"
        visibilityKm >= 5 -> "Good visibility"
        visibilityKm >= 2 -> "Moderate visibility"
        visibilityKm >= 1 -> "Poor visibility"
        visibilityKm >= 0.5 -> "Very poor, be careful"
        else -> "Dangerous, avoid cycling"
    }
}

private fun getVisibilityIcon(visibility: Double): String {
    val visibilityKm = visibility / 1000.0
    return when {
        visibilityKm >= 10 -> "🔭"
        visibilityKm >= 5 -> "👀"
        visibilityKm >= 2 -> "🌁"
        visibilityKm >= 1 -> "🌫️"
        else -> "😶‍🌫️"
    }
}
