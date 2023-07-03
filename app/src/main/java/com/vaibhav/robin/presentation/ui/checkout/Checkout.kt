package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.model.Response.Success
import com.vaibhav.robin.presentation.OrderSummary
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.generateCardName
import com.vaibhav.robin.presentation.getCardResourceByPan
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.ProfileInitial
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Checkout(
    navController: NavController,
    viewModel: CheckoutViewModel,
    cartUiState: CartUiState,
    messageBarState: MessageBarState
) {
    val addPaymentDialogState = rememberSaveable { mutableStateOf(false) }
    val addAddressDialogState = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.loadPayments()
            viewModel.loadAddresses()
        }
    )
    val response = viewModel.orderResponse
    LaunchedEffect(key1 = response, block = {
        if (response is Success)
            if (response.data)
                navController.navigate(RobinDestinations.CHECKOUT_DONE) {
                    popUpTo(RobinDestinations.HOME)
                }
    })
    LaunchedEffect(key1 = viewModel.addAddressResponse, block = {
        when (val t = viewModel.addAddressResponse) {
            is Error -> messageBarState.addError(t.exception.message ?: "Something Went Wrong")
            Loading -> {}
            is Success -> {
                addAddressDialogState.value = false
                viewModel.loadAddresses()
            }
        }
    })
    LaunchedEffect(key1 = viewModel.addPaymentResponse, block = {
        when (val t = viewModel.addPaymentResponse) {
            is Error -> messageBarState.addError(t.exception.message ?: "Something Went Wrong")
            Loading -> {}
            is Success -> {
                addPaymentDialogState.value = false
                viewModel.loadPayments()
            }
        }
    })
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.checkout),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = stringResource(id = R.string.navigation_back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        BoxWithConstraints(modifier = Modifier.padding(padding)) {
            val onPay: () -> Unit = {
                if (
                    viewModel.selectedAddressId.value != null &&
                    viewModel.selectedPaymentId.value != null
                ) {
                    viewModel.placeOrder((cartUiState as CartUiState.Success).cartItems)
                } else
                    messageBarState.addError("Please Select Address and payment method")
            }

            val fullScreen = rememberSaveable { mutableStateOf(true) }

            if (addPaymentDialogState.value)
                AddCardDialog(
                    modifier = if (fullScreen.value) Modifier.fillMaxSize() else Modifier,
                    fullScreen = fullScreen.value,
                    pan = viewModel.pan,
                    expiryDate = viewModel.expiryDate,
                    cvv = viewModel.cvv,
                    cardHolderName = viewModel.cardholderName,
                    onAddCard = {
                        viewModel.addPaymentMethod()
                    },
                    onCancel = {
                        addPaymentDialogState.value = false
                    }
                )
            if (addAddressDialogState.value)
                AddAddressDialog(
                    modifier = if (fullScreen.value) Modifier.fillMaxSize() else Modifier,
                    fullScreen = fullScreen.value,
                    addressFullName = viewModel.addressFullName,
                    streetAddress = viewModel.streetAddress,
                    city = viewModel.city,
                    state = viewModel.state,
                    pinCode = viewModel.pinCode,
                    phoneNumber = viewModel.phoneNumber,
                    apartmentAddress = viewModel.apartmentAddress,
                    onAddAddress = { viewModel.addAddress() },
                    onCancel = { addAddressDialogState.value = false }
                )

            if (maxWidth < 600.dp)
                CompactLayout(
                    addressResponse = viewModel.addressResponse,
                    paymentsResponse = viewModel.paymentsResponse,
                    cartUiState = cartUiState as? CartUiState.Success,
                    currentSelectedPaymentID = viewModel.selectedPaymentId,
                    currentSelectedAddressId = viewModel.selectedAddressId,
                    refreshAddress = { viewModel.loadAddresses() },
                    refreshPayments = { viewModel.loadPayments() },
                    onPay = onPay,
                    orderResponse = viewModel.orderResponse,
                    addPaymentDialogState = addPaymentDialogState,
                    addAddressDialogState = addAddressDialogState
                ).also { fullScreen.value = true }
            else
                ExpandedLayout(
                    addressResponse = viewModel.addressResponse,
                    paymentsResponse = viewModel.paymentsResponse,
                    cartUiState = cartUiState as? CartUiState.Success,
                    currentSelectedPaymentID = viewModel.selectedPaymentId,
                    currentSelectedAddressId = viewModel.selectedAddressId,
                    refreshAddress = { viewModel.loadAddresses() },
                    refreshPayments = { viewModel.loadPayments() },
                    onPay = onPay,
                    orderResponse = viewModel.orderResponse,
                    addPaymentDialogState = addPaymentDialogState,
                    addAddressDialogState = addAddressDialogState
                ).also { fullScreen.value = false }
        }
    }
}

