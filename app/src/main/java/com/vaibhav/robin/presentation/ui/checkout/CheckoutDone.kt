package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutDone(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.checkout),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = stringResource(id = R.string.navigation_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (maxWidth > 460.dp)
                Box(modifier = Modifier.width(460.dp)) {
                    CheckoutDoneUI {
                        navController.popBackStack()
                    }
                }
            else
                CheckoutDoneUI {
                    navController.popBackStack()
                }
        }
    }
}

@Composable
private fun CheckoutDoneUI(onContinueShopping: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(Dimens.gird_two)
            .verticalScroll(rememberScrollState())
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.confirmed))
        val progress by animateLottieCompositionAsState(composition)
        val dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.primary.toArgb(),
                keyPath = arrayOf(
                    "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
                keyPath = arrayOf(
                    "circle", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.tertiary.toArgb(),
                keyPath = arrayOf(
                    "stars", "**"
                )
            ),
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = MaterialTheme.colorScheme.onPrimary.toArgb(),
                keyPath = arrayOf(
                    "check", "**"
                )
            ),
        )
        LottieAnimation(
            modifier = Modifier.size(300.dp),
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Crop,
            dynamicProperties = dynamicProperties
        )
        SpacerVerticalTwo()
        Text(
            text = stringResource(R.string.your_order_has_been_received),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        SpacerVerticalTwo()
        Text(
            text = stringResource(R.string.checkout_success_msg),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        SpacerVerticalTwo()
        Button(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_bag),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.continue_shopping))
            },
            onClick = onContinueShopping
        )
    }
}

@Preview
@Composable
fun CheckoutDoneUiPreview() {
    RobinAppPreview {
        Box(modifier = Modifier.padding(Dimens.gird_two)) {
            CheckoutDoneUI {}
        }
    }
}