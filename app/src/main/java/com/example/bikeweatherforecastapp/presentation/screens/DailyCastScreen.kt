package com.example.bikeweatherforecastapp.presentation.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.DarkBlue
import com.example.bikeweatherforecastapp.ui.theme.LightBlue
import com.example.bikeweatherforecastapp.ui.theme.MediumBlue
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary


@Composable
fun DailyCastScreen(viewModel: WeatherViewModel) {


    BackHandler {
        viewModel.updateSelectedDay(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        DarkBlue,
                        MediumBlue,
                        LightBlue
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Daily Cast Screen",
            modifier = Modifier.padding(16.dp),
            color= TextPrimary
        )

    }
}