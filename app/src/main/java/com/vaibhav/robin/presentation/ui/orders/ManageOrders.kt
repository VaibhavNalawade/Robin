package com.vaibhav.robin.presentation.ui.orders

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.ui.common.RobinAppBar
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageOrders(
    navController: NavController
) {

}

@Composable
fun OrderList(
    orderItems: List<OrderItem>,
    onClick: () -> Unit,
    selectable: Boolean = false,
    selectedIndex: Int
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "${orderItems.size} items in your orders "
    )
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        content = {
            itemsIndexed(orderItems) { index, orderItem ->

            }
        }
    )
}

@Composable
fun OrderListItem(
    orderItem: OrderItem,
    onClick: () -> Unit,
    selectable: Boolean = false,
    selectedThis: Boolean = false
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        headlineContent = {
            Text(text = orderItem.items.firstOrNull()?.productName + " ${orderItem.items.size - 1} Items")
        },
        overlineContent = {
            Text(text = "Expected on 23 May")
        },
        trailingContent = {
            if (selectable)
                Checkbox(
                    checked = selectedThis,
                    onCheckedChange = {}
                )
            else
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = ""
                )
        },
        leadingContent = {
            RobinAsyncImage(
                modifier = Modifier.size(56.dp),
                contentDescription = "",
                model = orderItem.items.firstOrNull()?.productImage
            )
        },
    )
}



@Preview(group = "orderListItem")
@Composable
fun OrderOrderItemSelectablePreview() {
    RobinAppPreview {
        OrderListItem(
            onClick = {},
            orderItem = PreviewMocks.orderItem,
            selectable = true,
            selectedThis = true
        )
    }
}
@Preview(group = "orderListItem", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderOrderItemSelectablePreviewDark() {
    RobinAppPreview {
        OrderListItem(
            onClick = {},
            orderItem = PreviewMocks.orderItem,
            selectable = true,
            selectedThis = true
        )
    }
}
@Preview(group = "orderListItem")
@Composable
fun OrderOrderItemPreview() {
    RobinAppPreview {
        OrderListItem(
            onClick = {},
            orderItem = PreviewMocks.orderItem,
        )
    }
}

@Preview(group = "orderListItem", uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:width=600dp,height=850.9dp,dpi=440", fontScale = 1.2f
)
@Composable
fun OrderOrderItemPreviewDark() {
    RobinAppPreview {
        OrderListItem(
            onClick = {},
            orderItem = PreviewMocks.orderItem,
        )
    }
}