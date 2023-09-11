package com.vaibhav.robin.presentation.ui.common

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutIdParentData
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.lerp
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.compose.ui.util.lerp
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlin.math.max
import kotlin.math.roundToInt

@Composable
fun PasswordTextField(
    state: MutableState<TextFieldState>,
    label: @Composable () -> Unit = { Text(text = stringResource(id = R.string.password)) },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    supportingText: String? = null
) {
    var passwordHidden by remember { mutableStateOf(true) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value.text,
        onValueChange = { state.value = state.value.copy(text = it) },
        label = label,
        singleLine = true,
        isError = state.value.error,
        supportingText = {
            if (state.value.error) Text(text = state.value.errorMessage!!.asString())
            else supportingText?.let { Text(text = it) }
        },
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.password),
                contentDescription = null
            )
        }, trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) painterResource(id = R.drawable.visibility)
                    else painterResource(id = R.drawable.visibility_off)
                Icon(
                    painter = visibilityIcon,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
fun RobinTextField(
    modifier: Modifier=Modifier,
    state: MutableState<TextFieldState>,
    onValueChange:(String)-> Unit = { state.value = state.value.copy(text = it) },
    label: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    supportingText: String? = null,
    placeholder:@Composable (()->Unit)?=null,
    trailingIcon:@Composable (()->Unit)?={
        IconButton(
            onClick = { state.value = state.value.copy(text = "") },
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = null
                )
            }
        )
    },

) {
    OutlinedTextField(
        modifier = modifier,
        value = state.value.text,
        onValueChange = onValueChange,
        label = label,
        singleLine = true,
        isError = state.value.error,
        supportingText = {
            if (state.value.error) Text(text = state.value.errorMessage!!.asString())
            else supportingText?.let { Text(text = it) }
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon =trailingIcon,
        placeholder = placeholder
    )
}


@Composable
fun PaymentCardTextField(
    state: MutableState<TextFieldState>,
    onValueChange: (String) -> Unit ={ state.value = state.value.copy(text = it.take(16)) },
    label: @Composable () -> Unit = { Text(text = "Card Number") },
    keyboardActions: KeyboardActions = KeyboardActions(),
    supportingText: String? = null
) {
    ModifiedOutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value.text,
        onValueChange = onValueChange,
        label = label,
        singleLine = true,
        isError = state.value.error,
        supportingText = {
            if (state.value.error) Text(text = state.value.errorMessage!!.asString())
            else supportingText?.let { Text(text = it) }
        },
        visualTransformation = { creditCardFilter(it) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = keyboardActions,
        leadingIcon = {
            Icon(
                modifier = Modifier.size(48.dp,32.dp),
                painter = if (state.value.text.startsWith("4"))
                    painterResource(id = R.drawable.visa_2021)
                else if(state.value.text.startsWith("2")||state.value.text.startsWith("5"))
                    painterResource(id = R.drawable.mastercard_logo)
                else if(state.value.text.startsWith("60")||state.value.text.startsWith("65"))
                    painterResource(id = R.drawable.rupay)
                else painterResource(id = R.drawable.paymentcard),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    )
}

const val mask = "1234  5678  1234  5678"

fun creditCardFilter(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text

    val annotatedString = AnnotatedString.Builder().run {
        for (i in trimmed.indices) {
            append(trimmed[i])
            if (i % 4 == 3 && i != 15) {
                append("  ")
            }
        }
        pushStyle(SpanStyle(color = Color.LightGray))
        append(mask.takeLast(mask.length - length))
        toAnnotatedString()
    }

    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 3) return offset
            if (offset <= 7) return offset + 2
            if (offset <= 11) return offset + 4
            if (offset <= 16) return offset + 6
            return 22
        }

        override fun transformedToOriginal(offset: Int): Int {
            return if (text.text.isEmpty()) 0
            else text.length
        }
    }

    return TransformedText(annotatedString, creditCardOffsetTranslator)
}

