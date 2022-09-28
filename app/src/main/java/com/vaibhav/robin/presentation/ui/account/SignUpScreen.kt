package com.vaibhav.robin.presentation.ui.account

import android.util.Log
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values

@Composable
fun SignUp(navController: NavHostController, viewModel: SignUpViewModel) {
    val response = viewModel.signUpResponse.value

    when (response) {
        is Success -> {
            if (response.data){
                LaunchedEffect(key1 = true, block ={
                    navController.navigate(RobinDestinations.PERSONAL_DETAILS)
                } )
            }else  InitUi(viewModel, navController)
        }
        is Error -> {
            if (response.message !is ValidationFailedException)
            com.vaibhav.robin.presentation.common.Error(response.message){viewModel.retry();Log.e("click","dfuycyfuylgyuy")}
            else InitUi(viewModel, navController)
        }
        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Loading(modifier=Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun InitUi(viewModel: SignUpViewModel, navController: NavHostController) {
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
            onClick = { viewModel.signUp()}) {
            Text(text = "Sign up")
        }
    }
}


@Preview
@Composable
fun SignupPreview() {
    RobinAppPreviewScaffold {
        SignUp(NavHostController(LocalContext.current), viewModel = viewModel())
    }
}