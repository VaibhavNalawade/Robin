package com.vaibhav.robin.presentation.ui.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun AddressAndPhoneDetails(navController: NavHostController, viewModel: AddressPhoneViewModel) {
    InitUi(viewModel)
    val response = viewModel.response
    when (response) {
        is Response.Success -> {
            if (response.data)
                LaunchedEffect(key1 = true, block = {
                    navController.popBackStack()
                })
        }

        is Response.Error -> {
            if (response.message !is ValidationFailedException)
                ShowError(response.message) {
                    viewModel.retry()
                }
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
fun InitUi(viewModel: AddressPhoneViewModel) {
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
            state = viewModel.name,
            label = { Text("Name") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.badge),
                    contentDescription = ""
                )
            },
        )

        RobinTextField(
            state = viewModel.apartmentSuitesBuilding,
            label = { Text("Apartment Suites Building") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.apartment),
                    contentDescription = ""
                )
            },
        )

        RobinTextField(
            state = viewModel.streetAddressAndCity,
            label = { Text("Street address and city") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.location_city),
                    contentDescription = ""
                )
            },
        )
        RobinTextField(
            state = viewModel.postcode,
            label = { Text(" Postcode ") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.local_post_office),
                    contentDescription = ""
                )
            },
        )


        RobinTextField(
            state = viewModel.phone,
            label = { Text("phone") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call),
                    contentDescription = ""
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
        )
        SpacerVerticalOne()
        Row( verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Address Type")
            SpacerHorizontalOne()
            val painter = if (viewModel.isHome.value) painterResource(id = R.drawable.home)
            else painterResource(id = R.drawable.apartment)
            Switch(
                checked = viewModel.isHome.value,
                thumbContent = {
                    Icon(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                },
                onCheckedChange = {
                    viewModel.isHome.value = it
                }
            )
            SpacerHorizontalOne()
            Text(text = if (viewModel.isHome.value)"Home" else "Workplace")
        }


        SpacerVerticalFour()
        Button(modifier = Modifier.align(Alignment.End),
            onClick = { viewModel.updateAddressAndPhone() }) {
            Text(text = "Done")
        }
    }
}

