package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devbub.cycleforecast.presentation.viewmodel.WeatherViewModel
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.Success
import com.devbub.cycleforecast.ui.theme.SuccessLight
import com.devbub.cycleforecast.ui.theme.TextPrimary
import com.devbub.cycleforecast.ui.theme.TextSecondary
import com.devbub.cycleforecast.ui.theme.TextTertiary


@Composable
fun SearchInput(
    viewModel: WeatherViewModel
) {
    var query by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    fun performSearch() {
        if (query.isNotBlank()) {
            viewModel.searchCity(query)
            focusManager.clearFocus()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(CardBackground.copy(alpha = 0.7f))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Search city...",
                    color = TextTertiary,
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary,
                cursorColor = SuccessLight,
                focusedBorderColor = Success,
                unfocusedBorderColor = TextTertiary.copy(alpha = 0.5f),
                focusedContainerColor = Color(0xFF0F172A).copy(alpha = 0.6f),
                unfocusedContainerColor = Color(0xFF0F172A).copy(alpha = 0.4f)
            ),
            textStyle = TextStyle(
                color = TextPrimary,
                fontSize = 14.sp
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { performSearch() })
        )


    }
}