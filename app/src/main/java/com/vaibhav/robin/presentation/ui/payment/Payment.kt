package com.vaibhav.robin.presentation.ui.payment

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.google.android.gms.wallet.PaymentCardRecognitionIntentRequest
import com.google.android.gms.wallet.PaymentCardRecognitionResult
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.DottedButton
import com.vaibhav.robin.presentation.ui.common.PaymentCardTextField
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment(navController: NavController, viewModel: PaymentViewModel) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            var t by remember {
                mutableStateOf(false)
            }
            if (t)
                AddCardItemDialog(viewModel.pan,viewModel.expiryDate,viewModel.cvv,viewModel.cardholderName)
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                contentPadding = it,
                content = {
                    /*  when (val t = viewModel.respose) {
                          is Response.Error -> TODO()
                          Response.Loading -> {}
                          is Response.Success -> {
                              itemsIndexed(items = t.data) { i, map ->
                                  AddressItem(
                                      ind = i,
                                      map=map,
                                      selectedItem = viewModel.selectedAddressId?:t.data[0]["Id"] as? String,
                                      onAddressSelected = {
                                          viewModel.selectedAddressId=it
                                      }
                                  )
                              }
                          }
                      }*/
                    item {
                        DottedButton(
                            onClick = { t = !t },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.add_card),
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                                Text(text = stringResource(R.string.add_payment_card))


                            }
                        )
                    }
                }
            )
        },
    )
}

@Composable
fun AddCardItemDialog(
    pan: MutableState<TextFieldState>,
    expiryDate: MutableState<TextFieldState>,
    cvv: MutableState<TextFieldState>,
    cardholderName: MutableState<TextFieldState>
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card() {
            Column(modifier = Modifier.padding(Dimens.gird_two)) {
                Text(
                    text = "Add Card Details",
                    style = MaterialTheme.typography.headlineSmall
                )
                SpacerVerticalTwo()
                PaymentCardTextField(state = pan)
                Row() {
                    RobinTextField(
                        modifier = Modifier.fillMaxWidth(.6f),
                        state = expiryDate,
                        label = { Text(text = "Expiry Date") },
                        onValueChange = { expiryDate.value = expiryDate.value.copy(text = it.take(4)) },
                        trailingIcon = null,
                        placeholder = { Text(text = "MM/YY") }
                    )
                    SpacerHorizontalTwo()
                    RobinTextField(
                        state = cvv,
                        label = { Text(text = "CVV") },
                        onValueChange = { cvv.value = cvv.value.copy(text = it.take(3)) },
                        trailingIcon = null,
                        placeholder = { Text(text = "000") }
                    )
                }
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = cardholderName,
                    label = { Text(text = "Cardholder Name") },
                    trailingIcon = null
                )
                val ctx = LocalContext.current
                var int: PendingIntent? = null
                LaunchedEffect(key1 = true, block = {
                    val walletOptions = Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build()

                    Wallet.getPaymentsClient(ctx.getActivity()!!, walletOptions)
                        .getPaymentCardRecognitionIntent(PaymentCardRecognitionIntentRequest.getDefaultInstance())
                        .addOnSuccessListener {
                            int = it.paymentCardRecognitionPendingIntent
                        }
                        .addOnFailureListener {
                            int = null
                            Log.e("TAG", "Payment card ocr not available.", it)
                        }

                })
                val r = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = {

                        val c=PaymentCardRecognitionResult.getFromIntent(it.data!!)
                        if (c != null) {
                          pan.value= pan.value.copy(text = c.pan)
                            expiryDate.value=expiryDate.value.copy(text = "${c.creditCardExpirationDate?.month}/${c.creditCardExpirationDate?.year}")
                        }
                    }
                )
                Button(onClick = { r.launch(IntentSenderRequest.Builder(int!!).build()) }) {
                    Text(text = "Scan card")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Add card")
                }
            }
        }
    }
}

fun Context.getActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}