package com.devbub.cycleforecast.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.devbub.cycleforecast.ui.theme.CardBackground
import com.devbub.cycleforecast.ui.theme.Success


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshPullCircle(
    isRefreshing: Boolean,
    onRefresh:()-> Unit,
   content: @Composable ()-> Unit
){
    val state = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing=isRefreshing,
        onRefresh=onRefresh,
        state=state,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                containerColor = CardBackground,
                color = Success,
                state = state
            )
        }

    ) {
       content()
    }
}