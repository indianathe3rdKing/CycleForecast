package com.example.bikeweatherforecastapp.presentation.viewmodel

import android.Manifest
import android.app.Application
import android.app.NotificationManager
import android.content.Context
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
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.bikeweatherforecastapp.data.local.DataStoreManager
import com.example.bikeweatherforecastapp.data.services.notifications.NotificationHelper
import com.example.bikeweatherforecastapp.data.services.notifications.NotificationWorker
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
import retrofit2.HttpException
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit

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

    val toggleNotification = dataStoreManager.notificationToggle
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getNotificationSync()
        )

    val bestCardVisibility = dataStoreManager.getBestCardVisibility
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = dataStoreManager.getBestCardVisibilitySync()
        )

    // Battery optimization dialog trigger
    private val _showBatteryOptimizationDialog = mutableStateOf(false)
    val showBatteryOptimizationDialog: State<Boolean> = _showBatteryOptimizationDialog

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

    fun setSelectedDayDate(date: Long) {
        viewModelScope.launch {
            // Get the full weather response
            val weatherData = _weatherState.value.weatherData

            if (weatherData == null) {
                Log.e(TAG, "No weather data available")
                return@launch
            }

            // Filter hourly forecasts from the raw API data for the specific day
            val hourlyForecastsForDay = getHourlyForecastsForDay(weatherData.list, date)

            Log.i(TAG, "Selected date (epoch): $date")
            Log.i(TAG, "Filtered ${hourlyForecastsForDay.size} hourly forecasts for selected day")

            // Calculate scores for filtered hourly forecasts
            val hourlyScoresForDay = hourlyForecastsForDay.map { forecast ->
                forecast to calculateBikeRidingScoreUseCase(forecast, showNumericValues = true)
            }

            _hourlyScores.value = hourlyScoresForDay
            _weatherState.value = _weatherState.value.copy(
                selectedDay = true
            )
        }
    }

    /**
     * Get hourly forecasts for a specific day from the raw API weather items
     */
    private fun getHourlyForecastsForDay(weatherItems: List<WeatherItem>, targetDate: Long): List<Forecast> {
        return weatherItems
            .filter { item ->
                isSameDay(item.date, targetDate)
            }
            .map { item ->
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
            }
            .also {
                Log.i(TAG, "Created ${it.size} hourly forecasts for day with epoch $targetDate")
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

    fun updateNotificationToggle(toggle: Boolean){
        viewModelScope.launch {
            dataStoreManager.setNotificationToggle(toggle)
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

    fun enableNotifications(){
        val request = PeriodicWorkRequestBuilder<NotificationWorker>(
            12, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork("daily_notification",
            ExistingPeriodicWorkPolicy.UPDATE,request)
    }

    fun disableNotifications(){
        val context = getApplication<Application>()
        WorkManager.getInstance(getApplication())
            .cancelUniqueWork("daily_notification")

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1)
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

                    // Calculate scores for daily forecasts
                    val score = dailyForecast.map { forecast ->
                        forecast to calculateBikeRidingScoreUseCase(forecast, showNumericValues = false)
                    }

                    _dailyScores.value = score
                    _hourlyScores.value = emptyList()

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
                        hourlyForecasts = emptyList(), // We'll create these on-demand when a day is selected
                        error = null
                    )

                    Log.i(TAG, "Weather data fetched successfully for $cityName, $country")
                    Log.i(TAG, "Daily forecasts: ${dailyForecast.size}, Hourly items in API response: ${response.list.size}")
                }
                .onFailure { exception ->
                    Log.e(TAG, "Failed to fetch weather data", exception)
                    val errorCode =(exception as? HttpException)?.code()
                    val message = when(errorCode){
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        404 -> "Not Found"
                        429->"Too many requests. Try again later."
                        500-> "Weather service unavailable"
                        else -> "Unknown Error"
                    }
                    _weatherState.value = _weatherState.value.copy(
                        isLoading = false,
                        error = "Failed to fetch weather data: ${message}"
                    )
                }
        }
    }

    fun isSameDay(epoch1: Long, epoch2: Long): Boolean {
        val zone = ZoneId.systemDefault()

        val date1 = Instant.ofEpochSecond(epoch1).atZone(zone).toLocalDate()
        val date2 = Instant.ofEpochSecond(epoch2).atZone(zone).toLocalDate()

        return date1 == date2
    }

    fun requestIgnoreBatteryOptimization(context: Context) {
        if (!NotificationHelper(context).batteryOptimization()) {
            _showBatteryOptimizationDialog.value = true
        }
    }

    fun dismissBatteryOptimizationDialog() {
        _showBatteryOptimizationDialog.value = false
    }

    companion object {
        private const val TAG = "WeatherViewModel"
    }
}
