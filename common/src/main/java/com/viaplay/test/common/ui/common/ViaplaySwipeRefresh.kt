package com.viaplay.test.common.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.viaplay.test.common.base.BaseScreenState
import com.viaplay.test.common.base.BaseViewModel

@Composable
fun <TYPE, STATE : BaseScreenState<TYPE, STATE>> ViaplaySwipeRefresh(
    modifier: Modifier = Modifier,
    state: STATE,
    isRefreshing: Boolean = state.base.isRefreshing,
    refresh: () -> Unit,
    mainContent: @Composable () -> Unit,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { refresh.invoke() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state,
                trigger
            )
        },
        modifier = modifier.fillMaxSize()
    ) {
        mainContent()
    }
}