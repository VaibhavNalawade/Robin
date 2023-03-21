package com.vaibhav.robin.presentation.ui.delivery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

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
            if (response.exception !is ValidationFailedException)
                ShowError(response.exception) {
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

    val text =
        if (viewModel.isHome.value) stringResource(id = R.string.home)
        else stringResource(id = R.string.commercial)

    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.gird_two)
    ) {
        SpacerVerticalFour()
        Text(
            text = stringResource(R.string.add_a_new_address),
            style = MaterialTheme.typography.headlineMedium
        )
        SpacerVerticalFour()
        RobinTextField(
            modifier=Modifier.fillMaxWidth(),
            state = viewModel.name,
            label = { Text(stringResource(R.string.name)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.badge),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        RobinTextField(
            modifier=Modifier.fillMaxWidth(),
            state = viewModel.apartmentSuitesBuilding,
            label = {
                Text(stringResource(R.string.apartment_suites_building))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.apartment),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        RobinTextField(
            modifier=Modifier.fillMaxWidth(),
            state = viewModel.streetAddressAndCity,
            label = { Text(stringResource(R.string.street_address_and_city)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.location_city),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        RobinTextField(
            modifier=Modifier.fillMaxWidth(),
            state = viewModel.postcode,
            label = { Text(stringResource(R.string.postcode)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.local_post_office),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        RobinTextField(
            modifier=Modifier.fillMaxWidth(),
            state = viewModel.phone,
            label = { Text(stringResource(R.string.phone)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.call),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    viewModel.updateAddressAndPhone()
                }
            )
        )
        SpacerVerticalOne()
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(R.string.address_type))
            SpacerHorizontalOne()
            val painter = if (viewModel.isHome.value) painterResource(id = R.drawable.home)
            else painterResource(id = R.drawable.apartment)
            Switch(
                checked = viewModel.isHome.value,
                thumbContent = {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                },
                onCheckedChange = {
                    viewModel.isHome.value = it
                }
            )
            SpacerHorizontalOne()
            Text(
                text = text
            )
        }
        SpacerVerticalFour()
        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                viewModel.updateAddressAndPhone()
            },
            content = {
                Text(text = stringResource(R.string.add_a_new_address))
            }
        )
    }
}

