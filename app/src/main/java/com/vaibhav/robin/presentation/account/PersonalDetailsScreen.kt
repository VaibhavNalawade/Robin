package com.vaibhav.robin.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values

@Composable
fun PersonalDetails(navController: NavHostController, viewModel: PersonalDetailsViewModel) {
    InitUi(viewModel)
    val response=viewModel.response
    when (response) {
        is Response.Success -> {
            if (response.data)
                LaunchedEffect(key1 = true, block = {
                    navController.navigate(RobinDestinations.DATE_AND_GENDER)
                })
        }
        is Response.Error -> {

           com.vaibhav.robin.presentation.common.Error(response.message){}
        }
        is Response.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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
fun InitUi(viewModel: PersonalDetailsViewModel) {
    Column(modifier = Modifier.padding(horizontal = Values.Dimens.gird_two)) {

        SpacerVerticalFour()
        Text(
            text = "Personal Details",
            style = MaterialTheme.typography.headlineMedium
        )
        SpacerVerticalTwo()
        Text(
            text = "Enter your personal details such as your name phone number address. We don't share such details to third party and maintain your privacy",
            style = MaterialTheme.typography.bodyMedium
        )
        SpacerVerticalFour()
        RobinTextField(
            state = viewModel.firstName,
            label = { Text("First Name") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Badge, contentDescription = "") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        SpacerVerticalTwo()

        RobinTextField(
            state = viewModel.lastName,
            label = { Text("Last Name") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Badge, contentDescription = "") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),

            )


        SpacerVerticalFour()
        Button(modifier = Modifier.align(Alignment.End),
            onClick = { viewModel.updateDetails() }) {
            Text(text = "Next")
        }
    }
}

@Preview
@Composable
fun Preview() {
    RobinAppPreviewScaffold {
        PersonalDetails(NavHostController(LocalContext.current), viewModel = viewModel())
    }
}