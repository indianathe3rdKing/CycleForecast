package com.example.bikeweatherforecastapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.HourlyForecast
import com.example.bikeweatherforecastapp.presentation.utils.Utils
import com.example.bikeweatherforecastapp.presentation.utils.Utils.getScoreColor
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary


@Composable
fun HourlyCard(score: BikeRidingScore, forecast: HourlyForecast) {

    val scoreColor = getScoreColor(score.score)

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(CardBackground)
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    Utils.formatDate(forecast.date), fontSize = 18.sp,
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = score.recommendation.name,
                    color = scoreColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            CircularProgressBar(score.score, scoreColor, modifier = Modifier.size(120.dp))
            Spacer(modifier = Modifier.height(8.dp))
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(score.factors) { factor ->
                    FactorItem(factor, maxFactorHeight)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(score.factors) { factor ->
                    FactorItem(factor, maxFactorHeight)
                }

            }
        }
    }
}