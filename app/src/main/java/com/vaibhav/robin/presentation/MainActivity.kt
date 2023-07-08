package com.vaibhav.robin.presentation

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.vaibhav.robin.domain.model.Response
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
            RobinTheme() {
                Surface {
                    RobinApp(
                        windowSize = windowSize,
                        signOut = {
                            viewModel.signOut()
                        },
                        userAuthenticated = viewModel.userAuthenticated,
                        profileUiState = viewModel.profileData,
                        productUiState = viewModel.products,
                        categoriesUiState = viewModel.categories,
                        brandsUiState = viewModel.brands,
                        onApply = {
                            viewModel.quarry(it)
                        },
                        filterState = viewModel.filterState,
                        selectedProduct = viewModel.selectedProduct,
                        onSelectProduct = { product ->
                            viewModel.selectedProduct = product
                        },
                        cartUiState = viewModel.cartUiState,
                        orders = viewModel.orders
                    )
                }
            }
        }
        val content: View = findViewById(android.R.id.content)
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

    private fun splashFinishAnimation(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val x: Int = splashScreenView.view.right / 2
            val y: Int = splashScreenView.view.bottom * 2

            val startRadius = splashScreenView.view.width
                .coerceAtLeast((splashScreenView.view.height)) * 2f
            val endRadius = startRadius / 2f

            val revealAnim = ViewAnimationUtils
                .createCircularReveal(
                    splashScreenView.view,
                    x,
                    y,
                    startRadius,
                    endRadius
                )
            val animator = ObjectAnimator.ofFloat(
                splashScreenView.view,
                "alpha",
                1f,
                0f
            )
            animator.duration = 600L
            revealAnim.duration = 600L
            revealAnim.doOnEnd { splashScreenView.remove() }
            revealAnim.doOnStart {
                splashScreenView.iconView.visibility = View.INVISIBLE
                animator.start()
            }
            revealAnim.start()
        }
    }
}
