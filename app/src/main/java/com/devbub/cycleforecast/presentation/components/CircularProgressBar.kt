package com.devbub.cycleforecast.presentation.components


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.ProgressBackground


@Composable
fun CircularProgressBar(
    score: Int, strokeWidth: Int, color: Color,
    modifier: Modifier = Modifier, fontSize: Int = 14
) {

    val progress = remember { Animatable(0f) }
    val animatedScore = remember { Animatable(0f) }

    LaunchedEffect(score) {
        progress.snapTo(0f)
        animatedScore.snapTo(0f)
        progress.animateTo(
            targetValue = score / 100f * 360f,
            animationSpec = tween(durationMillis = 700, easing = LinearEasing)
        )
        animatedScore.animateTo(
            targetValue = score.toFloat(),
            animationSpec = tween(durationMillis = 700, easing = LinearEasing)
        )
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val strokeWidth = strokeWidth.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2

            //Background circle
            drawCircle(
                color = ProgressBackground,
                radius = radius,
                style = Stroke(width = strokeWidth)
            )

            //Progress circle
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = progress.value,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${animatedScore.value.toInt()}%",
            color = TextPrimary,
            fontSize = fontSize.sp,
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                shadow = Shadow(
                    color = TextPrimary.copy(0.6f),
                    offset = Offset(0f,0f),
                    blurRadius = 10f
                )
            )
        )
    }
}