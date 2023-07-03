package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.ErrorResolutionPolicy
import com.vaibhav.robin.presentation.ErrorVisualsType
import com.vaibhav.robin.presentation.ExceptionHandler
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.RobinTestTags
import com.vaibhav.robin.presentation.UiText
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import com.vaibhav.robin.presentation.util.RobinErrorHandler

@Deprecated("Move To New ErrorHandler Imp Remove in future")
@Composable
fun ShowError(
    exception: Exception,
    retry: () -> Unit
) {
    val errorHandler = remember {
        ExceptionHandler(exception)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.primary.copy(alpha = 0.05f) //TODO:Chenge this
    ) {
        Box {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        horizontal = Dimens.gird_two,
                        vertical = Dimens.gird_four
                    ),
                text = stringResource(id = R.string.app_name),
                style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(Dimens.gird_four),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerVerticalTwo()
                Surface(
                    shape = CircleShape,
                    color = colorScheme.errorContainer
                ) {
                    Icon(
                        imageVector = errorHandler.icon,
                        contentDescription = null,
                        tint = colorScheme.error,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(Dimens.gird_three)
                            .testTag(RobinTestTags.errorVisuals)
                    )
                }
                Text(
                    text = errorHandler.title.asString(),
                    textAlign = TextAlign.Center,
                    style = typography.titleLarge.copy(colorScheme.onSurfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(RobinTestTags.errorTitle)
                )
                SpacerVerticalOne()
                Text(
                    text = errorHandler.message.asString(),
                    textAlign = TextAlign.Center,
                    style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(RobinTestTags.errorMsg)
                )
                SpacerVerticalTwo()
                Button(
                    onClick = retry,
                    modifier = Modifier
                        .testTag(RobinTestTags.errorResolveBtn),
                    content = {
                        Text(text = stringResource(R.string.try_again))
                    }
                )
            }
        }
    }
}

@Composable
fun ShowFullScreenError(
    errorHandler: RobinErrorHandler,
) {
    Surface(
        color = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_5),
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints {
            Text(
                text = stringResource(id = R.string.app_name),
                style = typography.titleLarge.copy(colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        horizontal = Dimens.gird_two,
                        vertical = Dimens.gird_four
                    )
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(Dimens.gird_four)
                    .verticalScroll(rememberScrollState())
            ) {
                val visualsType=errorHandler.getErrorVisualType()
                Box(modifier = Modifier.size(300.dp)) {
                    if (visualsType is ErrorVisualsType.lottieAnimationType) {
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(visualsType.res))
                        val progress by animateLottieCompositionAsState(composition, iterations = visualsType.iterations)
                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = Modifier
                                .size(360.dp)
                                .testTag(RobinTestTags.errorVisuals),
                        )
                    }
                    else
                        Surface(
                            shape = CircleShape,
                            color = colorScheme.errorContainer
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check_circle),
                                contentDescription = null,
                                tint = colorScheme.error,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(64.dp)
                                    .testTag(RobinTestTags.errorVisuals)
                            )
                        }
                }
                SpacerVerticalTwo()
                Text(
                    text = errorHandler.getTitle().asString(),
                    textAlign = TextAlign.Center,
                    style = typography.titleLarge.copy(colorScheme.onSurfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(RobinTestTags.errorTitle)
                )
                SpacerVerticalOne()
                Text(
                    text = errorHandler.getMessage().asString(),
                    textAlign = TextAlign.Center,
                    style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag(RobinTestTags.errorMsg)
                )
                SpacerVerticalTwo()
                ErrorResolutionPolicyUI(resolutionPolicy = errorHandler.getErrorResolutionPolicy())
            }
        }
    }
}

@Composable
fun ErrorResolutionPolicyUI(resolutionPolicy: ErrorResolutionPolicy) {
    val onclick:()->Unit =remember {
        when (resolutionPolicy) {
            is ErrorResolutionPolicy.AttemptLater -> resolutionPolicy.onClick
            is ErrorResolutionPolicy.Restart -> resolutionPolicy.onClick
            is ErrorResolutionPolicy.Retry -> resolutionPolicy.onClick
            is ErrorResolutionPolicy.Support -> resolutionPolicy.onClick
        }
    }
    val drawableRes=remember {
        when (resolutionPolicy) {
            is ErrorResolutionPolicy.AttemptLater -> R.drawable.check_circle
            is ErrorResolutionPolicy.Restart -> R.drawable.check_circle
            is ErrorResolutionPolicy.Retry -> R.drawable.check_circle
            is ErrorResolutionPolicy.Support -> R.drawable.check_circle
        }
    }
    val stringRes =remember {
        when (resolutionPolicy) {
            is ErrorResolutionPolicy.AttemptLater ->R.string.try_again
            is ErrorResolutionPolicy.Restart -> R.string.try_again
            is ErrorResolutionPolicy.Retry -> R.string.try_again
            is ErrorResolutionPolicy.Support -> R.string.try_again
        }
    }
    Button(
        onClick = onclick,
        modifier = Modifier
            .testTag(RobinTestTags.errorResolveBtn),
        content = {
            Icon(
                painter = painterResource(id = drawableRes),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(stringRes))
        }
    )
}

@Composable
private fun ErrorVisuals(visualsType: ErrorVisualsType) {

}


@Preview
@Composable
fun ErrorPreview() {
    RobinAppPreview {
       ShowFullScreenError(errorHandler = object :RobinErrorHandler{
           override fun getTitle(): UiText =
             UiText.DynamicString("This is Error title")


           override fun getMessage(): UiText =
               UiText.DynamicString("This is error message. it tell about more about resolve the error and Resolution action")


           override fun getErrorResolutionPolicy(): ErrorResolutionPolicy =
              ErrorResolutionPolicy.Retry {}


           override fun getErrorVisualType(): ErrorVisualsType? =ErrorVisualsType.lottieAnimationType(R.raw.trex,
               Int.MAX_VALUE,null)

       })
    }
}