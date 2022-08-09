package com.vaibhav.robin.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values.Dimens.gird_two
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController, viewModel: SignInViewModel) {

    val response = viewModel.signInResponse

    Row(modifier = Modifier.fillMaxSize()) {

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.15f)
                .background(Color.DarkGray),
            content = {}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(gird_two)
        ) {

            SpacerVerticalFour()

            Text(
                text = stringResource(id = R.string.sign_in),
                style = typography.headlineMedium
            )

            SpacerVerticalTwo()

            Text(
                text = stringResource(R.string.agreement_short),
                style = typography.bodyMedium
            )

            SpacerVerticalFour()

            /**
             * todo Validation need to be implemented when error message functionality added to M3
             * todo currently waiting TBD
             */

            RobinTextField(
                state = viewModel.email,
                label = {
                    Text(text = stringResource(R.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Email,
                        contentDescription = null
                    )
                },
            )

            SpacerVerticalTwo()

            PasswordTextField(
                state = viewModel.password,
                label = {
                    Text(stringResource(id = R.string.password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.viewModelScope.launch {
                        viewModel.signWithEmailPassword()
                    }
                })
            )

            SpacerVerticalFour()

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = {
                    viewModel.viewModelScope.launch {
                        viewModel.signWithEmailPassword()
                    }
                },
                content = {
                    LoginButtonState(response, navController)
                }
            )

            SpacerVerticalTwo()

            Text(
                text = stringResource(R.string.forgot_password),
                style = typography.bodySmall
            )

            SpacerVerticalTwo()

            HyperlinkText(
                fullText = "New to Robin? SIGN UP",
                onClick = { navController.navigate(RobinDestinations.SIGN_UP) },
                linkText = listOf("SIGN UP")
            )

            SpacerVerticalTwo()

            SpaceBetweenContainer(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DividerHorizontal(modifier = Modifier.fillMaxWidth(.40f))
                Text(text = "OR")
                DividerHorizontal(modifier = Modifier.fillMaxWidth(.45f))
            }
            SpacerVerticalTwo()
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = stringResource(R.string.complete_the_payment),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Google")
            }
            SpacerVerticalTwo()
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.microsoft_logo),
                    contentDescription = stringResource(R.string.complete_the_payment),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Microsoft")
            }
            SpacerVerticalTwo()
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = stringResource(R.string.complete_the_payment),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Facebook")
            }
            SpacerVerticalTwo()
            OutlinedButton(modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.twitter),
                    contentDescription = stringResource(R.string.complete_the_payment),
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = "Twitter")
            }
        }
    }
}

@Composable
fun LoginButtonState(response: Response<Boolean>, navController: NavController) {
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
                    navController.popBackStack()
                })
            }
            else DefaultButtonAppearance()
        }
        is Error -> {
            DefaultButtonAppearance()
        }
    }
}

@Composable
fun DefaultButtonAppearance() {
    Icon(
        Icons.Filled.Login,
        contentDescription = stringResource(R.string.complete_the_payment),
        modifier = Modifier.size(ButtonDefaults.IconSize)
    )

    Spacer(Modifier.size(ButtonDefaults.IconSpacing))

    Text(text = "Login")
}

@Preview
@Composable
fun LoginPreviewLight() {
    RobinAppPreviewScaffold {
        Login(navController = rememberNavController(), viewModel = viewModel())
    }
}