package com.devbub.cycleforecast.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object HeroIcons {

    val Home: ImageVector
        get() = ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // m2.25 12 8.954-8.955c.44-.439 1.152-.439 1.591 0L21.75 12
                moveTo(2.25f, 12f)
                lineToRelative(8.954f, -8.955f)
                curveToRelative(0.44f, -0.439f, 1.152f, -0.439f, 1.591f, 0f)
                lineTo(21.75f, 12f)

                // M4.5 9.75v10.125c0 .621.504 1.125 1.125 1.125H9.75
                moveTo(4.5f, 9.75f)
                verticalLineToRelative(10.125f)
                curveToRelative(0f, 0.621f, 0.504f, 1.125f, 1.125f, 1.125f)
                horizontalLineTo(9.75f)

                // v-4.875c0-.621.504-1.125 1.125-1.125h2.25c.621 0 1.125.504 1.125 1.125V21
                verticalLineToRelative(-4.875f)
                curveToRelative(0f, -0.621f, 0.504f, -1.125f, 1.125f, -1.125f)
                horizontalLineToRelative(2.25f)
                curveToRelative(0.621f, 0f, 1.125f, 0.504f, 1.125f, 1.125f)
                verticalLineTo(21f)

                // h4.125c.621 0 1.125-.504 1.125-1.125V9.75
                horizontalLineToRelative(4.125f)
                curveToRelative(0.621f, 0f, 1.125f, -0.504f, 1.125f, -1.125f)
                verticalLineTo(9.75f)

                // M8.25 21h8.25
                moveTo(8.25f, 21f)
                horizontalLineToRelative(8.25f)
            }
        }.build()

    val Cog6Tooth: ImageVector
        get() = ImageVector.Builder(
            name = "Settings",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // Settings gear icon path
                moveTo(9.594f, 3.94f)
                curveToRelative(0.09f, -0.542f, 0.56f, -0.94f, 1.11f, -0.94f)
                horizontalLineToRelative(2.593f)
                curveToRelative(0.55f, 0f, 1.02f, 0.398f, 1.11f, 0.94f)
                lineToRelative(0.213f, 1.281f)
                curveToRelative(0.063f, 0.374f, 0.313f, 0.686f, 0.645f, 0.87f)
                curveToRelative(0.074f, 0.04f, 0.147f, 0.083f, 0.22f, 0.127f)
                curveToRelative(0.325f, 0.196f, 0.72f, 0.257f, 1.075f, 0.124f)
                lineToRelative(1.217f, -0.456f)
                curveToRelative(0.52f, -0.195f, 1.095f, 0.026f, 1.37f, 0.526f)
                lineToRelative(1.296f, 2.247f)
                curveToRelative(0.275f, 0.5f, 0.166f, 1.118f, -0.26f, 1.466f)
                lineToRelative(-1.004f, 0.827f)
                curveToRelative(-0.292f, 0.24f, -0.437f, 0.613f, -0.43f, 0.991f)
                curveToRelative(0.004f, 0.168f, 0.004f, 0.337f, 0f, 0.505f)
                curveToRelative(-0.007f, 0.378f, 0.138f, 0.75f, 0.43f, 0.99f)
                lineToRelative(1.004f, 0.828f)
                curveToRelative(0.426f, 0.348f, 0.535f, 0.965f, 0.26f, 1.465f)
                lineToRelative(-1.296f, 2.247f)
                curveToRelative(-0.275f, 0.5f, -0.85f, 0.721f, -1.37f, 0.526f)
                lineToRelative(-1.217f, -0.456f)
                curveToRelative(-0.355f, -0.133f, -0.75f, -0.072f, -1.076f, 0.124f)
                curveToRelative(-0.072f, 0.044f, -0.146f, 0.086f, -0.22f, 0.127f)
                curveToRelative(-0.331f, 0.183f, -0.581f, 0.495f, -0.644f, 0.869f)
                lineToRelative(-0.213f, 1.281f)
                curveToRelative(-0.09f, 0.543f, -0.56f, 0.94f, -1.11f, 0.94f)
                horizontalLineToRelative(-2.593f)
                curveToRelative(-0.55f, 0f, -1.019f, -0.398f, -1.11f, -0.94f)
                lineToRelative(-0.212f, -1.281f)
                curveToRelative(-0.063f, -0.374f, -0.313f, -0.686f, -0.645f, -0.869f)
                curveToRelative(-0.073f, -0.041f, -0.147f, -0.083f, -0.22f, -0.127f)
                curveToRelative(-0.325f, -0.196f, -0.72f, -0.257f, -1.076f, -0.124f)
                lineToRelative(-1.217f, 0.456f)
                curveToRelative(-0.52f, 0.195f, -1.094f, -0.026f, -1.369f, -0.526f)
                lineToRelative(-1.296f, -2.247f)
                curveToRelative(-0.276f, -0.5f, -0.166f, -1.117f, 0.259f, -1.465f)
                lineToRelative(1.004f, -0.828f)
                curveToRelative(0.292f, -0.24f, 0.437f, -0.612f, 0.43f, -0.99f)
                curveToRelative(-0.003f, -0.168f, -0.003f, -0.337f, 0f, -0.504f)
                curveToRelative(0.007f, -0.379f, -0.138f, -0.751f, -0.43f, -0.991f)
                lineToRelative(-1.004f, -0.827f)
                curveToRelative(-0.425f, -0.348f, -0.535f, -0.966f, -0.259f, -1.466f)
                lineToRelative(1.296f, -2.247f)
                curveToRelative(0.275f, -0.5f, 0.849f, -0.721f, 1.369f, -0.526f)
                lineToRelative(1.217f, 0.456f)
                curveToRelative(0.356f, 0.133f, 0.751f, 0.072f, 1.076f, -0.124f)
                curveToRelative(0.072f, -0.044f, 0.146f, -0.087f, 0.22f, -0.127f)
                curveToRelative(0.332f, -0.184f, 0.582f, -0.496f, 0.645f, -0.87f)
                lineToRelative(0.212f, -1.281f)
                close()
            }
            // Inner circle
            path(
                fill = null,
                stroke = SolidColor(Color.Black),
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                moveTo(15f, 12f)
                arcToRelative(3f, 3f, 0f, true, true, -6f, 0f)
                arcToRelative(3f, 3f, 0f, false, true, 6f, 0f)
                close()
            }
        }.build()
}


