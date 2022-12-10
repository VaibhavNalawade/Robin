package com.vaibhav.robin.presentation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.navigation.RobinNavHost
import com.vaibhav.robin.presentation.ui.common.ContentWithMessageBar
import com.vaibhav.robin.presentation.ui.common.DrawerContent
import com.vaibhav.robin.presentation.ui.common.MessageBarPosition
import com.vaibhav.robin.presentation.ui.common.rememberMessageBarState
import com.vaibhav.robin.presentation.ui.theme.RobinTheme
import kotlinx.coroutines.launch

@Composable
fun RobinApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    signOut: () -> Unit,
    userAuthenticated: Boolean,
    profileUiState: ProfileData?,
    productUiState: Response<List<Product>>
) {
    val navigationType: RobinNavigationType = when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            RobinNavigationType.NAVIGATION_DRAWER
        }

        WindowWidthSizeClass.Medium -> {
            RobinNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        WindowWidthSizeClass.Expanded -> {
            RobinNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            RobinNavigationType.NAVIGATION_DRAWER
        }
    }
    RobinNavigationWrapper(
        navigationType = navigationType,
        profileUiState = profileUiState,
        signOut = signOut,
        userAuthenticated = userAuthenticated,
        productUiState = productUiState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinNavigationWrapper(
    navigationType: RobinNavigationType,
    profileUiState: ProfileData?,
    signOut: () -> Unit,
    userAuthenticated: Boolean,
    productUiState: Response<List<Product>>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val state = rememberMessageBarState()
    val messageBarPosition =
        if (navigationType == RobinNavigationType.PERMANENT_NAVIGATION_DRAWER) MessageBarPosition.TOP
        else MessageBarPosition.BOTTOM

    val closeDrawer: () -> Unit = {
        scope.launch {
            drawerState.close()
        }
    }
    val toggleDrawer: () -> Unit = {
        scope.launch {
            if (drawerState.isClosed)
                drawerState.open()
            else drawerState.close()
        }
    }

    if (navigationType == RobinNavigationType.PERMANENT_NAVIGATION_DRAWER)
        PermanentNavigationDrawer(
            drawerContent = {
                DrawerContent(
                    userAuthenticated = userAuthenticated,
                    navController = navController,
                    signOut = signOut,
                    closeDrawer = closeDrawer
                )
            },
            content = {
                MessageBarWrapper(
                    state = state,
                    position = messageBarPosition,
                    content = {
                        RobinNavHost(
                            navController = navController,
                            profileUiState = profileUiState,
                            toggleDrawer = toggleDrawer,
                            productUiState = productUiState,
                            messageBarState = state
                        )
                    }
                )
            }
        )
    else
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    DrawerContent(
                        userAuthenticated = userAuthenticated,
                        navController = navController,
                        signOut = signOut,
                        closeDrawer = closeDrawer
                    )
                }
            },
            content = {
                MessageBarWrapper(
                    state = state,
                    position = messageBarPosition,
                    content = {
                        RobinNavHost(
                            navController = navController,
                            profileUiState = profileUiState,
                            toggleDrawer = toggleDrawer,
                            productUiState = productUiState,
                            messageBarState = state
                        )
                    }
                )
            },
            drawerState = drawerState
        )
}

@Composable
fun MessageBarWrapper(
    state: MessageBarState,
    content: @Composable () -> Unit,
    position: MessageBarPosition
) {
    ContentWithMessageBar(
        messageBarState = state,
        position = position
    ) {
        content()
    }
}

@Composable
fun RobinAppPreview(content: @Composable () -> Unit) {
    RobinTheme {
        content()
    }
}