package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.domain.model.Setting
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.CyanAccent
import com.devbub.cycleforecast.ui.theme.Success
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextTertiary

/**
 * Reusable settings item component that can display:
 * - Toggle switch settings
 * - Clickable action settings
 */
@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    iconTint: Color = CyanAccent,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Card(
        onClick={onClick ?: {}},
        enabled = onClick != null,
        modifier = Modifier
            .fillMaxWidth()
            ,
        colors = CardDefaults.cardColors(
            containerColor = CardBackground,
            disabledContainerColor = CardBackground
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp
            , disabledElevation = 12.dp
        )
        ,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = subtitle,
                        color = TextTertiary,
                        fontSize = 12.sp
                    )
                }
            }

            if (trailing != null) {
                trailing()
            }
        }
    }
}

/**
 * Settings item with a toggle switch
 */
@Composable
fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String? = null,
    iconTint: Color = Success,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    SettingsItem(
        icon = icon,
        title = title,
        subtitle = subtitle,
        iconTint = iconTint,
        trailing = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Success,
                    checkedTrackColor = Success.copy(alpha = 0.3f),
                    uncheckedThumbColor = TextTertiary,
                    uncheckedTrackColor = CardBackground
                )
            )
        }
    )
}

/**
 * Settings item with a clickable action (e.g., navigation arrow or button)
 */
@Composable
fun SettingsActionItem(
    setting: Setting
) {
    SettingsItem(
        icon = setting.icon,
        title = setting.title,
        subtitle = setting.subtitle,
        iconTint = setting.iconTint,
        onClick = setting.onClick,
        trailing = if (setting.actionIcon != null) {
            {
                Icon(
                    imageVector = setting.actionIcon,
                    contentDescription = null,
                    tint = setting.actionIconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
        } else null
    )
}


