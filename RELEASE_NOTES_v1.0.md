# CycleForecast v1.0 - Initial Public Release 🚴‍♂️

## 📱 What's New

This is the first public release of **CycleForecast**, a weather forecast app specifically designed for cyclists!

## ✨ Features

### Weather Intelligence
- **7-Day Forecast** with bike riding suitability scores
- **Hourly Breakdown** for detailed planning
- **Smart Scoring System** analyzing 7 weather factors:
  - Temperature (20% weight)
  - Wind Speed (18% weight)
  - Weather Conditions (25% weight)
  - Precipitation (15% weight)
  - Humidity (15% weight)
  - Visibility (4% weight)
  - UV Index (3% weight)

### User Experience
- 🎨 **Beautiful Material 3 UI** with smooth animations
- 🗺️ **Location-based** automatic weather detection
- 🔍 **City Search** for multiple locations
- 📊 **Visual Score Indicators** with color-coded progress bars
- ⭐ **Best Day Highlight** automatically shows optimal riding day
- 🔔 **Daily Notifications** with weather alerts

### Customization
- 🌡️ **Unit Toggle**: Metric (°C, km/h) or Imperial (°F, mph)
- 🔕 **Notification Control**: Enable/disable daily alerts
- 🔋 **Battery Optimization**: Manage for reliable background updates
- 📍 **Location Options**: Current location or saved city

### Technical Highlights
- Clean Architecture with MVVM pattern
- Jetpack Compose UI
- Open-Meteo API for weather data (no API key required)
- Background updates with WorkManager
- Modern Material 3 design
- Smooth Lottie animations

## 📥 Installation

**Requirements:**
- Android 13 (API 33) or higher
- Location permissions for weather data
- Internet connection

**Steps:**
1. Download the `cycleforecast.apk` file attached below
2. Enable "Install from Unknown Sources" in your device settings if prompted
3. Open the downloaded APK file
4. Follow the installation prompts
5. Grant necessary permissions when launching the app

## 🎯 How to Use

1. **First Launch**: Grant location permissions and the app will fetch your local weather
2. **View Forecasts**: Scroll through daily cards to see 7-day forecast
3. **Hourly Details**: Tap any daily card to see hour-by-hour breakdown
4. **Best Day**: Look for the highlighted "Best Day for Riding" card
5. **Change Location**: Use Settings tab to search for different cities
6. **Customize**: Toggle units, notifications, and other preferences in Settings

## 🐛 Known Issues

- Requires Android 13+ (API 33 minimum)
- Background notifications may be affected by aggressive battery optimization
- Location requires Google Play Services

## 🙏 Credits

- **Open-Meteo API** - Weather data and geocoding
- **OpenWeather CDN** - Weather icon images
- **Heroicons** - Beautiful weather icons
- **Lottie** - Smooth animations

## 📊 Stats

- **Version**: 1.0
- **Version Code**: 1
- **Min SDK**: 33 (Android 13)
- **Target SDK**: 36 (Android 15)
- **Package**: com.devbub.cycleforecast
- **APK Size**: ~14 MB

---

**Made with ❤️ for cyclists**

For bug reports or feature requests, please open an issue on GitHub.

