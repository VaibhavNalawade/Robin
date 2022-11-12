package com.vaibhav.robin.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vaibhav.robin.presentation.navigation.RobinNavHost
import com.vaibhav.robin.presentation.ui.theme.RobinTheme

@Composable
fun RobinAppScaffold() {
    RobinTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            val navController = rememberNavController()
            RobinNavHost(navController)
        }
    }
}

@Composable
fun RobinAppPreviewScaffold(content: @Composable () -> Unit) {
    RobinTheme {
        Surface {
            content()
        }
    }
}