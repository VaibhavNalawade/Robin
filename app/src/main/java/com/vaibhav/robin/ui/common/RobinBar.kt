package com.vaibhav.robin.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.vaibhav.robin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = { Text(stringResource(id = R.string.app_name)) },
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable (RowScope.() -> Unit) = {},
    fab: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()),
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val backgroundColors = TopAppBarDefaults.centerAlignedTopAppBarColors()
    val backgroundColor = backgroundColors.containerColor(scrollBehavior.state.collapsedFraction
    ).value
    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Surface(color = backgroundColor) {
                CenterAlignedTopAppBar(
                    modifier = modifier,
                    title = title,
                    navigationIcon = navigationIcon,
                    scrollBehavior = scrollBehavior,
                    actions = actions
                )
            }
        }, floatingActionButton = fab,
        snackbarHost = snackbarHost,
        content = { content.invoke() }
    )
}