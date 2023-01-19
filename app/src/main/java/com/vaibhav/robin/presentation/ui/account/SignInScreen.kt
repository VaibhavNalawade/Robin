package com.vaibhav.robin.presentation.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens.gird_two

@Composable
fun SignIn(
    navController: NavController,
    viewModel: SignInViewModel,
    messageBarState: MessageBarState,
    navigationType: RobinNavigationType
) {
    when (navigationType) {

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SignInContent(
                    emailState = viewModel.email,
                    passwordState = viewModel.password,
                    signInResponse = viewModel.signInResponse,
                    messageBarState = messageBarState,
                    signIn = {
                        viewModel.signWithEmailPassword()
                    },
                    navigateToSignup = {
                        navController.navigate(RobinDestinations.SIGN_UP)
                    },
                    onSignInSuccess = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}

@Composable
private fun SignInContent(
    emailState: MutableState<TextFieldState>,
    passwordState: MutableState<TextFieldState>,
    messageBarState: MessageBarState,
    signInResponse: Response<Boolean>,
    signIn: () -> Unit,
    navigateToSignup: () -> Unit,
    onSignInSuccess: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        signIn()
                    }
                )
            )
            TextButton(modifier = Modifier.align(Alignment.End),
                onClick = { /*TODO*/ }) {
                Text(

                    text = stringResource(R.string.forgot_password),
                    style = typography.bodySmall,
                )
            }


            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = signIn,
                content = {
                    SignInButton(
                        response = signInResponse,
                        onSignInSuccess = onSignInSuccess,
                        messageBarState = messageBarState
                    )
                }
            )
            SpacerVerticalOne()
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.sign_in_with_google))
            }
            SpacerVerticalFour()
            TextButton(
                onClick = navigateToSignup,
                content = {
                    Text(text = stringResource(R.string.don_t_have_account_sign_up))
                }
            )
        }
    }
}

@Composable
 fun SignInButton(
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
                LaunchedEffect(key1 = true, block = {
                    onSignInSuccess()
                })
            } else DefaultButtonAppearance()
        }

        is Error -> {
            DefaultButtonAppearance()
            LaunchedEffect(key1 = true, block = {
                messageBarState.addError(response.message.message ?: "")
            })
        }
    }
}

@Composable
private fun  DefaultButtonAppearance() {
    Icon(
        painter = painterResource(id = R.drawable.signin),
        contentDescription = "",
        modifier = Modifier.size(ButtonDefaults.IconSize)
    )

    Spacer(Modifier.size(ButtonDefaults.IconSpacing))

    Text(text = stringResource(id = R.string.sign_in))
}

@Preview
@Composable
fun SignInPreview() {
    RobinAppPreview {
        val dummy =remember { mutableStateOf(TextFieldState()) }
        SignInContent(
            emailState = dummy,
            passwordState = dummy,
            messageBarState = rememberMessageBarState(),
            signInResponse = Success(true),
            signIn = {},
            navigateToSignup = {},
            onSignInSuccess = {}
        )
    }
}
