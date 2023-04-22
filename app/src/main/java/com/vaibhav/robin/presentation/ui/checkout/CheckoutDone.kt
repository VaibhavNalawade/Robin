package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.theme.Values
import com.vaibhav.robin.presentation.ui.theme.harmonizeWithPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutDone(navController: NavController) {
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (maxWidth > 460.dp)
                Box(modifier = Modifier.width(460.dp)) {
                    CheckoutDoneUI {
                        navController.popBackStack()
                    }
                }
            else
                CheckoutDoneUI {
                    navController.popBackStack()
                }
        }
    }
}

@Composable
private fun CheckoutDoneUI(onContinueShopping: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Icon(
            modifier = Modifier.size(96.dp),
            painter = painterResource(id = R.drawable.check_circle),
            contentDescription = null,
            tint = colorScheme.harmonizeWithPrimary(Color(0xff26c281))
        )
        SpacerVerticalTwo()
        Text(
            text = stringResource(R.string.your_order_has_been_received),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        SpacerVerticalTwo()
        Text(
            text = stringResource(R.string.checkout_success_msg),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        SpacerVerticalTwo()
        Button(
            modifier = Modifier.defaultMinSize(300.dp),
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.shopping_bag),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(R.string.continue_shopping))
            },
            onClick = onContinueShopping
        )
    }
}

@Preview
@Composable
fun CheckoutDoneUiPreview() {
    RobinAppPreview {
        Box(modifier = Modifier.padding(Values.Dimens.gird_two)) {
            CheckoutDoneUI {}
        }
    }
}