@Composable
private fun CompactLayout(
    addressResponse: Response<List<Address>>,
    paymentsResponse: Response<List<PaymentData>>,
    cartUiState: CartUiState.Success?,
    currentSelectedPaymentID: MutableState<String?>,
    currentSelectedAddressId: MutableState<String?>,
    refreshAddress: () -> Unit,
    refreshPayments: () -> Unit,
    onPay: () -> Unit,
    orderResponse: Response<Boolean>,
    addPaymentDialogState: MutableState<Boolean>,
    addAddressDialogState: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Dimens.gird_two)
            .verticalScroll(rememberScrollState())
    ) {
        SpacerVerticalTwo()
        Shipping(
            addressResponse = addressResponse,
            currentAddress = currentSelectedAddressId,
            refreshAddress = refreshAddress,
            onAddAddressClick = { addAddressDialogState.value = true }
        )
        SpacerVerticalTwo()
        Payment(
            paymentResponse = paymentsResponse,
            currentPayment = currentSelectedPaymentID,
            refreshPayments = refreshPayments,
            onAddPaymentClick = { addPaymentDialogState.value = true }
        )
        SpacerVerticalTwo()
        Summary(
            cartUiState as? CartUiState.Success,
            onClick = onPay,
            orderResponse = orderResponse
        )
        SpacerVerticalTwo()
    }
}


@Composable
private fun ExpandedLayout(
    addressResponse: Response<List<Address>>,
    paymentsResponse: Response<List<PaymentData>>,
    cartUiState: CartUiState.Success?,
    currentSelectedPaymentID: MutableState<String?>,
    currentSelectedAddressId: MutableState<String?>,
    refreshAddress: () -> Unit,
    refreshPayments: () -> Unit,
    onPay: () -> Unit,
    orderResponse: Response<Boolean>,
    addPaymentDialogState: MutableState<Boolean>,
    addAddressDialogState: MutableState<Boolean>
) {
    Row(
        modifier = Modifier
            .widthIn(300.dp, 1500.dp)
    ) {
        Column(
            Modifier
                .padding(Dimens.gird_two)
                .fillMaxWidth(.60f)
                .verticalScroll(rememberScrollState())
        ) {
            Shipping(
                addressResponse = addressResponse,
                currentAddress = currentSelectedAddressId,
                refreshAddress = refreshAddress,
                onAddAddressClick = { addAddressDialogState.value = true }
            )
            Payment(
                paymentResponse = paymentsResponse,
                currentPayment = currentSelectedPaymentID,
                refreshPayments = refreshPayments,
                onAddPaymentClick = { addPaymentDialogState.value = true }
            )
        }
        Column(
            Modifier
                .padding(Dimens.gird_two)
                .widthIn(300.dp, 500.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SpacerVerticalFour()
            Summary(
                cartUiState = cartUiState,
                onClick = onPay,
                orderResponse = orderResponse
            )
        }
    }
}

@Composable
private fun Shipping(
    addressResponse: Response<List<Address>>,
    currentAddress: MutableState<String?>,
    refreshAddress: () -> Unit,
    onAddAddressClick: () -> Unit
) {
    Text(
        text = stringResource(R.string.shipping_address),
        maxLines = 1,
        style = typography.headlineSmall
    )
    SpacerVerticalOne()
    Text(
        text = stringResource(R.string.select_a_delivery_address),
        maxLines = 1,
        style = typography.bodyLarge
    )
    SpacerVerticalOne()
    when (addressResponse) {
        is Error -> ShowError(
            exception = addressResponse.exception,
            retry = refreshAddress
        )

        Loading -> Loading()
        is Success -> addressResponse.data.forEachIndexed { index, item ->
            AddressListItem(
                address = item,
                selected = currentAddress.value == item.id,
                index = index,
                onAddressSelected = { currentAddress.value = it }
            )
        }
    }

    SpacerVerticalOne()
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onAddAddressClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.add_location),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(id = R.string.add_a_new_address))
        }
    )
}

