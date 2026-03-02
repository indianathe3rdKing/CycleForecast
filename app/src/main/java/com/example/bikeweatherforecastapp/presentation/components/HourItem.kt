package com.example.bikeweatherforecastapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bikeweatherforecastapp.domain.model.BikeRidingFactor
import com.example.bikeweatherforecastapp.domain.model.HourlyFactor
import com.example.bikeweatherforecastapp.ui.theme.TextPrimary
import com.example.bikeweatherforecastapp.ui.theme.TextTertiary

import com.example.bikeweatherforecastapp.ui.theme.FactorBackground

@Composable
fun HourlyItem(
    factor: HourlyFactor,
    height: Dp
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = FactorBackground
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .width(80.dp)
            .height(height)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Text(
                text = factor.icon,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text=factor.temperature.toString(),
                color=TextPrimary,
                fontSize = 10.sp,
                fontWeight= FontWeight.Medium,
                maxLines=1,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(2.dp))
            Text(
                text=factor.time,
                color=TextTertiary,
                fontSize = 8.sp,
                textAlign = TextAlign.Center,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 10.sp
            )
        }
    }
}

