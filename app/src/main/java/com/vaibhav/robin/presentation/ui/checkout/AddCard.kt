package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.generateFakeCardPan
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.PaymentCardTextField
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@Composable
fun AddCardDialog(
    modifier: Modifier,
    fullScreen: Boolean,
    pan: MutableState<TextFieldState>,
    expiryDate: MutableState<TextFieldState>,
    cvv: MutableState<TextFieldState>,
    cardHolderName: MutableState<TextFieldState>,
    onAddCard: () -> Unit,
    onCancel: () -> Unit
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
                    .padding(Dimens.gird_two)
            ) {
                Row {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.close),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = stringResource(R.string.add_payment_method),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                SpacerVerticalTwo()
                PaymentCardTextField(
                    state = pan,
                    onValueChange = { pan.value = TextFieldState(text = generateFakeCardPan()) }
                )
                Row {
                    RobinTextField(
                        modifier = Modifier.weight(1f),
                        state = expiryDate,
                        label = { Text(text = stringResource(R.string.expiry_date)) },
                        onValueChange = {
                            expiryDate.value = TextFieldState("12/50")
                        },
                        trailingIcon = null,
                        placeholder = { Text(text = stringResource(R.string.mm_yy)) }
                    )
                    SpacerHorizontalTwo()
                    RobinTextField(
                        modifier = Modifier.weight(1f),
                        state = cvv,
                        label = { Text(text = stringResource(R.string.cvv)) },
                        onValueChange = { cvv.value = TextFieldState("111") },
                        trailingIcon = null,
                        placeholder = { Text(text = "000") }
                    )
                }
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = cardHolderName,
                    onValueChange = {
                        cardHolderName.value = TextFieldState(text = "John Muir")
                    },
                    label = { Text(text = stringResource(R.string.cardholder_name)) },
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
                        onClick = onAddCard,
                        content = {
                            Text(text = stringResource(R.string.add_card))
                        }
                    )
                }
            }
        }
    }
}

