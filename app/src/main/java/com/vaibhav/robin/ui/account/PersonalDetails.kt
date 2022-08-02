package com.vaibhav.robin.ui.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.vaibhav.robin.RobinAppPreviewScaffold
import com.vaibhav.robin.RobinDestinations
import com.vaibhav.robin.ui.common.RobinTextField
import com.vaibhav.robin.ui.common.SpacerVerticalFour
import com.vaibhav.robin.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.ui.theme.Values

@Composable
fun PersonalDetails(navController: NavHostController, viewModel: AccountSharedViewModel) {
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
            leadingIcon = { Icon(imageVector = Icons.Filled.Badge, contentDescription = "")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        SpacerVerticalTwo()

        RobinTextField(
            state = viewModel.lastName,
            label = { Text("Last Name") },
            leadingIcon = { Icon(imageVector = Icons.Filled.Badge, contentDescription = "")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),

        )


        SpacerVerticalFour()
        Button(modifier = Modifier.align(Alignment.End),
            onClick = {viewModel.predatePersonalDetails { navController.navigate(RobinDestinations.DATE_AND_GENDER) } }) {
            Text(text = "Next")
        }
    }
}

@Preview
@Composable
fun Preview() {
    RobinAppPreviewScaffold {
        PersonalDetails(NavHostController(LocalContext.current), AccountSharedViewModel())
    }
}