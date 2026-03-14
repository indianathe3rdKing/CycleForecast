package com.devbub.cycleforecast.domain.model

data class BikeRidingScore(
    val score: Int,
    val recommendation: BikeRidingRecommendation,
    val factors: List<BikeRidingFactor>,
    val overallRating: String
)

enum class BikeRidingRecommendation {
    EXCELLENT,
    GOOD,
    MODERATE,
    POOR,
    DANGEROUS
}

data class BikeRidingFactor(
    val name: String,
    val score: Int,
    val weight: Double,
    val description: String,
    val icon: String
)

data class HourlyFactor(
    val time: String,
    val temperature: Double,
    val icon: String,
    val score: Int,
    val weight: Double
)
