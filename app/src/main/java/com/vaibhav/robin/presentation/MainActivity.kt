package com.vaibhav.robin.presentation

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.google.android.gms.common.internal.Constants
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.theme.RobinTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView.view,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.view.height.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 300L
                slideUp.doOnEnd { splashScreenView.remove() }
                slideUp.start()
            }
        super.onCreate(savedInstanceState)
        if (viewModel.products !is Response.Success)
            viewModel.fetchUiState()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        fun createPaymentsClient(activity: Activity): PaymentsClient {
            val walletOptions = Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_PRODUCTION)
                .build()

            return Wallet.getPaymentsClient(activity, walletOptions)
        }

        setContent {
            val windowSize = calculateWindowSizeClass(this)
            val displayFeatures = calculateDisplayFeatures(this)
            RobinTheme {
                Surface(

                ) {
                    RobinApp(
                        windowSize = windowSize,
                        displayFeatures = displayFeatures,
                        profileUiState = viewModel.profileData,
                        userAuthenticated = viewModel.userAuthenticated,
                        productUiState = viewModel.products,
                        selectedProduct = viewModel.selectedProduct,
                        brandsUiState = viewModel.brands,
                        categoriesUiState = viewModel.categories,
                        filterState = viewModel.filterState,
                        cartItems = viewModel.cartItem,
                        signOut = {
                            viewModel.signOut()
                        },
                        onApply = {
                            viewModel.quarry(it)
                        },
                        onSelectProduct = { product ->
                            viewModel.selectedProduct = product
                        }
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
}
