package com.vaibhav.robin.presentation.ui.review

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.common.CircularImage
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens

@Composable
fun Review(
    navController: NavController,
    viewModel: ReviewViewModel,
    selectProduct:Product
) {
    val exe = Exception(NullPointerException())
    val arg = remember {
        navController.currentBackStackEntry?.arguments ?: throw exe
    }

    viewModel.stars = remember { arg.getInt("star") }

    val response = viewModel.response

    LaunchedEffect(key1 = true, block = {
        if (!viewModel.getAuthState()) {
            navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                popUpTo(
                    route = RobinDestinations.REVIEW_SIGNATURE
                ) {
                    inclusive = true
                }
            }
        }
    })
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Surface(
            tonalElevation = Dimens.surface_elevation_5
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(63.dp)
            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null
                            )
                        }
                    )
                    RobinAsyncImage(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(shapes.small),
                        contentDescription = null,
                        model = selectProduct.media[selectProduct.variantIndex[0]]?.get(0) ?:"",
                        contentScale = ContentScale.Crop
                    )
                    SpacerHorizontalOne()
                    Column {
                        Text(
                            text = selectProduct.name,
                            style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                        )
                        Text(
                            text = stringResource(R.string.rate_this_product),
                            style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant)
                        )
                    }
                }
                TextButton(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterEnd),
                    onClick = { viewModel.createReview(selectProduct) },
                    content = {
                        Text(text = stringResource(R.string.post))
                    })
            }
        }
        when (response) {
            is Response.Error -> ShowError(response.message) {}

            is Response.Loading -> Loading(modifier = Modifier.fillMaxSize())
            is Response.Success -> {
                if (response.data)
                    LaunchedEffect(key1 = true, block = { navController.popBackStack() })
                InitUI(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InitUI(viewModel: ReviewViewModel) {
    val userName = viewModel.userName
    val profilePhoto = viewModel.userPhoto
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        tonalElevation = Dimens.surface_elevation_1
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.gird_two)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    modifier = Modifier
                        .size(62.dp)
                        .align(Alignment.Top),
                    contentDescription = null,
                    image = profilePhoto
                )
                SpacerHorizontalTwo()
                Column {
                    Text(
                        text = userName,
                        style = typography.titleMedium
                    )
                    SpacerVerticalOne()
                    Text(
                        stringResource(R.string.review_policy),
                        style = typography.bodyMedium
                    )
                    Row {
                        val stars = viewModel.stars
                        for (i in 0..4 step 1) {
                            IconButton(
                                onClick = { viewModel.stars = i + 1 },
                                content = {
                                    Icon(
                                        modifier = Modifier.size(48.dp),
                                        imageVector = if (stars > i) Icons.Outlined.Star else Icons.Outlined.Warning,
                                        contentDescription = null,
                                        tint = colorScheme.primary
                                    )
                                })
                        }
                    }
                }
            }
            val textFieldState by viewModel.comment
            OutlinedTextField(
                modifier = Modifier
                    .defaultMinSize(minHeight = 250.dp)
                    .fillMaxWidth()
                    .padding(Dimens.gird_two),
                value = textFieldState.text,
                onValueChange = { viewModel.comment.value = textFieldState.copy(text = it) },
                placeholder = { Text(text = stringResource(R.string.review_write_hint)) }
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = Dimens.gird_two),
                text = "${textFieldState.text.length}/500"
            )
        }
    }
}

@Preview
@Composable
fun ReviewPreview() {
    RobinAppPreview {
        Review(
            navController = NavController(LocalContext.current),
            viewModel = hiltViewModel(),
            selectProduct = Product()
        )
    }
}