package com.example.bikeweatherforecastapp.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.presentation.components.WeatherContent
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel


@Composable
fun HomeScreen(weatherData: WeatherResponse, viewModel: WeatherViewModel) {
    val state by viewModel.weatherState

    if (state.selectedDay!!) {
      DailyCastScreen(viewModel)
    }else{
        WeatherContent(weatherData, viewModel)
    }
}
