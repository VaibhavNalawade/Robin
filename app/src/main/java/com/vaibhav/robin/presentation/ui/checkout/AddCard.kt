package com.vaibhav.robin.presentation.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Newdesign() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add credit or debit card",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { /* doSomething() */ },
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
        },
        bottomBar = {
                    Box(
                        modifier = Modifier
                            .padding(
                                vertical = 16.dp,
                                horizontal = 24.dp
                            ),
                        content={
                            Button(
                                enabled = false,
                                onClick = { /*TODO*/ },
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    Text(text = "Add Card")
                                }
                            )
                        }
                    )
        },
        content = { innerPadding ->
            val layoutDirection= LocalLayoutDirection.current
            Column(
              modifier=Modifier
                  .padding(
                      top = innerPadding.calculateTopPadding()+Dimens.gird_two,
                      bottom = innerPadding.calculateBottomPadding()+Dimens.gird_two,
                      start = innerPadding.calculateStartPadding(layoutDirection)+Dimens.gird_three,
                      end = innerPadding.calculateEndPadding(layoutDirection)+Dimens.gird_three
                  )
            ) {
                SpacerVerticalOne()
                val t = remember {
                    mutableStateOf(TextFieldState())
                }
             PaymentCardTextField(state =t)
                SpacerVerticalOne()
                if (t.value.text.isEmpty())
                OutlinedButton(
                    onClick = {},
                    content={
                        Icon(painter = painterResource(id = R.drawable.photo_camera), contentDescription = "")
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Scan Card")
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun AddCardPreview() {
    RobinAppPreview {
       Newdesign()
    }
}

