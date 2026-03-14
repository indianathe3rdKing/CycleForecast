# 🚴‍♂️ CycleForecast

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-purple.svg)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Android-13+-green.svg)](https://developer.android.com)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-blue.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A modern Android weather application designed specifically for cyclists, providing weather forecasts with bike riding score recommendations based on multiple weather factors.

## 📱 Overview

CycleForecast helps cyclists plan their rides by analyzing weather conditions and providing a comprehensive bike riding score. The app evaluates temperature, wind speed, humidity, weather conditions, precipitation, visibility, and UV index to determine the best times for cycling.

## 📸 Screenshots

<p align="center">
  <img src="screenshots/screenshot1.jpg" width="200" alt="Home Screen" />
  <img src="screenshots/screenshot2.jpg" width="200" alt="Daily Forecast" />
  <img src="screenshots/screenshot3.jpg" width="200" alt="Hourly Details" />
  <img src="screenshots/screenshot4.jpg" width="200" alt="Settings Screen" />
</p>

## 🎥 Demo Video

<p align="center">
  <a href="screenshots/app_recording.mp4">
    <img src="screenshots/screenshot1.jpg" width="400" alt="Watch Demo Video" />
  </a>
</p>

> **📹 Click the image above or [here](screenshots/app_recording.mp4) to watch the demo video**

## ✨ Features

### 🌦️ Weather Data
- **Real-time Weather Information**: Current weather conditions with detailed metrics
- **7-Day Forecast**: Extended forecast with daily bike riding scores
- **Hourly Breakdown**: Hour-by-hour weather details for each day
- **Location-based**: Automatic location detection or manual city search
- **Multiple Cities**: Search and save weather for different locations

### 🚴 Bike Riding Score
- **Intelligent Scoring System**: Analyzes 7 weather factors to calculate ridability
- **Factor Breakdown**: Detailed view of how each factor affects your ride
  - Temperature (20% weight)
  - Wind Speed (18% weight)
  - Humidity (15% weight)
  - Weather Conditions (25% weight)
  - Precipitation (15% weight)
  - Visibility (4% weight)
  - UV Index (3% weight)
- **Visual Indicators**: Color-coded scores and animated progress bars
- **Best Day Highlight**: Automatically identifies the best day for cycling
- **Factor Descriptions**: Descriptive text or numeric values for each factor

### 🔔 Notifications
- **Daily Weather Alerts**: Morning notifications with bike riding scores
- **Background Updates**: WorkManager-powered periodic updates
- **Customizable**: Toggle notifications on/off in settings
- **Battery Optimized**: Request to disable battery optimization for reliable notifications

### ⚙️ Settings & Customization
- **Unit Toggle**: Switch between Metric (°C, km/h) and Imperial (°F, mph)
- **Location Options**: Use current location or saved city
- **Best Card Visibility**: Show/hide the "Best Day" card
- **Notification Control**: Enable/disable daily weather notifications
- **Battery Optimization**: Manage power settings for background updates

### 🎨 User Interface
- **Material 3 Design**: Modern, beautiful UI with dynamic theming
- **Smooth Animations**: Lottie animations and custom transitions
- **Pull-to-Refresh**: Easy data refresh with circular progress indicator
- **Interactive Cards**: Tap daily forecast cards to view hourly details
- **Tab Navigation**: Easy switching between Home and Settings
- **Responsive Layout**: Optimized for various screen sizes
- **Weather Icons**: Beautiful SVG icons from Heroicons

## 🏗️ Architecture

The app follows **Clean Architecture** principles with clear separation of concerns:

```
app/
├── data/                          # Data Layer
│   ├── local/                     # Local data sources
│   │   └── DataStoreManager.kt    # Preferences storage
│   ├── remote/                    # Remote data sources
│   │   ├── WeatherApiService.kt   # Weather API interface
│   │   ├── OpenMeteoApiService.kt # Open-Meteo API
│   │   ├── Config.kt              # API configuration
│   │   ├── dto/                   # Data Transfer Objects
│   │   └── mapper/                # DTO to Domain mappers
│   ├── repository/                # Repository implementations
│   └── services/                  # Background services
│       └── notifications/         # Notification system
│
├── domain/                        # Domain Layer
│   ├── model/                     # Domain models
│   │   ├── WeatherModels.kt       # Weather entities
│   │   ├── RidingModels.kt        # Bike riding entities
│   │   └── SettingsModels.kt      # Settings entities
│   ├── repository/                # Repository interfaces
│   │   ├── WeatherRepository.kt
│   │   └── SearchRepository.kt
│   └── usecase/                   # Business logic
│       ├── GetWeatherUseCase.kt
│       ├── SearchCityUseCase.kt
│       └── CalculateBikeRidingScoreUseCase.kt
│
├── presentation/                  # Presentation Layer
│   ├── viewmodel/                 # ViewModels
│   │   └── WeatherViewModel.kt
│   ├── screens/                   # Composable screens
│   │   ├── WeatherScreen.kt       # Main weather screen
│   │   ├── HomeScreen.kt          # Home content
│   │   ├── DailyCastScreen.kt     # Hourly details
│   │   ├── SettingsScreen.kt      # Settings screen
│   │   ├── WelcomeScreen.kt       # Onboarding
│   │   ├── LoadingScreen.kt       # Loading state
│   │   └── ErrorScreen.kt         # Error state
│   ├── components/                # Reusable UI components
│   │   ├── BikeRidingCard.kt      # Daily forecast card
│   │   ├── HourlyCard.kt          # Hourly forecast card
│   │   ├── FactorItem.kt          # Score factor display
│   │   ├── CircularProgressBar.kt # Animated progress
│   │   ├── MainTabNavigator.kt    # Bottom navigation
│   │   └── ...
│   └── utils/                     # Utility functions
│
├── di/                            # Dependency Injection
│   └── AppModule.kt               # Koin modules
│
├── navigation/                    # Navigation
│   └── MainActivity.kt            # App entry point
│
└── ui/theme/                      # UI theming
    └── Theme.kt                   # Material 3 theme
```

## 🛠️ Tech Stack

### Core
- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - Declarative UI framework
- **Material 3** - Latest Material Design guidelines
- **Clean Architecture** - Separation of concerns

### Android Jetpack
- **ViewModel** - UI state management
- **Lifecycle** - Lifecycle-aware components
- **DataStore** - Modern data storage solution
- **WorkManager** - Background task scheduling
- **Core SplashScreen** - Splash screen API

### Networking
- **Retrofit** (2.9.0) - Type-safe HTTP client
- **OkHttp** (4.12.0) - HTTP client with logging
- **Gson** (2.10.1) - JSON serialization/deserialization

### Dependency Injection
- **Koin** - Lightweight DI framework

### UI & Animation
- **Lottie** (6.0.0) - JSON-based animations
- **Coil** (2.4.0) - Image loading library

### Location
- **Google Play Services Location** - Location services integration

### Navigation
- **Voyager** (1.1.0-beta02) - Compose navigation library
  - Navigator
  - Tab Navigator
  - Bottom Sheet Navigator

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 33 (Android 13) or higher
- JDK 11 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/indianathe3rdKing/CycleForecast.git
   cd CycleForecast
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the app**
   - Open the project in Android Studio
   - Click Run or press Shift+F10
   - Select an emulator or connected device

### Configuration

The app uses:
- **Open-Meteo API** - For all weather forecast data and city geocoding (free, no API key required)
- **OpenWeather CDN** - For weather icon images only (public CDN, no API key required)

You can configure API endpoints in `data/remote/Config.kt`.

## 📖 Usage

### First Launch
1. Grant location permissions when prompted
2. The app will automatically fetch weather for your current location
3. View your bike riding score on the home screen

### Viewing Forecasts
- **Daily Cards**: Scroll through 7-day forecast cards
- **Hourly Details**: Tap any daily card to see hourly breakdown
- **Best Day**: Look for the highlighted "Best Day for Riding" card
- **Factor Details**: Tap factor items to see animated score breakdowns

### Changing Location
1. Go to Settings tab
2. Toggle "Use Current Location" off
3. Search for a city using the search bar
4. Select from search results

### Managing Notifications
1. Go to Settings tab
2. Toggle "Enable Weather Notifications"
3. Grant notification permissions
4. Optionally disable battery optimization for reliable notifications

### Switching Units
- Toggle between °C/km/h and °F/mph in Settings
- All values update immediately

## 📊 Bike Riding Score Algorithm

The bike riding score (0-100) is calculated using a weighted formula:

```kotlin
score = (temperature * 0.20) +
        (wind * 0.18) +
        (humidity * 0.15) +
        (weather * 0.25) +
        (precipitation * 0.15) +
        (visibility * 0.04) +
        (uvIndex * 0.03)
```

### Score Interpretation
- **80-100**: Excellent - Perfect conditions for cycling
- **60-79**: Good - Great weather for a ride
- **40-59**: Fair - Acceptable but not ideal
- **20-39**: Poor - Challenging conditions
- **0-19**: Very Poor - Not recommended for cycling

## 🔧 Build Configuration

### Gradle
- **Gradle Version**: 8.7+
- **Build System**: Gradle Kotlin DSL
- **Min SDK**: 33 (Android 13)
- **Target SDK**: 36 (Android 15 preview)
- **Compile SDK**: 36
- **Java Version**: 11

### ProGuard
The release build uses ProGuard for code optimization and obfuscation:
```gradle
release {
    isMinifyEnabled = true
    isShrinkResources = true
    proguardFiles(...)
}
```

## 🎨 Design Highlights

- **Dynamic Color Scheme** - Adapts to device theme
- **Smooth Transitions** - Animated state changes
- **Weather-based Visuals** - Icons and colors reflect conditions
- **Score Visualization** - Color-coded progress bars
- **Responsive Design** - Optimized for phones and tablets
- **Accessibility** - Semantic content descriptions

## 🔒 Permissions

The app requires the following permissions:

- **Location** (`ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`) - For current location weather
- **Internet** (`INTERNET`) - For fetching weather data
- **Network State** (`ACCESS_NETWORK_STATE`) - For checking connectivity
- **Post Notifications** (`POST_NOTIFICATIONS`) - For weather alerts (Android 13+)
- **Request Ignore Battery Optimizations** - For reliable background updates

## 🐛 Known Issues & Limitations

- Requires Android 13+ (API 33)
- Location requires Google Play Services
- Background notifications may be affected by aggressive battery optimization
- Some weather APIs have rate limits

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Open-Meteo API** - For free weather forecast data and geocoding services
- **OpenWeather CDN** - For weather icon images
- **Heroicons** - For beautiful weather icons
- **Lottie** - For smooth animations
- **Material Design** - For design guidelines

## 📧 Contact

For questions or support, please open an issue on GitHub.

---

**Made with ❤️ for cyclists**
