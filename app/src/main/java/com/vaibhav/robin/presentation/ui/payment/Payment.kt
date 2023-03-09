package com.vaibhav.robin.presentation.ui.payment

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.ui.common.DottedButton
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.PaymentCardTextField
import com.vaibhav.robin.presentation.ui.common.RobinTextField
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment(navController: NavController, viewModel: PaymentViewModel) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.loadPayments()
        }
    )
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.payment),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            var dialogShow by remember {
                mutableStateOf(false)
            }
            if (dialogShow)
                AddCardItemDialog(
                    viewModel.pan,
                    viewModel.expiryDate,
                    viewModel.cvv,
                    viewModel.cardholderName,
                    onCancel = { dialogShow = false },
                    onSaveCard = {
                        viewModel.addPayment {
                            dialogShow = false
                        }
                        viewModel.loadPayments()
                    }
                )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(300.dp),
                contentPadding = PaddingValues(
                    start = Dimens.gird_two,
                    end = Dimens.gird_two,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(Dimens.gird_two),
                horizontalArrangement = Arrangement.spacedBy(Dimens.gird_two),
                content = {
                    when (val t = viewModel.paymentsResponse) {
                        is Response.Error -> item{
                            ShowError(
                                exception = t.message,
                                retry = {
                                    viewModel.loadPayments()
                                }
                            )
                        }
                        Response.Loading -> item {
                            Loading()
                        }
                        is Response.Success -> {
                            itemsIndexed(items = t.data) { i, paymentData ->
                                PaymentGridItem(
                                    paymentData = paymentData,
                                    onPaymentMethodRemove = {
                                        viewModel.removePaymentMethod(id=it)
                                        viewModel.loadPayments()
                                    }
                                )
                            }
                        }
                    }
                    item {
                        DottedButton(
                            onClick = { dialogShow = !dialogShow },
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
fun PaymentGridItem(
    paymentData: PaymentData,
    onPaymentMethodRemove:(String)->Unit
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .clip(CardDefaults.shape)
            .background(
                brush = Brush.verticalGradient(
                    .25f to Color(0xff524175),
                    .75f to Color(0xff295270),
                )
            )
            .padding(Dimens.gird_two)
    ) {
        SpacerVerticalTwo()
        SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
            Icon(
                modifier = Modifier.size(48.dp),
                painter = if (paymentData.prn.startsWith("4"))
                    painterResource(id = R.drawable.visa_2021)
                else if(paymentData.prn.startsWith("2")||paymentData.prn.startsWith("5"))
                    painterResource(id = R.drawable.mastercard_logo)
                else if(paymentData.prn.startsWith("60")||paymentData.prn.startsWith("65"))
                    painterResource(id = R.drawable.rupay)
                else painterResource(id = R.drawable.credit_card),
                contentDescription = null,
                tint = Color.Unspecified
            )
            IconButton(onClick = { onPaymentMethodRemove(paymentData.id) }) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription =null
                )
            }
        }
        val prn by remember {
            mutableStateOf(
                "••••  ••••  ••••  "+
                        paymentData.prn.substring(12, 16)
            )
        }
        SpacerVerticalTwo()
        Text(
            text = prn,
            style = MaterialTheme.typography.headlineSmall.copy(
                letterSpacing = TextUnit(
                    1.5f,
                    type = TextUnitType.Sp
                )
            )
        )
        SpacerVerticalTwo()
        Text(text = paymentData.cardHolderName)
        SpacerVerticalTwo()

    }
}

@Composable
fun AddCardItemDialog(
    pan: MutableState<TextFieldState>,
    expiryDate: MutableState<TextFieldState>,
    cvv: MutableState<TextFieldState>,
    cardholderName: MutableState<TextFieldState>,
    onSaveCard: () -> Unit,
    onCancel: () -> Unit
) {
    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = onCancel
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(Dimens.gird_two)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.add_card_details),
                    style = MaterialTheme.typography.headlineSmall
                )
                SpacerVerticalTwo()
                PaymentCardTextField(
                    state = pan,
                    onValueChange = {
                        pan.value=pan.value.copy(
                            "${arrayOf(20,40,50,60,65).random()}" +
                                    "${ (10000000000000..99999999999999).random()}")
                    }
                )
                Row() {
                    RobinTextField(
                        modifier = Modifier.fillMaxWidth(.6f),
                        state = expiryDate,
                        label = { Text(text = "Expiry Date") },
                        onValueChange = {
                            expiryDate.value=expiryDate.value.copy("12/50")
                        },
                        trailingIcon = null,
                        placeholder = { Text(text = "MM/YY") }
                    )
                    SpacerHorizontalTwo()
                    RobinTextField(
                        state = cvv,
                        label = { Text(text = "CVV") },
                        onValueChange = { cvv.value = cvv.value.copy("111") },
                        trailingIcon = null,
                        placeholder = { Text(text = "000") }
                    )
                }
                RobinTextField(
                    modifier = Modifier.fillMaxWidth(),
                    state = cardholderName,
                    onValueChange = {cardholderName.value= cardholderName.value.copy(text = "John Muir") },
                    label = { Text(text = "Cardholder Name") },
                    trailingIcon = null
                )
                val ctx = LocalContext.current
                var int: PendingIntent? = null


                SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        enabled = int!=null
                        ,
                        onClick = { }) {
                        Text(text = "Scan card")
                    }
                    Button(onClick = onSaveCard) {
                        Text(text = "Add card")
                    }
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
private const val TAG="PAYMENT_COMPOSE"