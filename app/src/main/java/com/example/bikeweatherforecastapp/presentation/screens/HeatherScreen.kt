package com.example.bikeweatherforecastapp.presentation.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.Forecast
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.presentation.utils.Utils
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.CyanAccent
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.TextSecondary
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary

@Composable
fun HeaderSection(
    weatherData: WeatherResponse,
    bestForecast: Forecast?,
    bestScore: BikeRidingScore?,

    ){
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ),
        colors= CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        modifier = Modifier.fillMaxWidth()
            .border(
                1.dp,TextTertiary.copy(alpha = 0.5f),
                RoundedCornerShape(16.dp)
            ),
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
               ,
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

            if (bestForecast!= null&&bestScore!=null){
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text="🥇Best day :",
                        color=CyanAccent,
                        fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text= Utils.formatDate(bestForecast.date),
                        color=TextPrimary,
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