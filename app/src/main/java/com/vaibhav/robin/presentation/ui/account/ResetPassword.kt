package com.vaibhav.robin.presentation.ui.account


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.model.Response.Success
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens.gird_two

@Composable
fun ResetPassword(
    navController: NavController,
    viewModel: ResetPasswordViewModel,
    messageBarState: MessageBarState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ResetPasswordContent(
            emailState = viewModel.email,
            response = viewModel.response,
            messageBarState = messageBarState,
            resetPassword = {
                viewModel.sendPasswordResetMail()
            },
            onSuccess = {

                navController.popBackStack()
            }
        )
    }
}


@Composable
private fun ResetPasswordContent(
    emailState: MutableState<TextFieldState>,
    messageBarState: MessageBarState,
    response: Response<Boolean>,
    resetPassword: () -> Unit,
    onSuccess: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(gird_two)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.width(360.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpacerVerticalFour()
            Icon(
                modifier = Modifier.size(96.dp),
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Text(
                text = stringResource(R.string.hi_welcome_back),
                style = typography.titleLarge
            )
            SpacerVerticalTwo()
            Text(
                text = stringResource(R.string.agreement_short),
                style = typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            SpacerVerticalFour()

            RobinTextField(
                modifier = Modifier.fillMaxSize(),
                state = emailState,
                label = {
                    Text(text = stringResource(R.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.alternate_email),
                        contentDescription = null
                    )
                },
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = resetPassword,
                content = {
                    RecoverPasswordButton(
                        response = response,
                        onSignInSuccess = onSuccess,
                        messageBarState = messageBarState
                    )
                }
            )
        }
    }
}

@Composable
private fun RecoverPasswordButton(
    response: Response<Boolean>,
    onSignInSuccess: () -> Unit,
    messageBarState: MessageBarState
) {
    when (response) {
        is Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = colorScheme.onPrimary,
                strokeWidth = 5.dp
            )
        }

        is Success -> {
            if (response.data) {
                val message= stringResource(R.string.passwprd_recovry_sucess)
                LaunchedEffect(key1 = true, block = {
                    messageBarState.addSuccess(message)
                    onSignInSuccess()
                })
            } else DefaultButtonAppearance()
        }

        is Error -> {
            DefaultButtonAppearance()
            LaunchedEffect(key1 = true, block = {
                messageBarState.addError(response.exception.message ?: "")
            })
        }
    }
}

@Composable
private fun DefaultButtonAppearance() {
    Text(text = stringResource(id = R.string.forgot_password))
}

