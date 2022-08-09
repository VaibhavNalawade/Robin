package com.vaibhav.robin.presentation.account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreviewScaffold
import com.vaibhav.robin.presentation.common.RobinTextField
import com.vaibhav.robin.presentation.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.theme.Values

@Composable
fun AddressAndPhoneDetails(navController: NavHostController, viewModel: AddressPhoneViewModel) {
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
            state =viewModel.apartmentSuitesBuilding,
            label = {Text("Apartment Suites Building")},
            leadingIcon = {Icon(imageVector = Icons.Filled.Apartment, contentDescription ="" )},
        )
        SpacerVerticalTwo()
        RobinTextField(
            state =viewModel.streetAddressAndCity,
            label = {Text("Street address and city")},
            leadingIcon = {Icon(imageVector = Icons.Filled.LocationCity, contentDescription ="" )},

        )
        SpacerVerticalTwo()
        RobinTextField(
            state =viewModel.postcode,
            label = {Text(" Postcode ")},
            leadingIcon = { Icon(imageVector = Icons.Filled.LocalPostOffice , contentDescription ="" )},
        )
        SpacerVerticalTwo()

        RobinTextField(
            state = viewModel.phone,
            label = { Text("phone") },
            leadingIcon = { Icon(imageVector = Icons.Filled.PhoneInTalk, contentDescription = "") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )


        SpacerVerticalFour()
        Button(modifier = Modifier.align(Alignment.End),
            onClick = { viewModel.predateAddressAndPhone {navController.popBackStack(route = RobinDestinations.HOME,false) }}) {
            Text(text = "Next")
        }
    }
}

@Preview
@Composable
fun AddressAndPhoneDetailsPreview() {
    RobinAppPreviewScaffold {
        AddressAndPhoneDetails(NavHostController(LocalContext.current), AddressPhoneViewModel())
    }
}