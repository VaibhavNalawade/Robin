package com.vaibhav.robin.ui.account

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
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.vaibhav.robin.R
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.RobinDestinations
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.ui.common.*
import com.vaibhav.robin.ui.theme.Values
import com.vaibhav.robin.userExist
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController, viewModel: LoginViewModel) {

    val authState = viewModel.authState.collectAsState().value

    /*BackHandler {
            if(!userExist() &&navController.previousBackStackEntry?.destination?.route == RobinDestinations.CART)
            navController.popBackStack(RobinDestinations.HOME,false)
            else navController.popBackStack()
    }*/
    LaunchedEffect(key1 = true ,block ={
       // viewModel.checkEmail(this)

    } )

    Row(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.15f)
                .background(Color.DarkGray)
        ) {}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Values.Dimens.gird_two)
        ) {

            SpacerVerticalFour()
            Text(
                text = "Login",
                style = typography.headlineMedium
            )
            SpacerVerticalTwo()
            Text(
                text = "By continuing, you agree to our User Agreement and Privacy Policy.",
                style = typography.bodyMedium
            )
            SpacerVerticalFour()
            /**
             * todo Validation need to be implemented when error message functionality added to M3
             * todo currently waiting for TBD
             */
            RobinTextField(
                state = viewModel.email,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Email,
                        contentDescription = "Localized description"
                    )
                },
            )
            SpacerVerticalTwo()
            PasswordTextField(
                state = viewModel.password,
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    viewModel.viewModelScope.launch { viewModel.signWithEmailPassword() }
                })
            )

            SpacerVerticalFour()
            Button(modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                onClick = { viewModel.viewModelScope.launch { viewModel.signWithEmailPassword() } }) {
                LoginButtonState(authState, navController)
            }
            SpacerVerticalTwo()
            Text(text = "Forgot your username or password ?", style = typography.bodySmall)
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
fun LoginButtonState(authState: AuthState, navController: NavController) {
    when (authState) {
        is AuthState.Loading -> {
            Icon(
                Icons.Filled.Login,
                contentDescription = stringResource(R.string.complete_the_payment),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
        }
        is AuthState.Successful -> {
            if (userExist())
                navController.popBackStack()
        }
        else -> {
            Icon(
                Icons.Filled.Login,
                contentDescription = stringResource(R.string.complete_the_payment),
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "Login")
        }
    }
}

@Preview
@Composable
fun LoginPreviewLight() {
    RobinAppPreviewScaffold {
        Login(navController = rememberNavController(), viewModel = viewModel())
    }
}