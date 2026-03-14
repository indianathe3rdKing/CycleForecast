package com.devbub.cycleforecast.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.domain.model.BikeRidingScore
import com.devbub.cycleforecast.domain.model.Forecast
import com.devbub.cycleforecast.domain.model.WeatherResponse
import com.devbub.cycleforecast.presentation.utils.Utils
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.CyanAccent
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextSecondary
import com.devbub.cycleforecast.ui.theme.TextTertiary

@Composable
fun HeaderSection(
    weatherData: WeatherResponse,
    bestForecast: Forecast?,
    bestScore: BikeRidingScore?,

    ) {

    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter= fadeIn()+ slideInVertically(initialOffsetY = { -200 })
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    1.dp, TextTertiary.copy(alpha = 0.5f),
                    RoundedCornerShape(16.dp)
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🚴 Bike Riding Forecast",
                    color = TextPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (weatherData.city.country.isNotEmpty()) {
                        "${weatherData.city.name}, ${weatherData.city.country}"
                    } else {
                        weatherData.city.name
                    },
                    color = TextSecondary,
                    fontSize = 16.sp
                )

                if (bestForecast != null && bestScore != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "🥇Best day :",
                            color = CyanAccent,
                            fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = Utils.formatDate(bestForecast.date),
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = bestScore.overallRating,
                            color = TextTertiary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}