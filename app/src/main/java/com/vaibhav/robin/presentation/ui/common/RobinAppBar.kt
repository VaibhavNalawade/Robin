package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vaibhav.robin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinAppBar(
    modifier: Modifier=Modifier,
    title: String,
    onBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior?=null,
    content: @Composable (PaddingValues) -> Unit
) {
    if (scrollBehavior!=null) modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    Scaffold(
        modifier =modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = stringResource(id = R.string.navigation_back)
                            )
                        },
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(titleContentColor = Color.Blue),
                scrollBehavior = scrollBehavior
            )
        },
        content = content
    )
}