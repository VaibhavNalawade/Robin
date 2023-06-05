package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vaibhav.robin.R
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.OrderSummary
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.priceFormat
import com.vaibhav.robin.presentation.ui.theme.Values

@Composable
fun Summary(
    cartItem: Response.Success<List<CartItem>>?,
    buttonLabel: @Composable RowScope.(OrderSummary) -> Unit,
    textMessage: String,
    onClick: () -> Unit,
) {
    val summary = cartItem?.let { calculateSummary(it.data) } ?: OrderSummary()
    Surface(
        modifier = Modifier.widthIn(max = 480.dp),
        shape = CardDefaults.shape,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_2)
    ) {
        Column(modifier = Modifier.padding(Values.Dimens.gird_two)) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                content = { buttonLabel(summary) }
            )
            SpacerVerticalTwo()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = textMessage,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            SpacerVerticalTwo()
            Surface(
                shape = CardDefaults.shape,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(Values.Dimens.gird_two)) {
                    Text(
                        text = stringResource(R.string.order_summary),
                        style = MaterialTheme.typography.titleMedium
                    )
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.total),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                priceFormat.format(summary.total)
                            ),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.subtotal),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                priceFormat.format(summary.subTotal)
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.tax),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                priceFormat.format(summary.tax)
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    SpacerVerticalOne()
                    SpaceBetweenContainer {
                        Text(
                            text = stringResource(R.string.shipping),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = stringResource(
                                R.string.local_price,
                                priceFormat.format(summary.shippiing)
                            ),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun SummaryPreview() {
    Summary(
        cartItem = Response.Success(listOf(PreviewMocks.cartItem)),
        buttonLabel = { Text(text = "ButtonLabelMessage") },
        textMessage = "This is Text Message shown below Button",
        onClick = {}
    )
}