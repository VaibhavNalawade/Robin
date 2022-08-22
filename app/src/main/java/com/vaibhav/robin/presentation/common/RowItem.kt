@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.theme.RobinTypography
import com.vaibhav.robin.presentation.theme.Values.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowItem(
   placeholder: Boolean = false,
    onClick: () -> Unit = {}
){/*

    val onSurface = MaterialTheme.colorScheme.onSurface
    OutlinedCard(onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            RobinAsyncImage(
                modifier = Modifier
                    .size(128.dp)
                    .padding(Dimens.gird_one)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop,
                model = unsplashGet.urls?.small,
                contentDescription = stringResource(id = R.string.product_image)
            )

            Column(modifier = Modifier.padding(Dimens.gird_one)) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(placeholder),
                    text = unsplashGet.user?.name ?: "PlaceHolder",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = RobinTypography.titleMedium.copy(onSurface)
                )

                Spacer(modifier = Modifier.height(Dimens.gird_quarter))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(placeholder),
                    text = stringResource(id = R.string.lorem_ipsum),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = RobinTypography.bodySmall.copy(onSurface)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimens.gird_half, horizontal = Dimens.gird_one),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {

                        Text(
                            modifier = Modifier.placeholder(placeholder),
                            text = "$180",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = RobinTypography.bodySmall.copy(onSurface),
                            textDecoration = TextDecoration.LineThrough
                        )

                        Spacer(modifier = Modifier.height(Dimens.gird_quarter))

                        Text(
                            modifier = Modifier.placeholder(placeholder),
                            text = "$123",
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            style = RobinTypography.bodyMedium.copy(
                                MaterialTheme.colorScheme.tertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            modifier = Modifier.placeholder(placeholder),
                            text = "Color",
                            style = RobinTypography.bodySmall.copy(onSurface)
                        )

                        Spacer(modifier = Modifier.height(Dimens.gird_quarter))

                        Row {
                            unsplashGet.color?.let { Color(it.toColorInt()) }
                                ?.let {
                                    Circle(
                                        modifier = Modifier
                                            .placeholder(placeholder), color = it
                                    )
                                }

                            Spacer(modifier = Modifier.width(Dimens.gird_quarter))

                            Circle(
                                modifier = Modifier
                                    .placeholder(placeholder),
                                color = MaterialTheme.colorScheme.primaryContainer
                            )

                            Spacer(modifier = Modifier.width(Dimens.gird_quarter))

                            Circle(
                                modifier = Modifier
                                    .placeholder(placeholder),
                                color = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        }
                    }
                }
            }
        }
    }
*/}