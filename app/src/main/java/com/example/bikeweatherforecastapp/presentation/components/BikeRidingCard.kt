package com.example.bikeweatherforecastapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.DailyForecast
import com.example.bikeweatherforecastapp.presentation.utils.Utils
import com.example.bikeweatherforecastapp.presentation.utils.Utils.getScoreColor
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.Success
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.TextSecondary
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.CardBackgroundBest
import org.koin.androidx.compose.koinViewModel


@Composable
fun BikeRidingCard(
    forecast: DailyForecast,
    score: BikeRidingScore,

    isBest: Boolean,
    viewModel: WeatherViewModel = koinViewModel()
) {
    val scoreColor = getScoreColor(score.score)
    val weatherMetric = viewModel.isMetric.collectAsState().value
    val backgroundColor = if (isBest) {
        CardBackgroundBest
    } else {
        CardBackground
    }
    lateinit var temperatureMax: String
    lateinit var temperatureMin: String
    if (weatherMetric) {
        temperatureMax = "${forecast.temperature.max.toInt()}°"
        temperatureMin = "${forecast.temperature.min.toInt()}°"

    } else {
        temperatureMax = "${Utils.toFahrenheit(forecast.temperature.max).toInt()}°"
        temperatureMin = "${Utils.toFahrenheit(forecast.temperature.min).toInt()}°"

    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = {viewModel.updateSelectedDay(true)},
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isBest) Modifier.border(
                    3.dp, Success,
                    RoundedCornerShape(20.dp)
                ) else Modifier
            )
            ,
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(20.dp),

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)

                ) {
                    Text(
                        text = Utils.formatDate(forecast.date),
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = score.recommendation.name,
                        color = scoreColor,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                //Circular progress bar
                CircularProgressBar(
                    score = score.score, color = scoreColor,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = score.overallRating,
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = Utils.getWeatherIconUrl(forecast.weather.firstOrNull()?.icon ?: ""),
                    contentDescription = forecast.weather.firstOrNull()?.description,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(15.dp))
                Column {
                    Text(
                        text = "$temperatureMax / $temperatureMin",
                        color = TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = forecast.weather.firstOrNull()?.description?.capitalize() ?: "",
                        color = TextTertiary,
                        fontSize = 14.sp
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))
            //Show factors with better layout
            val maxFactorHeight = remember(score.factors) {
                //Calculate the maximum height for factors
                score.factors.maxOfOrNull { factor ->
                    //More generous height calculation to ensure all content is visible
                    val baseHeight = 100.dp
                    val extraHeight = when {
                        factor.description.length > 30 -> 30.dp
                        factor.description.length > 20 -> 20.dp
                        else -> 0.dp
                    }
                    baseHeight + extraHeight
                } ?: 120.dp

            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(score.factors) { factor ->
                    FactorItem(
                        factor = factor,
                        height = maxFactorHeight
                    )
                }
            }
        }
    }
}