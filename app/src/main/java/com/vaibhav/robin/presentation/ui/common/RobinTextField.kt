package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
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
    keyboardActions: KeyboardActions= KeyboardActions(),
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
                contentDescription = "Localized description"
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
    state: MutableState<TextFieldState>,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
    supportingText: String? = null
) {
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
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = leadingIcon,
        trailingIcon = {
            IconButton(onClick = { state.value = state.value.copy(text = "") }) {
                Icon(painter = painterResource(id = R.drawable.cancel), contentDescription = "")
            }
        }
    )

}