@Composable
private fun AddressListItem(
    address: Address,
    selected: Boolean,
    onAddressSelected: (String) -> Unit,
    index: Int
) {
    ListItem(
        modifier = Modifier.clickable { onAddressSelected(address.id) },
        headlineContent = { Text(text = address.fullName) },
        supportingContent = {
            Text(
                /*todo SOLID principle not gone like this. please separate function*/
                text = "${address.apartmentAddress},${address.streetAddress}, ${address.pinCode}"
            )
        },
        trailingContent = {
            Checkbox(
                checked = selected,
                onCheckedChange = {}
            )
        },
        leadingContent = {
            ProfileInitial(
                modifier = Modifier.size(48.dp),
                profileName = index.toString()
            )
        }
    )
}

@Composable
private fun Payment(
    paymentResponse: Response<List<PaymentData>>,
    currentPayment: MutableState<String?>,
    refreshPayments: () -> Unit,
    onAddPaymentClick: () -> Unit
) {
    Text(
        text = stringResource(R.string.payment_method),
        maxLines = 1,
        style = typography.headlineSmall
    )
    SpacerVerticalOne()
    Text(
        text = stringResource(R.string.select_a_payment_method),
        maxLines = 1,
        style = typography.bodyLarge
    )
    SpacerVerticalOne()
    when (paymentResponse) {
        is Error -> ShowError(
            exception = paymentResponse.exception,
            retry = refreshPayments
        )

        Loading -> Loading()

        is Success -> paymentResponse.data.forEach { item ->
            PaymentListItem(
                payment = item,
                selected = currentPayment.value == item.id,
                onPaymentSelected = { currentPayment.value = it }
            )
        }
    }
    SpacerVerticalOne()
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = onAddPaymentClick,
        content = {
            Icon(
                painter = painterResource(id = R.drawable.add_card),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.add_a_new_payment_method))
        }
    )
}

@Composable
private fun PaymentListItem(
    payment: PaymentData,
    selected: Boolean,
    onPaymentSelected: (String) -> Unit
) {
    val resourceId = remember { getCardResourceByPan(pan = payment.pan) }
    ListItem(
        modifier = Modifier.clickable { onPaymentSelected(payment.id) },
        headlineContent = { Text(text = generateCardName(payment.pan).asString()) },
        supportingContent = {
            Text(
                text = stringResource(R.string.dot_card_digits, payment.pan.takeLast(4))
            )
        },
        trailingContent = {
            Checkbox(
                checked = selected,
                onCheckedChange = {}
            )
        },
        leadingContent = {
            Icon(
                modifier = Modifier.size(40.dp),
                painter = painterResource(id = resourceId),
                contentDescription = ""
            )
        }
    )
}

@Composable
private fun Summary(
    cartUiState: CartUiState.Success?,
    onClick: () -> Unit,
    orderResponse: Response<Boolean>
) {
    val summary = remember { cartUiState?.let { calculateSummary(it.cartItems) } ?: OrderSummary() }
    val format = DecimalFormat("#,##0.00")
    Surface(
        shape = CardDefaults.shape,
        color = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_2)
    ) {
        Column(modifier = Modifier.padding(Dimens.gird_two)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                content = {
                    when (orderResponse) {
                        Loading -> CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = colorScheme.onPrimary,
                            strokeWidth = 5.dp
                        )

                        else -> Text(
                            text = stringResource(
                                R.string.pay,
                                format.format(summary.total)
                            )
                        )

                    }

                }
            )
            SpacerVerticalTwo()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.checkout_final_message),
                textAlign = TextAlign.Center,
                style = typography.bodyMedium
            )
            SpacerVerticalTwo()
            Surface(
                shape = CardDefaults.shape,
                color = colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(Dimens.gird_two)) {
                    Text(
                        text = stringResource(R.string.order_summary),
                        style = typography.titleMedium
                    )
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.total),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.total)
                            ),
                            style = typography.titleMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.subtotal),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.subTotal)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.tax),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.tax)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.shipping),
                            style = typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                format.format(summary.shippiing)
                            ),
                            style = typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

