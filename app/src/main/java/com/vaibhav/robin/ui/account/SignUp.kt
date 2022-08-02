package com.vaibhav.robin.ui.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuthException
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.RobinDestinations
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.ui.AuthExceptionDecode
import com.vaibhav.robin.ui.common.*
import com.vaibhav.robin.ui.product.LoadingStateLight
import com.vaibhav.robin.ui.theme.Values

@Composable
fun SignUp(navController: NavHostController, viewModel: AccountSharedViewModel) {
    val authState by viewModel.authState.collectAsState()
    InitUi(viewModel, navController)
    when (authState) {
        is AuthState.Successful -> {
            navController.navigate(RobinDestinations.PERSONAL_DETAILS)
        }
        is AuthState.Error -> {
            val openDialog = remember { mutableStateOf(true) }
            val decode = remember {
                mutableStateOf(AuthExceptionDecode((authState as AuthState.Error).exception))
            }
            if (openDialog.value) {
                AlertDialog(
                    onDismissRequest = {

                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.Error,
                            modifier = Modifier.size(48.dp),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    title = {
                        Text(text = decode.value.title.asString())
                    },
                    text = {
                        Text(
                            decode.value.message.asString() +
                                    "\n error code ${decode.value.errorCode}"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.authState.value = AuthState.Init
                                openDialog.value = false
                            },
                        ) {
                            Text("Retry")
                        }
                    },

                    )
            }
        }
        AuthState.Init -> {}
        is AuthState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
                Loading(
                    Modifier
                        .height(68.dp)
                        .background(MaterialTheme.colorScheme.surface)
                )
            }
        }
    }
}

@Composable
private fun InitUi(viewModel: AccountSharedViewModel, navController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = Values.Dimens.gird_two)) {
        SpacerVerticalFour()
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.headlineMedium
        )
        SpacerVerticalTwo()
        Text(
            text = "By continuing, you agree to our User Agreement and Privacy Policy.",
            style = MaterialTheme.typography.bodyMedium
        )
        SpacerVerticalFour()

        /**
         * todo Validation need to be implemented when error message functionality added to M3
         * todo currently waiting for TBD
         */

        /**
         * todo Validation need to be implemented when error message functionality added to M3
         * todo currently waiting for TBD
         */

        RobinTextField(
            state = viewModel.signUpEmail,
            label = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Email, contentDescription = "") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
        )
        SpacerVerticalTwo()
        PasswordTextField(
            state = viewModel.signUpPassword,
            keyboardActions = KeyboardActions(onDone = {}),
        )
        SpacerVerticalTwo()
        PasswordTextField(
            state = viewModel.signUpConfirmPassword,
            label = { Text("Confirm Password") },
            keyboardActions = KeyboardActions(onDone = {}),
        )
        SpacerVerticalFour()
        Button(modifier = Modifier.fillMaxWidth(),
            onClick = { viewModel.predateSignUp { } }) {
            Text(text = "Sign up")
        }
    }
}


@Preview
@Composable
fun SignupPreview() {
    RobinAppPreviewScaffold {
        SignUp(NavHostController(LocalContext.current), AccountSharedViewModel())
    }
}