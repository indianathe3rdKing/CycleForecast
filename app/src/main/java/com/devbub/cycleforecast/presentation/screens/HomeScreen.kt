package com.devbub.cycleforecast.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.devbub.cycleforecast.domain.model.WeatherResponse
import com.devbub.cycleforecast.presentation.components.WeatherContent
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel


@Composable
fun HomeScreen(weatherData: WeatherResponse, viewModel: WeatherViewModel) {
    val state by viewModel.weatherState

    if (state.selectedDay == true) {
      DailyCastScreen(viewModel)
    }else{
        WeatherContent(weatherData, viewModel)
    }
}
