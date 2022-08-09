package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Password
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import  com.vaibhav.robin.R
import com.vaibhav.robin.entities.ui.state.TextFieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    state: MutableState<TextFieldState>,
    label: @Composable () -> Unit = { Text(text = stringResource(id = R.string.password)) },
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions,
) {
    var passwordHidden by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxWidth()) {
    OutlinedTextField(
        value = state.value.text,
        onValueChange = {state.value=state.value.copy(text = it)},
        label = label,
        singleLine = true,
        isError = state.value.error,
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        leadingIcon = {
            Icon(
                Icons.Rounded.Password,
                contentDescription = "Localized description"
            )
        }, trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                // Please provide localized description for accessibility services
                val description = if (passwordHidden) "Show password" else "Hide password"
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        }
    )
        SpacerVerticalOne()
        state.value.errorMessage?.asString()?.let { Text(text =it , style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinTextField(
    state: MutableState<TextFieldState>,
    label: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Password,
        imeAction = ImeAction.Done
    ),
    keyboardActions: KeyboardActions = KeyboardActions(),
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = state.value.text,
            onValueChange = {state.value=state.value.copy(text = it)},
            label = label,
            singleLine = true,
            isError = state.value.error,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            leadingIcon = leadingIcon,
        )
        SpacerVerticalOne()
        state.value.errorMessage?.asString()?.let { Text(text = it, style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)) }
    }
}