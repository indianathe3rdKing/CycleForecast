package com.devbub.cycleforecast.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.domain.model.BikeRidingFactor
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel
import com.devbub.cycleforecast.ui.theme.FactorBackground
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextTertiary
import kotlinx.coroutines.delay

@Composable
fun FactorItem(
    factor: BikeRidingFactor,
    height: Dp, backgroundColor: Color = FactorBackground,
    viewModel: WeatherViewModel
) {

    var visible by remember { mutableStateOf(false) }
    var textScale by remember { mutableStateOf(1f) }
    val weatherState by viewModel.weatherState
    val selectedDay = weatherState.selectedDay


    // Animate the text scale
    val animatedTextScale by animateFloatAsState(
        targetValue = textScale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "textScale"
    )

    // Initial animation on first composition
    LaunchedEffect(Unit) {
        visible = true
    }

    // Trigger animation when description changes
    if (selectedDay == true) {
        LaunchedEffect(factor.description) {
            textScale = 1.3f
            delay(150)
            textScale = 1f
        }
    }
    Card(
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(80.dp)
            .height(height)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(1000)) +
                        scaleIn(initialScale = 0.5f, animationSpec = tween(1000)),

                ) {
                Text(
                    text = factor.icon,
                    fontSize = 18.sp,
                    color = TextTertiary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            AnimatedVisibility(
                visible=visible,
                enter = fadeIn(animationSpec = tween(1000))+
                        scaleIn(initialScale = 0.5f, animationSpec = tween(1000))
            ) {
            Text(
                text = factor.name,
                color = TextPrimary,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                textAlign = TextAlign.Center
            )}

            Spacer(Modifier.height(4.dp))
            AnimatedVisibility(
                visible = visible,
                enter= fadeIn(animationSpec =tween(1000))+
                scaleIn(initialScale = 0.5f, animationSpec = tween(1000))
            ) {
            Text(
                text = factor.description,
                color = TextTertiary,
                fontSize = (8.sp * animatedTextScale),
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = (10.sp * animatedTextScale)
            )}
        }
    }
}