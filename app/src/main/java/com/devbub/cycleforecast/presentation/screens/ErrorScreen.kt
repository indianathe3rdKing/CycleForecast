package com.devbub.cycleforecast.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.R
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextTertiary

@Composable
fun ErrorScreen(error: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter=painterResource(R.drawable.error_cyclist),
            contentDescription = "Lost person image with broken cycle",
            modifier = Modifier.size(400.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Oops! Something went wrong",
            color = TextPrimary.copy(0.9f),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            color = TextTertiary,
            fontSize = 14.sp,
            fontWeight = FontWeight.Thin, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        ElevatedButton(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = CardBackground,
                contentColor = TextPrimary
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {
            Text(
                "Try Again", fontWeight = FontWeight.SemiBold,
                style = TextStyle(
                    shadow = Shadow(
                        color = TextPrimary.copy(0.6f),
                        offset = Offset(0f, 0f),
                        blurRadius = 4f
                    )
                )
            )

        }
    }
}