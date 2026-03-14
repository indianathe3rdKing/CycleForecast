package com.devbub.cycleforecast.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextSecondary
import com.devbub.cycleforecast.ui.theme.TextTertiary

@Composable
fun WelcomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text="🚴"
            , fontSize = 64.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text="Bike Weather",
            color=TextPrimary,
            fontSize = 32.sp,
            fontWeight=FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text="Find the perfect day for cycling",
            color=TextSecondary,
            fontSize = 18.sp
        )
        Spacer(Modifier.height(24.dp))
        Text(
            text="We'll analysis weather conditions and recommend the best days for your bike rides!",
            color=TextTertiary,
            fontSize=14.sp,
            textAlign= TextAlign.Center
        )
    }
}