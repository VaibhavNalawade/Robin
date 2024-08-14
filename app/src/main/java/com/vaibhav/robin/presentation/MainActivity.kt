package com.vaibhav.robin.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.common.splashFinishAnimation
import com.vaibhav.robin.presentation.ui.theme.RobinTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S)
            splashFinishAnimation(splashScreen)

        super.onCreate(savedInstanceState)

        if (viewModel.products !is Response.Success)
            viewModel.fetchUiState()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val windowSize = calculateWindowSizeClass(this)
            RobinTheme {
                Surface {
                    RobinApp(
                        windowSize = windowSize,
                        currentUserProfileData = viewModel.currentUserProfileData,
                        productUiState = viewModel.products,
                        categoriesUiState = viewModel.categories,
                        orders = viewModel.orders,
                        brandsUiState = viewModel.brands,
                        filterState = viewModel.filterState,
                        selectedProduct = viewModel.selectedProduct,
                        cartUiState = viewModel.cartUiState,
                        onApply = {
                            viewModel.quarry(it)
                        },
                        onSelectProduct = { product ->
                            viewModel.selectedProduct = product
                        },
                        signOut = {
                            viewModel.signOut()
                        }
                    )
                }
            }
        }
        val content: View = findViewById(android.R.id.content)
        var wait =false
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (viewModel.products !is Response.Loading) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else false
                }
            }
        )
    }
}
