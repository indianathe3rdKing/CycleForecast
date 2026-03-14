package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.ui.theme.FactorBackground
import com.devbub.cycleforecast.ui.theme.SuccessLight
import com.devbub.cycleforecast.ui.theme.TextSecondary

@Composable
fun UnitToggleButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) SuccessLight else FactorBackground,
            contentColor = if (isSelected) Color.Black else TextSecondary
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}