package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.devbub.cycleforecast.ui.icons.HeroIcons
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.devbub.cycleforecast.domain.model.WeatherResponse
import com.devbub.cycleforecast.presentation.screens.HomeScreen
import com.devbub.cycleforecast.presentation.screens.SettingsScreen
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.FactorBackground
import com.devbub.cycleforecast.ui.theme.Success
import com.devbub.cycleforecast.ui.theme.SuccessLight
import com.devbub.cycleforecast.ui.theme.TextTertiary
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainTabNavigator(
    weatherData: WeatherResponse,
    viewModel: WeatherViewModel= koinViewModel()
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val navColor = CardBackground.copy(0.9f)
    val weatherState by viewModel.weatherState

    Scaffold(
        containerColor = Color.Transparent,
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(
                        start = innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                        top =
                            innerPadding.calculateTopPadding(),
                        end = innerPadding.calculateEndPadding(
                            LayoutDirection.Ltr
                        ),
                        bottom = 0.dp
                    )
                    .fillMaxSize()
            ) {
                when (selectedTabIndex) {
                    0 -> RefreshPullCircle( weatherState.isLoading,{viewModel.checkLocationPermission()} ,{ HomeScreen(weatherData,viewModel) })
                    1 -> SettingsScreen()
                }
            }
        },
        bottomBar = {
            Surface(
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                color = CardBackground
            ) {
                NavigationBar(
                    containerColor = navColor,
                    tonalElevation = 2.dp,
                    modifier = Modifier.shadow(2.dp)
                        .border(
                            1.dp,
                            TextTertiary.copy(0.5f),
                            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp)
                        )


                ) {
                    TabNavigationItem(
                        title = "Home",
                        icon = HeroIcons.Home,
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 }
                    )
                    TabNavigationItem(
                        title = "Settings",
                        icon = HeroIcons.Cog6Tooth,
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 }
                    )
                }
            }
        }
    )
}

@Composable
private fun RowScope.TabNavigationItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = title
            )
        },
        label = { Text(title) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Success,
            selectedTextColor = SuccessLight,
            unselectedIconColor = TextTertiary,
            unselectedTextColor = TextTertiary,
            indicatorColor = FactorBackground
        )
    )
}
