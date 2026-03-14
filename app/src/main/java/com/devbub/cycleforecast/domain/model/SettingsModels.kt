package com.devbub.cycleforecast.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.devbub.cycleforecast.ui.theme.CyanAccent
import com.devbub.cycleforecast.ui.theme.TextTertiary

data class Setting(
    val icon: ImageVector,
    val title: String,
    val subtitle: String? = null,
    val iconTint: Color = CyanAccent,
    val actionIcon: ImageVector? = null,
    val actionIconTint: Color = TextTertiary,
    val onClick: () -> Unit
)