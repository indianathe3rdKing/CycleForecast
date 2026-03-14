package com.devbub.cycleforecast.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.R
import com.devbub.cycleforecast.domain.model.BikeRidingScore
import com.devbub.cycleforecast.domain.model.Forecast
import com.devbub.cycleforecast.presentation.utils.Utils
import com.devbub.cycleforecast.presentation.utils.Utils.getScoreColor
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextTertiary
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel


@Composable
fun HourlyCard(
    forecast: Forecast,
    score: BikeRidingScore,
    hourlyForecasts: List<Forecast>,
    selectedIndex: Int,
    isMetric: Boolean = true,
    onHourSelected: (Int) -> Unit = {},
    viewModel: WeatherViewModel = koinViewModel()
) {
    val scoreColor = getScoreColor(score.score)
    var visible by remember { mutableStateOf(false) }
    var animate by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    // Reset and trigger animation whenever the forecast changes (hourly card clicked)
    LaunchedEffect(forecast.date, selectedIndex) {
        animate = false
        delay(50) // Small delay to ensure reset
        animate = true
    }



    Box(
        modifier = Modifier
            .fillMaxSize(),

        ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(24.dp, 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn()+ slideInVertically(initialOffsetY = {50})
                    + slideInHorizontally(initialOffsetX = {-50}),

                ) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        ),
                        modifier = Modifier
                            .size(50.dp)
                            .clickable { viewModel.updateSelectedDay(false) },
                        colors = CardDefaults.cardColors(
                            containerColor = CardBackground
                        ),
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()

                        ) {
                            Icon(
                                painter = painterResource(R.drawable.back_black),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                                tint = TextTertiary
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp, 4.dp)
                ) {
                    Text(
                        Utils.formatDate(forecast.date), fontSize = 16.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = TextPrimary.copy(0.6f),
                                offset = Offset(0f, 0f),
                                blurRadius = 12f
                            )
                        )
                    )
                    Text(
                        text = score.recommendation.name,
                        color = scoreColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = scoreColor.copy(0.4f),
                                offset = Offset(0f, 0f),
                                blurRadius = 20f
                            )
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
                modifier = Modifier
                    .size(210.dp), colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ),
                shape = RoundedCornerShape(85.dp)

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center

                ) {
                    CircularProgressBar(
                        score.score,
                        14,
                        scoreColor,
                        modifier = Modifier.size(170.dp),
                        30
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
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
            // Use Row instead of LazyVerticalGrid to avoid nested scrolling crash

            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
                modifier = Modifier
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ), shape = RoundedCornerShape(24.dp)


            ) {

                Spacer(modifier = Modifier.height(8.dp))
                // Show remaining factors if any

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items(score.factors) { item ->
                        FactorItem(item, maxFactorHeight,CardBackground,viewModel=viewModel)
                    }
                }

            }
            Spacer(modifier = Modifier.height(8.dp))

            // Hourly items LazyRow - clicking changes the displayed HourlyCard
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CardBackground
                ), shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 10.dp, 16.dp, 14.dp)
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Hourly Forecast",
                        fontSize = 12.sp,
                        color = TextTertiary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {

                        itemsIndexed(hourlyForecasts) { index, hourForecast ->
                            HourlyItem(
                                forecast = hourForecast,
                                isMetric = isMetric,
                                isSelected = index == selectedIndex,
                                onClick = { onHourSelected(index) }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}