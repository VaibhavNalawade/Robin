package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun AddAddressDialog(
    modifier: Modifier,
    fullScreen: Boolean ,
    onAddAddress: () -> Unit,
    onCancel: () -> Unit,
    streetAddress: MutableState<TextFieldState>,
    apartmentAddress: MutableState<TextFieldState>,
    addressFullName: MutableState<TextFieldState>,
    city: MutableState<TextFieldState>,
    state: MutableState<TextFieldState>,
    pinCode: MutableState<TextFieldState>,
    phoneNumber: MutableState<TextFieldState>
) {
    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = !fullScreen
        )
    ) {
        Surface(
            tonalElevation = AlertDialogDefaults.TonalElevation,
            shape = if (!fullScreen) AlertDialogDefaults.shape else RectangleShape
        ) {
            Column(
                modifier = modifier
                    .padding(Values.Dimens.gird_two)
            ) {
                Row {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "Add Address",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                SpacerVerticalTwo()
                RobinTextField(
                    state = addressFullName,
                    label = { Text(text = stringResource(id = R.string.full_name)) },
                    onValueChange = {
                        addressFullName.value = addressFullName.value.copy(text = it)
                    },
                    trailingIcon = null,
                )
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = apartmentAddress,
                    onValueChange = {
                        apartmentAddress.value = apartmentAddress.value.copy(text = it)
                    },
                    label = { Text(text = stringResource(R.string.apartment_address)) },
                    trailingIcon = null
                )
                RobinTextField(
                    state = streetAddress,
                    label = { Text(text = stringResource(id = R.string.street_address)) },
                    onValueChange = {
                        streetAddress.value = streetAddress.value.copy(text = it)
                    },
                    trailingIcon = null,
                )
                Row {
                    RobinTextField(
                        modifier = Modifier.weight(1f),
                        state = city,
                        label = { Text(text = stringResource(R.string.city)) },
                        onValueChange = {
                            city.value = city.value.copy(text = it)
                        },
                        trailingIcon = null,
                    )
                    SpacerHorizontalTwo()
                    RobinTextField(
                        modifier = Modifier.weight(1f),
                        state = state,
                        label = { Text(text = stringResource(R.string.state)) },
                        onValueChange = {
                            state.value = state.value.copy(text = it)
                                        },
                        trailingIcon = null,
                    )
                }
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = pinCode,
                    onValueChange = {
                        pinCode.value = pinCode.value.copy(text = it)
                    },
                    label = { Text(text = stringResource(R.string.postal_code)) },
                    trailingIcon = null
                )
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = phoneNumber,
                    onValueChange = {
                        phoneNumber.value = phoneNumber.value.copy(text = it)
                    },
                    label = { Text(text = stringResource(R.string.phone)) },
                    trailingIcon = null
                )
                SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onCancel,
                        content = {
                            Text(text = stringResource(id = R.string.cancel))
                        }
                    )
                    Button(
                        onClick = onAddAddress,
                        content = {
                            Text(text = stringResource(R.string.add_card))
                        }
                    )
                }
            }
        }
    }
}