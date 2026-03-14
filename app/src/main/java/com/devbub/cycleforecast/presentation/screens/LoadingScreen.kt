package com.devbub.cycleforecast.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.devbub.cycleforecast.R
import com.devbub.cycleforecast.ui.theme.TextPrimary

@Composable
fun LoadingScreen(){
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.cycle))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
    Box(
        Modifier.fillMaxSize(),
        contentAlignment= Alignment.Center
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LottieAnimation(
                modifier = Modifier.size(400.dp),
                composition=composition,
                progress = { progress }

            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text="Loading weather data...",
                color=TextPrimary, fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}