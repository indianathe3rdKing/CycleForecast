package com.example.bikeweatherforecastapp.presentation.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.example.bikeweatherforecastapp.domain.usecase.CalculateBikeRidingScoreUseCase
import com.example.bikeweatherforecastapp.domain.usecase.GetWeatherUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.runtime.State
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import com.example.bikeweatherforecastapp.data.local.DataStoreManager
import com.example.bikeweatherforecastapp.domain.model.BikeRidingScore
import com.example.bikeweatherforecastapp.domain.model.Forecast
import com.example.bikeweatherforecastapp.domain.model.Temperature
import com.example.bikeweatherforecastapp.domain.model.WeatherItem
import com.example.bikeweatherforecastapp.domain.model.WeatherState
import com.example.bikeweatherforecastapp.domain.usecase.SearchCityUseCase
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class WeatherViewModel(
    application: Application,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val calculateBikeRidingScoreUseCase: CalculateBikeRidingScoreUseCase,
    private val searchCityUseCase: SearchCityUseCase,
    private val dataStoreManager: DataStoreManager
) : AndroidViewModel(application) {

    val isMetric = dataStoreManager.isMetric
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getMetricSync()
        )
    val savedCity = dataStoreManager.city
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getCitySync()
        )

    val useCurrentLocation = dataStoreManager.useCurrentLocation
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getUseCurrentLocationSync()
        )

    val bestCardVisibility = dataStoreManager.getBestCardVisibility
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getBestCardVisibilitySync()
        )


    //location
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices
        .getFusedLocationProviderClient(
            application
        )
    private val _locationPermissionGranted = mutableStateOf(false)
    val locationPermissionGranted: State<Boolean> = _locationPermissionGranted

    //Weather
    private val _weatherState = mutableStateOf(WeatherState())
    val weatherState: State<WeatherState> = _weatherState

    //score
    private val _dailyScores =
        mutableStateOf<List<Pair<Forecast, BikeRidingScore>>>(emptyList())
    val dailyScores: State<List<Pair<Forecast, BikeRidingScore>>> = _dailyScores
    private val _hourlyScores =
    mutableStateOf<List<Pair<Forecast, BikeRidingScore>>>(emptyList())
    val hourlyScores: State<List<Pair<Forecast, BikeRidingScore>>> = _hourlyScores
    fun updateSelectedDay(selectedDay: Boolean) {
        viewModelScope.launch {

            _weatherState.value = _weatherState.value.copy(
                selectedDay = selectedDay
            )
            Log.i(TAG, "updateSelectedDay: $selectedDay")
        }
    }

    fun updateBestCardVisibility(isVisible: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setBestCardVisibility(isVisible)
        }
    }

    fun updateMetric(metric: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setMetric(metric)
        }
    }

    fun updateUseCurrentLocation(useCurrentLocation: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setUseCurrentLocation(useCurrentLocation)
            // If toggled on, immediately fetch current location weather
            if (useCurrentLocation) {
                checkLocationPermission()
            } else {
                // If toggled off and we have a saved city, use it
                val city = dataStoreManager.getCitySync()
                if (city.isNotEmpty()) {
                    searchCity(city)
                }
            }
        }
    }

    fun checkLocationPermission() {
        val context = getApplication<Application>()
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        _locationPermissionGranted.value = hasPermission
        if (hasPermission) getCurrentLocation()
    }

    fun searchCity(city: String) {
        Log.d(TAG, "searchCity called with: $city")
        viewModelScope.launch {
            val cityLocation = searchCityUseCase(city)
            Log.d(TAG, "searchCity result: $cityLocation")

            cityLocation?.let {
                Log.i(TAG, "Found: ${it.name}, ${it.country} at ${it.coordinates.lat}, ${it.coordinates.lon}")
                fetchWeatherData(
                    latitude = it.coordinates.lat,
                    longitude = it.coordinates.lon,
                    cityName = it.name,
                    country = it.country
                )
                dataStoreManager.setCity(it.name)
                // Turn off "Use Current Location" when user manually searches for a city
                dataStoreManager.setUseCurrentLocation(false)
            } ?: run {
                Log.w(TAG, "City not found: $city")
                _weatherState.value = _weatherState.value.copy(
                    isLoading = false,
                    error = "City not found: $city"
                )
            }
        }
    }

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun getCurrentLocation() {
        if (
            ContextCompat.checkSelfPermission(
                getApplication(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY, null
            )

                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        fetchWeatherData(
                            latitude = it.latitude,
                            longitude = it.longitude,
                            cityName = "Current Location",
                            country = ""
                        )
                    }
                }

                .addOnFailureListener { exception ->
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Failed to get location : ${exception.message}"
                    )
                }
        }
    }

    private fun fetchWeatherData(
        latitude: Double,
        longitude: Double,
        cityName: String = "Current Location",
        country: String = ""
    ) {
        _weatherState.value = _weatherState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            getWeatherUseCase(latitude, longitude)
                .onSuccess { response ->
                    // Open-Meteo already provides daily forecasts directly
                    val dailyForecast = response.daily.take(7)

                    // Get hourly forecasts from the list (already processed by mapper)
                    val hourlyForecast = processHourlyForecasts(response.list)

                    val score = dailyForecast.map { forecast ->
                        forecast to calculateBikeRidingScoreUseCase(forecast, showNumericValues = false)
                    }

                    val hourlyScore = hourlyForecast.map { forecast ->
                        forecast to calculateBikeRidingScoreUseCase(forecast, showNumericValues = true)
                    }
                    _dailyScores.value = score
                    _hourlyScores.value = hourlyScore

                    // Update city name and country in response
                    val updatedResponse = response.copy(
                        city = response.city.copy(
                            name = cityName,
                            country = country
                        )
                    )

                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        weatherData = updatedResponse,
                        hourlyForecasts = hourlyForecast,
                        error = null
                    )

                    Log.i(TAG, "Weather data fetched successfully for $cityName, $country")
                    Log.i(TAG, "Daily forecasts: ${dailyForecast.size}, Hourly forecasts: ${hourlyForecast.size}")
                }
                .onFailure { exception ->
                    Log.e(TAG, "Failed to fetch weather data", exception)
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Failed to fetch weather data: ${exception.message}"
                    )
                }
        }
    }

    private fun processHourlyForecasts(weatherItems: List<WeatherItem>): List<Forecast> {
       val formatTime = SimpleDateFormat("HH:mm", Locale.getDefault())
        // Take next 24 hours of forecasts
        return weatherItems.take(24).map { item ->
            val formattedTime = formatTime.format(item.date*1000)
            Forecast(
                date = item.date,
                temperature = Temperature(
                    min = item.main.temp,
                    max = item.main.temp
                ),
                weather = item.weather,
                humidity = item.main.humidity,
                windSpeed = item.wind.speed,
                precipitationPredictability = item.precipitationPredictability
            )
        }.also {
            Log.i(TAG, "Processed ${it.size} hourly forecasts")
        }
    }


}

private const val TAG = "WeatherViewModel"