/*
@Preview
@Composable
fun AddressListItemPreview() {
    RobinAppPreview {
        PaymentListItem(
            selected = true,
            onPaymentSelected = {},
            payment = PreviewMocks.paymentData
        )
    }
}

@Preview
@Composable
fun PaymentListItemPreview() {
    RobinAppPreview {
        AddressListItem(
            selected = true,
            index = 5,
            address = PreviewMocks.address,
            onAddressSelected = {}
        )
    }
}

@Preview(
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE,
    device = "spec:width=500dp,height=1080dp,dpi=160"
)
@Composable
fun SummaryPreview() {
    RobinAppPreview {
        Summary(
            Success(listOf(PreviewMocks.cartItem)),
            onClick = {},
            Success((true))
        )
    }
}

@Preview(wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun ShippingPreview() {
    RobinAppPreview {
        Surface {
            Column(Modifier.padding(Dimens.gird_two)) {
                Shipping(
                    Success(listOf(PreviewMocks.address, PreviewMocks.address)),
                    currentAddress = remember { mutableStateOf("4") },
                    refreshAddress = {}, {}
                )
            }
        }
    }
}

@Preview(wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE)
@Composable
fun PaymentPreview() {
    RobinAppPreview {
        Surface {
            Column(Modifier.padding(Dimens.gird_two)) {
                Payment(
                    paymentResponse = Success(
                        listOf(
                            PreviewMocks.paymentData,
                            PreviewMocks.paymentData
                        )
                    ),
                    refreshPayments = {},
                    currentPayment = remember { mutableStateOf("4") }, onAddPaymentClick = {}
                )
            }
        }
    }
}

@Preview(wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE, device = "id:pixel_6_pro")
@Composable
fun CompactPreview() {
    RobinAppPreview {
        Surface {
            Column(Modifier.padding(Dimens.gird_two)) {
                Shipping(
                    Success(listOf(PreviewMocks.address, PreviewMocks.address)),
                    currentAddress = remember { mutableStateOf("4") },
                    refreshAddress = {}, {}
                )
                SpacerHorizontalThree()
                Payment(
                    paymentResponse = Success(
                        listOf(
                            PreviewMocks.paymentData,
                            PreviewMocks.paymentData
                        )
                    ),
                    refreshPayments = {},
                    currentPayment = remember { mutableStateOf("4") }, onAddPaymentClick = {}
                )
                SpacerHorizontalThree()
                Summary(
                    Success(listOf(PreviewMocks.cartItem)),
                    onClick = {},
                    Success(true)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE,
    device = "spec:width=1920dp,height=1080dp,dpi=405,orientation=landscape", group = "final-view",
    fontScale = 1.5f
)
@Composable
fun ExpandedPreview() {
    RobinAppPreview {
        Surface {
            RobinAppBar(
                title = "order", onBack = { */
/*TODO*//*
 },
                content = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(it), contentAlignment = Alignment.TopCenter
                    ) {
                        Row(
                            modifier = Modifier
                                .widthIn(300.dp, 1500.dp)
                                .align(Alignment.TopCenter)
                        ) {
                            Column(
                                Modifier
                                    .padding(Dimens.gird_two)
                                    .fillMaxWidth(.60f)
                            ) {
                                Shipping(
                                    Success(listOf(PreviewMocks.address, PreviewMocks.address)),
                                    currentAddress = remember { mutableStateOf("4") },
                                    refreshAddress = {}, {}
                                )
                                Payment(
                                    paymentResponse = Success(
                                        listOf(
                                            PreviewMocks.paymentData,
                                            PreviewMocks.paymentData
                                        )
                                    ),
                                    refreshPayments = {},
                                    currentPayment = remember { mutableStateOf("4") },
                                    onAddPaymentClick = {}
                                )
                            }
                            Column(
                                Modifier
                                    .padding(Dimens.gird_two)
                                    .widthIn(300.dp, 500.dp)
                            ) {
                                SpacerVerticalFour()
                                Summary(
                                    Success(listOf(PreviewMocks.cartItem)),
                                    onClick = {},
                                    Success(true)
                                )
                            }
                        }
                    }
                },
            )
        }
    }
}*/
