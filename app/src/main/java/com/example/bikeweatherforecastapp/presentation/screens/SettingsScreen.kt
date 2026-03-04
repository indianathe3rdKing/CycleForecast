package com.example.bikeweatherforecastapp.presentation.screens

import android.Manifest
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.bikeweatherforecastapp.data.services.notifications.NotificationHelper
import com.example.bikeweatherforecastapp.domain.model.Setting
import com.example.bikeweatherforecastapp.presentation.components.SettingsActionItem
import com.example.bikeweatherforecastapp.presentation.components.SettingsSectionHeader
import com.example.bikeweatherforecastapp.presentation.components.SettingsToggleItem
import com.example.bikeweatherforecastapp.presentation.viewmodel.WeatherViewModel
import com.example.bikeweatherforecastapp.ui.theme.CardBackground
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary
import com.example.bikeweatherforecastapp.ui.theme.Warning
import org.koin.androidx.compose.koinViewModel
import com.example.bikeweatherforecastapp.presentation.components.UnitToggleButton
import com.example.bikeweatherforecastapp.ui.theme.Success
import kotlin.contracts.contract
import androidx.core.net.toUri


@Composable
fun SettingsScreen(viewModel: WeatherViewModel = koinViewModel()) {
    // State for toggle settings
    val context = viewModel.getApplication<Application>()
    val isMetricUnit = viewModel.isMetric.collectAsState().value
    val useCurrentLocation = viewModel.useCurrentLocation.collectAsState().value
    val savedCity = viewModel.savedCity.collectAsState().value
    val bestCardVisibility = viewModel.bestCardVisibility.collectAsState().value
    var notificationsEnabled = viewModel.toggleNotification.collectAsState().value
    val showBatteryOptimizationDialog by viewModel.showBatteryOptimizationDialog

    val scrollState = rememberScrollState()

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract= ActivityResultContracts.RequestPermission()
    ) {
        granted ->
        if(granted){
            notificationsEnabled=true
            viewModel.enableNotifications()
        }else{
            notificationsEnabled=false
        }
    }

    // Battery Optimization Dialog
    if (showBatteryOptimizationDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissBatteryOptimizationDialog() },
            title = { Text("Permission Required") },
            text = {
                Text("To ensure full functionality, please allow Bike Weather Forecast to run without battery restrictions.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.dismissBatteryOptimizationDialog()
                        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                            data = "package:${context.packageName}".toUri()
                            flags= Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        context.startActivity(intent)
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.dismissBatteryOptimizationDialog() }
                ) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 26.dp)
            .padding(bottom = 100.dp) // Extra padding for bottom navigation bar
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header
        Text(
            text = "Settings",
            color = TextPrimary,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Customize your app experience",
            color = TextTertiary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Location Section
        SettingsSectionHeader(title = "Location")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsToggleItem(
            icon = Icons.Default.LocationOn,
            title = "Use Current Location",
            subtitle = if (useCurrentLocation) "Using device GPS location"
            else if (savedCity.isNotEmpty()) "Using: $savedCity"
            else "Search for a city to use",
            checked = useCurrentLocation,
            onCheckedChange = { viewModel.updateUseCurrentLocation(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Best Card Section
        SettingsSectionHeader(title = "Best Day Card")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsToggleItem(
            icon = Icons.Default.Create,
            title = "Best Day Card",
            subtitle = "Show the best day in the forecast",
            checked = bestCardVisibility,
            onCheckedChange = {viewModel.updateBestCardVisibility(it)}
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Units Section
        SettingsSectionHeader(title = "Units")

        Spacer(modifier = Modifier.height(12.dp))

        // Unit Toggle Card with custom implementation for toggle buttons
        Card(
            modifier = Modifier.fillMaxWidth()
                ,
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Units",
                        tint = Success,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Column {
                        Text(
                            text = "Temperature Unit",
                            color = TextPrimary,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = if (isMetricUnit) "Celsius (°C)" else "Fahrenheit (°F)",
                            color = TextTertiary,
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Toggle Buttons for Unit Selection
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    UnitToggleButton(
                        text = "Metric (°C)",
                        isSelected = isMetricUnit,
                        onClick = { viewModel.updateMetric(true) },
                        modifier = Modifier.weight(1f)
                    )
                    UnitToggleButton(
                        text = "Imperial (°F)",
                        isSelected = !isMetricUnit,
                        onClick = { viewModel.updateMetric(false) },
                        modifier = Modifier.weight(1f)

                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Notifications Section
        SettingsSectionHeader(title = "Notifications")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsToggleItem(
            icon = Icons.Default.Notifications,
            title = "Daily Forecast Alerts",
            subtitle = "Get notified about best riding conditions",
            checked = notificationsEnabled,
            onCheckedChange = { enabled->

                if (enabled){
                if(!NotificationHelper(context).batteryOptimization()){
                    viewModel.requestIgnoreBatteryOptimization(context)

                }else{
                    if(
                        ContextCompat.checkSelfPermission(
                            viewModel.getApplication(),
                            Manifest.permission.POST_NOTIFICATIONS
                        )== PackageManager.PERMISSION_GRANTED
                    ){
                        viewModel.updateNotificationToggle(true)
                        viewModel.enableNotifications()
                    }else{
                        notificationPermissionLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )

                    }
                }}else{
                    viewModel.updateNotificationToggle(false)
                    viewModel.disableNotifications()
                }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Premium Section
        SettingsSectionHeader(title = "Premium")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionItem(
            Setting(
                icon = Icons.Default.Star,
                title = "Remove Ads",
                subtitle = "Enjoy an ad-free experience",
                iconTint = Warning,
                actionIcon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                onClick = {
                    // Handle remove ads action - navigate to purchase screen
                })
        )

        Spacer(modifier = Modifier.height(24.dp))

        // About Section
        SettingsSectionHeader(title = "About")

        Spacer(modifier = Modifier.height(12.dp))

        SettingsActionItem(
            Setting(
                icon = Icons.Default.Build,
                title = "App Version",
                iconTint = Success,
                subtitle = "1.0.0",
                actionIcon = null,
                onClick = { }
            )
        )

        Spacer(modifier = Modifier.height(48.dp))
    }
}






