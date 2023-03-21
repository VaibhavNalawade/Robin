package com.vaibhav.robin.presentation.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun SignUp(
    navController: NavHostController,
    viewModel: SignUpViewModel,
    messageBarState: MessageBarState
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SignUpContent(
            emailState = viewModel.signUpEmail,
            passwordState = viewModel.signUpPassword,
            confirmPassword = viewModel.signUpConfirmPassword,
            signUpResponse = viewModel.signUpResponse,
            nameState = viewModel.signUpName,
            messageBarState = messageBarState,
            signUp = {
                viewModel.signUp()
            },
            navigateToSignIn = {
                navController.popBackStack()
            },
            onSignUpSuccess = {
                navController.popBackStack(route = RobinDestinations.LOGIN_ROUTE, inclusive = true)
            }
        )
    }
}



@Composable
private fun SignUpContent(
    emailState: MutableState<TextFieldState>,
    passwordState: MutableState<TextFieldState>,
    confirmPassword: MutableState<TextFieldState>,
    nameState: MutableState<TextFieldState>,
    messageBarState: MessageBarState,
    signUpResponse: Response<Boolean>,
    signUp: () -> Unit,
    navigateToSignIn: () -> Unit,
    onSignUpSuccess: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(Values.Dimens.gird_two)
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
                text = stringResource(R.string.hi_welcome),
                style = MaterialTheme.typography.titleLarge
            )
            SpacerVerticalTwo()
            Text(
                text = stringResource(R.string.agreement_short),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
            SpacerVerticalFour()
            RobinTextField(
                modifier = Modifier.fillMaxSize(),
                state = nameState,
                label = {
                    Text(text = stringResource(R.string.full_name))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.badge),
                        contentDescription = null
                    )
                },
            )
            SpacerVerticalTwo()
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

            SpacerVerticalTwo()

            PasswordTextField(
                state = passwordState,
                label = {
                    Text(stringResource(id = R.string.password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )
            SpacerVerticalTwo()
            PasswordTextField(
                state = confirmPassword,
                label = {
                    Text(stringResource(R.string.confirm_password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        signUp()
                    }
                )
            )
            SpacerVerticalTwo()
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = signUp,
                content = {
                    SignUpButton(
                        response = signUpResponse,
                        onSignUpSuccess = onSignUpSuccess,
                        messageBarState = messageBarState
                    )
                }
            )
            SpacerVerticalFour()
            TextButton(
                onClick = navigateToSignIn,
                content = {
                    Text(text = stringResource(R.string.have_account_sign_in))
                }
            )
        }
    }
}

@Composable
fun SignUpButton(
    response: Response<Boolean>,
    onSignUpSuccess: () -> Unit,
    messageBarState: MessageBarState
) {
    when (response) {
        is Loading -> {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 5.dp
            )
        }

        is Success -> {
            if (response.data) {
                LaunchedEffect(key1 = true, block = {
                    onSignUpSuccess()
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
    Icon(
        painter = painterResource(id = R.drawable.signin),
        contentDescription = "",
        modifier = Modifier.size(ButtonDefaults.IconSize)
    )

    Spacer(Modifier.size(ButtonDefaults.IconSpacing))

    Text(text = stringResource(R.string.sign_up))
}

@Preview
@Composable
fun SignUpPreview() {
    RobinAppPreview {
        val dummy = remember { mutableStateOf(TextFieldState()) }
        SignUpContent(
            emailState = dummy,
            passwordState = dummy,
            confirmPassword = dummy,
            messageBarState = rememberMessageBarState(),
            nameState = dummy,
            signUpResponse = Success(true),
            signUp = {},
            navigateToSignIn = {},
            onSignUpSuccess = {}
        )
    }
}
