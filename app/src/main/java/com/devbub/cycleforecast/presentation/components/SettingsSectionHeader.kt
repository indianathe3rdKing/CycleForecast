package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.ui.theme.TextTertiary

@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        color = TextTertiary,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(start = 4.dp)
    )
}