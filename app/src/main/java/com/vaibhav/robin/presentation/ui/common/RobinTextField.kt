package com.vaibhav.robin.presentation.ui.common

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.google.common.io.Files.append
import  com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCardTextField(
    state: MutableState<TextFieldState>,
    label: @Composable () -> Unit = { Text(text = "Card Number") },
    keyboardActions: KeyboardActions = KeyboardActions(),
    supportingText: String? = null
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.value.text,
        onValueChange = { state.value = state.value.copy(text = it.take(16)) },
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
                modifier = Modifier.size(24.dp),
                painter = if (state.value.text.startsWith("4"))
                    painterResource(id = R.drawable.visa_2021)
                else if(state.value.text.startsWith("2")||state.value.text.startsWith("5"))
                    painterResource(id = R.drawable.mastercard_logo)
                else if(state.value.text.startsWith("60")||state.value.text.startsWith("65"))
                    painterResource(id = R.drawable.rupay)
                else painterResource(id = R.drawable.credit_card),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    )
}

val mask = "1234  5678  1234  5678"

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

        // todo need to fix the bug
        override fun transformedToOriginal(offset: Int): Int {
            return if (text.text.isEmpty()) 0
            else text.length
        }
    }

    return TransformedText(annotatedString, creditCardOffsetTranslator)
}

