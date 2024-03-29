package com.vaibhav.robin.presentation.ui.common

    import androidx.compose.animation.AnimatedVisibility
    import androidx.compose.animation.EnterTransition
    import androidx.compose.animation.ExitTransition
    import androidx.compose.animation.animateContentSize
    import androidx.compose.animation.core.tween
    import androidx.compose.animation.expandVertically
    import androidx.compose.animation.shrinkVertically
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.navigationBarsPadding
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.statusBarsPadding
    import androidx.compose.foundation.layout.width
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Check
    import androidx.compose.material.icons.filled.Warning
    import androidx.compose.material3.Icon
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.DisposableEffect
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberUpdatedState
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.vector.ImageVector
    import androidx.compose.ui.platform.LocalClipboardManager
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.platform.testTag
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.Dp
    import androidx.compose.ui.unit.dp
    import com.vaibhav.robin.presentation.models.state.MessageBarState
    import com.vaibhav.robin.presentation.ui.common.TestTags.MESSAGE_BAR
    import com.vaibhav.robin.presentation.ui.common.TestTags.MESSAGE_BAR_TEXT
    import com.vaibhav.robin.presentation.ui.theme.Values
    import java.util.Timer
    import kotlin.concurrent.schedule

@Composable
    fun rememberMessageBarState(): MessageBarState {
        return remember { MessageBarState() }
    }

    enum class MessageBarPosition {
        TOP,
        BOTTOM
    }

    @Composable
    fun ContentWithMessageBar(
        modifier: Modifier = Modifier,
        messageBarState: MessageBarState,
        position: MessageBarPosition = MessageBarPosition.TOP,
        visibilityDuration: Long = 3000L,
        showToastOnCopy: Boolean = false,
        successIcon: ImageVector = Icons.Default.Check,
        errorIcon: ImageVector = Icons.Default.Warning,
        contentBackgroundColor: Color = MaterialTheme.colorScheme.surface,
        successContainerColor: Color = MaterialTheme.colorScheme.primaryContainer,
        successContentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        errorContainerColor: Color = MaterialTheme.colorScheme.errorContainer,
        errorContentColor: Color = MaterialTheme.colorScheme.onErrorContainer,
        enterAnimation: EnterTransition = expandVertically(
            animationSpec = tween(durationMillis = 300),
            expandFrom = if (position == MessageBarPosition.TOP)
                Alignment.Top else Alignment.Bottom
        ),
        exitAnimation: ExitTransition = shrinkVertically(
            animationSpec = tween(durationMillis = 300),
            shrinkTowards = if (position == MessageBarPosition.TOP)
                Alignment.Top else Alignment.Bottom
        ),
        verticalPadding: Dp = 12.dp,
        horizontalPadding: Dp = 12.dp,
        content: @Composable () -> Unit
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = contentBackgroundColor)
        ) {
            content()
            MessageBarComponent(
                messageBarState = messageBarState,
                position = position,
                visibilityDuration = visibilityDuration,
                successIcon = successIcon,
                errorIcon = errorIcon,
                successContainerColor = successContainerColor,
                successContentColor = successContentColor,
                errorContainerColor = errorContainerColor,
                errorContentColor = errorContentColor,
                enterAnimation = enterAnimation,
                exitAnimation = exitAnimation,
                verticalPadding = verticalPadding,
                horizontalPadding = horizontalPadding,
                showToastOnCopy = showToastOnCopy
            )
        }
    }

    @Composable
    private fun MessageBarComponent(
        messageBarState: MessageBarState,
        position: MessageBarPosition,
        visibilityDuration: Long,
        successIcon: ImageVector,
        errorIcon: ImageVector,
        successContainerColor: Color,
        successContentColor: Color,
        errorContainerColor: Color,
        errorContentColor: Color,
        enterAnimation: EnterTransition,
        exitAnimation: ExitTransition,
        verticalPadding: Dp,
        horizontalPadding: Dp,
        showToastOnCopy: Boolean
    ) {
        var showMessageBar by remember { mutableStateOf(false) }
        val error by rememberUpdatedState(newValue = messageBarState.error)
        val message by rememberUpdatedState(newValue = messageBarState.success)

        DisposableEffect(key1 = messageBarState.updated) {
            showMessageBar = true
            val timer = Timer("Animation Timer", true)
            timer.schedule(visibilityDuration) {
                showMessageBar = false
            }
            onDispose {
                timer.cancel()
                timer.purge()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            verticalArrangement = if (position == MessageBarPosition.TOP)
                Arrangement.Top else Arrangement.Bottom
        ) {
            AnimatedVisibility(
                visible = messageBarState.error != null && showMessageBar
                        || messageBarState.success != null && showMessageBar,
                enter = enterAnimation,
                exit = exitAnimation
            ) {
                MessageBar(
                    message = message?.asString(),
                    error = error?.asString(),
                    successIcon = successIcon,
                    errorIcon = errorIcon,
                    successContainerColor = successContainerColor,
                    successContentColor = successContentColor,
                    errorContainerColor = errorContainerColor,
                    errorContentColor = errorContentColor,
                    verticalPadding = verticalPadding,
                    horizontalPadding = horizontalPadding,
                    showToastOnCopy = showToastOnCopy
                )
                if (position == MessageBarPosition.BOTTOM)
                    Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }

    @Composable
    private fun MessageBar(
        message: String?,
        error: String?,
        successIcon: ImageVector,
        errorIcon: ImageVector,
        successContainerColor: Color,
        successContentColor: Color,
        errorContainerColor: Color,
        errorContentColor: Color,
        verticalPadding: Dp,
        horizontalPadding: Dp,
        showToastOnCopy: Boolean,
    ) {
        val clipboardManager = LocalClipboardManager.current
        val context = LocalContext.current

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (error != null) errorContainerColor
                    else successContainerColor
                )
                .padding(vertical = if (error != null) 0.dp else verticalPadding)
                .padding(horizontal = horizontalPadding)
                .animateContentSize()
                .testTag(MESSAGE_BAR),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(4f)
                    .navigationBarsPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector =
                    if (error != null) errorIcon
                    else successIcon,
                    contentDescription = "Message Bar Icon",
                    tint = if (error != null) errorContentColor
                    else successContentColor
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    modifier = Modifier
                        .padding(horizontal = Values.Dimens.gird_one)
                        .padding(top = Values.Dimens.gird_one)
                        .testTag(tag = MESSAGE_BAR_TEXT),
                    text = message ?: (error ?: "Unknown"),
                    color = if (error != null) errorContentColor
                    else successContentColor,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2
                )
            }

        }
    }
 object TestTags {
    const val MESSAGE_BAR = "MessageBar"
    const val MESSAGE_BAR_TEXT = "MessageBarText"
}

    @Composable
    @Preview
    private fun MessageBarPreview() {
        MessageBar(
            message = "Successfully Updated.",
            error = null,
            showToastOnCopy = false,
            successIcon = Icons.Default.Check,
            errorIcon = Icons.Default.Warning,
            successContainerColor = MaterialTheme.colorScheme.primaryContainer,
            successContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            errorContentColor = MaterialTheme.colorScheme.onErrorContainer,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp
        )
    }

    @Composable
    @Preview
    private fun MessageBarErrorPreview() {
        MessageBar(
            message = null,
            error = "Internet Unavailable.",
            showToastOnCopy = false,
            successIcon = Icons.Default.Check,
            errorIcon = Icons.Default.Warning,
            successContainerColor = MaterialTheme.colorScheme.primaryContainer,
            successContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            errorContentColor = MaterialTheme.colorScheme.onErrorContainer,
            verticalPadding = 12.dp,
            horizontalPadding = 12.dp
        )
    }