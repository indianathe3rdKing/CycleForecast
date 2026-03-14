package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.domain.model.Forecast
import com.devbub.cycleforecast.presentation.utils.Utils
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextTertiary
import com.devbub.cycleforecast.ui.theme.FactorBackground

@Composable
fun HourlyItem(
    forecast: Forecast,
    isMetric: Boolean = true,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    // Use Utils.getWeatherIcon which uses the same logic as CalculateBikeRidingScoreUseCase
    val weatherIcon = Utils.getWeatherIcon(forecast.weather.firstOrNull(), Utils.formatTime(forecast.date))
    val temperature = if (isMetric) forecast.temperature.max else forecast.temperature.max * 9/5 + 32
    val unit = if (isMetric) "°C" else "°F"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) TextPrimary.copy(alpha = 0.2f) else FactorBackground
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(70.dp)
            .height(90.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            // Time
            Text(
                text = Utils.formatTime(forecast.date),
                color = TextTertiary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Weather Icon
            Text(
                text = weatherIcon,
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            // Temperature
            Text(
                text = "${temperature.toInt()}$unit",
                color = TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}


