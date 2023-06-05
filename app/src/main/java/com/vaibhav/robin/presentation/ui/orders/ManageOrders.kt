package com.vaibhav.robin.presentation.ui.orders

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.generateDeliveryStatus
import com.vaibhav.robin.presentation.generateOrderName
import com.vaibhav.robin.presentation.ui.cart.EmptyCart
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import com.vaibhav.robin.presentation.priceFormat
import com.vaibhav.robin.presentation.DATE_DD_MMM
import com.vaibhav.robin.presentation.DATE_TEXT_DAY_FORMAT
import com.vaibhav.robin.presentation.DATE_TEXT_MONTH
import com.vaibhav.robin.presentation.generateCardName
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalFour
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageOrders(
    navController: NavController,
    orderItems: Response<List<OrderItem>>,
    viewModel: ManageOrderViewModel
) {
    LaunchedEffect(
        key1 = false,
        block = {
            if (!viewModel.getAuthState())
                navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                    popUpTo(
                        route = RobinDestinations.CART
                    ) {
                        inclusive = true
                    }
                }
        }
    )
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_orders),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.arrow_back),
                                contentDescription = stringResource(id = R.string.navigation_back)
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier.padding(padding)
        ) {
            if (maxWidth > 600.dp) {
                (orderItems as? Success)?.let {
                    ExpandedLayout(
                        orderItems = it,
                        viewModel.selectedOrderItemIndex
                    )
                }
            } else {
                CompactLayout(orderItems) {
                    navController.navigate(RobinDestinations.manageOrdersDetails((orderItems as Success).data[it].id))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailsCompact(
    navController: NavController,
    orders: Response<List<OrderItem>>
) {
    val id = remember {
        navController.currentBackStackEntry?.arguments?.getString(
            RobinDestinations.MANAGE_ORDERS_DETAILS_ARG
        ) ?: throw NullPointerException()
    }
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.order_details),
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
        when (orders) {
            is Error -> Box(Modifier.padding(padding)) {
                ShowError(exception = orders.exception) {}
            }

            Loading -> Box(Modifier.padding(padding)) {
                Loading()
            }

            is Success -> {
                val t = orders.data.find { it.id == id }
                if (t != null)
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .padding(horizontal = Dimens.gird_two)
                            .verticalScroll(rememberScrollState())
                    ) {

                        SpacerVerticalTwo()
                        OutlinedCard {
                            OrderDetailsCompact(t)
                        }
                        SpacerVerticalTwo()
                        Text(
                            text = stringResource(R.string.shipment_details),
                            style = typography.titleMedium
                        )
                        SpacerVerticalTwo()
                        OutlinedCard {
                            ShipmentDetails(orderItems = t)
                        }
                        SpacerVerticalTwo()
                        Text(
                            text = stringResource(R.string.order_summary),
                            style = typography.titleMedium
                        )
                        SpacerVerticalTwo()
                        OutlinedCard {
                            Summary(cartItem = t.items)
                        }
                        SpacerVerticalTwo()
                        Text(
                            text = stringResource(R.string.payment_information),
                            style = typography.titleMedium
                        )
                        SpacerVerticalTwo()
                        OutlinedCard {
                            PaymentInformation(orderItems = t)
                        }
                        SpacerVerticalTwo()
                    }
                else
                    ShowError(exception = IllegalArgumentException()) {}
            }
        }
    }
}

