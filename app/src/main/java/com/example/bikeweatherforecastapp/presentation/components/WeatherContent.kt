package com.example.bikeweatherforecastapp.presentation.components

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.bikeweatherforecastapp.domain.model.WeatherResponse
import com.example.bikeweatherforecastapp.presentation.screens.HeaderSection
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherContent(
    weatherData: WeatherResponse,
    viewModel: WeatherViewModel
) {

    val dailyScores by viewModel.dailyScores
    val bestDay = dailyScores.maxByOrNull { it.second.score }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val bestCardVisible = viewModel.bestCardVisibility.collectAsState().value
    val context= LocalContext.current
    var visible by remember { mutableStateOf(false) }


    BackHandler(enabled = true) {
        keyboardController?.hide()
        focusManager.clearFocus()
        (context as? Activity)?.finish()
    }

    LaunchedEffect(Unit) {
        visible= true
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AnimatedVisibility(
            visible=visible,
            enter = fadeIn()+ slideInVertically(initialOffsetY = {-300})
        ) {
        SearchInput(viewModel)}
        if (bestCardVisible) HeaderSection(weatherData, bestDay?.first, bestDay?.second)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 124.dp
            )
        ) {
            items(dailyScores) { (forecast, score) ->
                AnimatedVisibility(
                    visible = visible,
                    enter =
                        fadeIn()+ slideInHorizontally(initialOffsetX = {-300})
                ) {
                BikeRidingCard(
                    forecast = forecast,
                    score = score,
                    isBest = bestDay?.first?.date == forecast.date,
                    onClick = { selectedDate ->
                        viewModel.setSelectedDayDate(selectedDate)
                    }
                )}
            }
        }
    }
}