@Composable
private fun CompactLayout(
    orderItems: Response<List<OrderItem>>,
    onClick: (Int) -> Unit
): Unit {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        OrderList(
            cartItemResponse = orderItems,
            retry = { /*TODO*/ },
            onBrowse = { /*TODO*/ },
            onClick = onClick,
            selectIndex = Int.MAX_VALUE
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ExpandedLayout(
    orderItems: Success<List<OrderItem>>,
    selectedOrderItem: MutableState<Int>
) {
    Row(
        modifier = Modifier
            .widthIn(300.dp, 1500.dp)
    ) {
        Column(
            Modifier
                .padding(Dimens.gird_two)
                .width(300.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OrderList(
                cartItemResponse = orderItems,
                retry = { /*TODO*/ },
                onBrowse = { /*TODO*/ },
                onClick = {
                    selectedOrderItem.value = it
                },
                selectIndex = selectedOrderItem.value
            )
        }
        val t = orderItems.data[selectedOrderItem.value]
        Column(
            modifier = Modifier
                .padding(Dimens.gird_two)
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedCard(modifier = Modifier.fillMaxWidth()) {
                FlowRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(modifier = Modifier.width(300.dp)) {
                        OrderDetailsCompact(t)
                    }
                    Box(modifier = Modifier.width(300.dp)) {
                        Summary(cartItem = t.items)
                    }
                    Box(modifier = Modifier.width(300.dp)) {
                        PaymentInformation(orderItems = t)
                    }
                }
            }
            SpacerVerticalFour()
            OutlinedCard {
                ShipmentDetails(orderItems = t)
            }
        }
    }
}

@Composable
private fun OrderList(
    cartItemResponse: Response<List<OrderItem>>,
    retry: () -> Unit,
    onBrowse: () -> Unit,
    compactLayout: Boolean = true,
    onClick: (Int) -> Unit,
    selectIndex: Int
) {
    val n = (cartItemResponse as? Success)?.data?.size ?: 0
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_three),
        text = stringResource(R.string.items_in_your_order, n),
        maxLines = 1,
        style = typography.bodySmall
    )
    when (cartItemResponse) {
        is Error ->
            ShowError(
                exception = cartItemResponse.exception,
                retry = retry
            )
        Loading -> Loading()
        is Success -> {
            if (cartItemResponse.data.isNotEmpty())
                cartItemResponse.data.forEachIndexed { i, item ->
                    if (compactLayout)
                        OrderListItem(
                            orderItem = item,
                            onClick = onClick,
                            selected = selectIndex == i,
                            index = i
                        )
                }
            else
                EmptyCart(onBrowse)
        }
    }
}
@Composable
fun OrderDetailsCompact(orderItem: OrderItem) {
    Column(modifier = Modifier.padding(Dimens.gird_two)) {
        SpaceBetweenContainer {
            Text(
                text = stringResource(R.string.order_date),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
            Text(
                text = SimpleDateFormat(
                    DATE_TEXT_MONTH,
                    Locale.getDefault()
                ).format(orderItem.orderPlacedDate),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
        }
        SpacerVerticalOne()
        SpaceBetweenContainer {
            Text(
                text = stringResource(R.string.order),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
            Text(
                text = orderItem.id.take(25),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
        }
        SpacerVerticalOne()
        SpaceBetweenContainer {
            Text(
                text = stringResource(R.string.order_total),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.items,
                    priceFormat.format(orderItem.totalPrice),
                    orderItem.items.size
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
fun PaymentInformation(orderItems: OrderItem) {
    Column(modifier = Modifier.padding(Dimens.gird_two)) {
        Text(
            text = stringResource(id = R.string.payment_method),
            style = typography.titleMedium
        )
        Text(
            text = generateCardName(orderItems.paymentMethod).asString(),
            style = typography.bodyMedium
        )
        SpacerVerticalTwo()
        Text(
            text = stringResource(R.string.address),
            style = typography.titleMedium
        )
        Text(
            text = orderItems.shippingAddress,
            style = typography.bodyMedium
        )
    }
}


@Composable
fun ShipmentDetails(orderItems: OrderItem) {
    Column(modifier = Modifier.padding(Dimens.gird_two)) {
        Text(
            text = generateDeliveryStatus(orderItems.expectedDeliveryDate).asString(),
            style = typography.titleMedium
        )
        SpacerVerticalOne()
        Text(
            text = stringResource(R.string.delivery_estimate),
            style = typography.bodyMedium
        )
        SpacerVerticalOne()
        Text(
            text = SimpleDateFormat(
                DATE_TEXT_DAY_FORMAT,
                Locale.getDefault()
            ).format(orderItems.expectedDeliveryDate),
            style = typography.bodyMedium,
            color = colorScheme.primary
        )
        SpacerVerticalOne()
        Text(
            text = stringResource(R.string.items_only, orderItems.items.size),
            style = typography.bodyMedium,
        )
        SpacerVerticalOne()
        orderItems.items.forEach {
            CartListItem(it)
        }
    }
}

@Composable
private fun Summary(cartItem: List<CartItem>) {
    val summary = calculateSummary(cartItem)
    Column(modifier = Modifier.padding(Dimens.gird_two)) {
        SpaceBetweenContainer {
            Text(
                text = stringResource(R.string.total),
                style = typography.bodyMedium
            )
            Text(
                text = stringResource(
                    R.string.local_price,
                    priceFormat.format(summary.total)
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
                    priceFormat.format(summary.subTotal)
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
                    priceFormat.format(summary.tax)
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
                    priceFormat.format(summary.shippiing)
                ),
                style = typography.bodyMedium
            )
        }
    }
}

@Composable
private fun CartListItem(
    cartItems: CartItem,
) {
    ListItem(
        headlineContent = {
            Text(
                text = cartItems.productName,
                maxLines = 1
            )
        },
        overlineContent = {
            Text(
                text = cartItems.brandName,
                maxLines = 1
            )
        },
        supportingContent = {
            Text(
                text = stringResource(
                    id = R.string.local_price,
                    priceFormat.format(cartItems.price)
                ),
                maxLines = 1
            )
        },
        leadingContent = {
            RobinAsyncImage(
                modifier = Modifier.size(56.dp),
                contentDescription = null,
                model = cartItems.productImage
            )
        }
    )
}


@Composable
private fun OrderListItem(
    orderItem: OrderItem,
    onClick: (Int) -> Unit,
    selected: Boolean = false,
    index: Int = 0
) {
    ListItem(
        modifier = Modifier
            .clip(CardDefaults.shape)
            .clickable { onClick(index) },
        headlineContent = {
            Text(text = generateOrderName(orderItem))
        },
        overlineContent = {
            Text(
                text = stringResource(
                    R.string.expected_on,
                    SimpleDateFormat(
                        DATE_DD_MMM,
                        Locale.getDefault()
                    ).format(orderItem.expectedDeliveryDate)
                )
            )
        },
        leadingContent = {
            RobinAsyncImage(
                modifier = Modifier.size(56.dp),
                contentDescription = null,
                model = orderItem.items.firstOrNull()?.productImage,
                contentScale = ContentScale.Crop
            )
        },
        tonalElevation = if (selected) Dimens.surface_elevation_4 else Dimens.surface_elevation_0
    )
}

