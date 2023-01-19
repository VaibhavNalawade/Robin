package com.vaibhav.robin.presentation.ui.product

import android.app.PictureInPictureUiState
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.*
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values.*
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens.appbarSize
import com.vaibhav.robin.presentation.timeStampHandler
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random


/**
 * Todo: In this screen we use legacy Material2 BottomSheet so Wait for Material 3 Implementation.
 * TBD is Currently Unknown.
 *
 * Todo: Add video media support on back layer.
 */
@OptIn(
    ExperimentalMaterialApi::class
)
@Composable
fun ProductDetails(
    viewModel: ProductViewModel,
    navController: NavHostController,
    selectedProductUiState: Product
) {

    val id = remember {
        navController.currentBackStackEntry?.arguments?.getString("Id") ?: ""
    }

}

data class FabState(
    val text: String = "", val icon: @Composable () -> Unit = {}, val OnClick: () -> Unit = {}
)

@Composable
fun BackLayer(
    viewModel: ProductViewModel,
    sheetPeekHeight: MutableState<Int>,
    navController: NavHostController,
) {
    val response = viewModel.productResponse
    Surface(tonalElevation = Dimens.surface_elevation_1) {
        Box(
            modifier = Modifier
                .fillMaxHeight(.7f)
                .onGloballyPositioned { sheetPeekHeight.value = it.size.height },
        ) {
            when (response) {
                is Error -> {}
                is Loading -> {}
                is Success -> {
                    ImageSlider(
                        bannerImage = response.data.media[response.data.variant[viewModel.selectedVariant.value]]!!,
                        contentScale = ContentScale.Crop,
                        urlParam = "&w=640&q=80",
                    ) {}
                }
            }
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(appbarSize)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = ""
                    )
                }


                FilledIconToggleButton(
                    checked = viewModel.favouriteToggleButtonState,
                    onCheckedChange = {
                        if (viewModel.getAuthState())
                            if (it)
                                viewModel.addFavorite()
                            else
                                viewModel.removeFavorite()
                        else
                            navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                                popUpTo(RobinDestinations.LOGIN_ROUTE) {
                                    inclusive = true
                                }
                            }
                    }) {
                    if (viewModel.favouriteToggleButtonState)
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Localized description",
                        )
                    else Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = "Localized description",
                    )
                }

            }
        }
    }
}
/*
*//**
 * Todo- Remember To remove Surface layer When Using Material 3 BottomSheet
 * Well it is workaround for Material2 Background Issue With Material 3 wrapper
 * *//*
@Composable
fun FrontLayer(viewModel: ProductViewModel, navController: NavHostController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(frontLayoutHeightCalculation()),
        tonalElevation = Dimens.surface_elevation_1,
    ) {
        Column {
            BottomSheetHandle()
            when (val product = viewModel.productResponse) {
                is Success -> LoadProductUi(
                    product = product.data, viewModel = viewModel, navController = navController
                )

                else -> {
                    TODO()
                }
            }
        }
    }
}

@Composable
fun LoadProductUi(product: Product, viewModel: ProductViewModel, navController: NavHostController) {

    val selectedVariant = viewModel.selectedVariant
    val selectedSize = viewModel.selectedSize

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.gird_one)
    ) {
        item {
            TitleDescription(
                product = product,
                selectedVariant = selectedVariant.value,
                selectedSize = selectedSize.value
            )
        }
        item {
            VariantSelector(
                product = product,
                selectedVariantState = selectedVariant,
                selectedSizeState = selectedSize
            )
        }

        item {
            Size(
                product = product,
                selectedVariant = selectedVariant.value,
                selectedSizeState = selectedSize
            )
        }

        item {
            Details(product = product)
        }

        item {
            LaunchedEffect(key1 = true, block = { viewModel.loadCurrentUserReview() })
            Rating(
                starState = viewModel.stars, review = viewModel.yourReviewResponse
            ) {
                navController.navigate(
                    RobinDestinations.review(
                        // todo revisedcode
                        Id = navController.currentBackStackEntry?.arguments?.getString(
                            "Id"
                        )!!, name = product.name, image = URLEncoder.encode(
                            product.variant[selectedVariant.value].media.selection,
                            StandardCharsets.UTF_8.toString()
                        ), star = viewModel.stars.value
                    )
                )
            }
        }

        item {
            LaunchedEffect(key1 = true, block = {
                viewModel.loadReview()
            })
            SpacerVerticalTwo()
            Text(
                modifier = Modifier.padding(horizontal = Dimens.gird_four),
                text = stringResource(id = R.string.rating_and_reviews),
                style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
            )
        }

        when (val reviewResponse = viewModel.reviewsResponse) {
            is Error -> item {
                ShowError(exception = reviewResponse.message) {
                }
            }

            Loading -> item {
                Loading()
            }

            is Success -> {
                if (reviewResponse.data.isNotEmpty())
                    items(reviewResponse.data) { review ->
                        ReviewContainer(review = review)
                    }
                else item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            model = R.drawable.cancel,
                            contentScale = ContentScale.Fit,
                            contentDescription = "",
                        )
                        Text(
                            text = stringResource(id = R.string.nothing_to_see_here),
                            style = typography.titleLarge
                        )
                        Text(
                            text = stringResource(id = R.string.encourage_write_review),
                            style = typography.titleMedium
                        )
                    }
                }
            }
        }
        item { SpacerVerticalFour() }
    }
}




@Composable
fun TitleDescription(product: Product, selectedVariant: Int, selectedSize: Int) {
    ExpandingCard(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.gird_two),
    ) { cardExpanded ->
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two, vertical = Dimens.gird_one
            )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.name,
                style = typography.headlineMedium.copy(color = colorScheme.onSurfaceVariant)
            )
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    modifier = Modifier.size(24.dp),
                    image = product.brand.url,
                    contentDescription = null
                )
                SpacerHorizontalOne()
                Text(
                    text = product.brand.name,
                    style = typography.bodyMedium.copy(colorScheme.primary)
                )
                SpacerHorizontalOne()
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = null,
                    tint = colorScheme.primary
                )
                Text(
                    text = product.rating.stars?.toString() ?: "Not Rated",
                    style = typography.bodyMedium.copy(colorScheme.primary)
                )
                SpacerHorizontalOne()
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "(${product.rating.count} reviews)",
                    style = typography.labelSmall.copy(colorScheme.primary)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(26.dp)
                        .align(Alignment.Bottom),
                    painter = painterResource(id = R.drawable.cancel),
                    contentDescription = null,
                    tint = colorScheme.primary
                )
                SpacerHorizontalOne()
                Text(
                    modifier = Modifier,
                    text = product.variant[selectedVariant].size[selectedSize].price.retail.toInt()
                        .toString(),
                    style = typography.titleLarge.copy(colorScheme.primary)
                )
            }
            SpacerVerticalOne()
            Text(
                modifier = Modifier.animateContentSize(
                    tweenSpec()
                ),
                text = product.description,
                maxLines = if (!cardExpanded) 4 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant),
            )
            SpacerVerticalOne()
            BulletPoints(product, cardExpanded)
        }
    }
}

@Composable
fun BulletPoints(productData: Product, visible: Boolean) {
    val textColor = colorScheme.onSurfaceVariant
    productData.subDescription.forEach { map ->
        SlideInTopVisibilityAnimation(visible = visible) {
            SpaceBetweenContainer(verticalAlignment = Alignment.CenterVertically) {
                map["0"]?.let { title ->
                    Text(
                        text = title, maxLines = 1, style = typography.bodyMedium.copy(textColor)
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(Dimens.gird_four)
                        .padding(horizontal = Dimens.gird_one), color = colorScheme.onSurfaceVariant
                )
                map["1"]?.let { text ->
                    Text(
                        text = text, maxLines = 1, style = typography.bodySmall.copy(textColor)
                    )
                }
            }
        }
    }
}

@Composable
fun VariantSelector(
    product: Product,
    selectedVariantState: MutableState<Int>,
    selectedSizeState: MutableState<Int>
) {
    SpacerVerticalOne()
    LazyRow {

        product.variant.forEachIndexed { index, variant ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedVariantState.value
                RobinAsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(shapes.small)
                        .border(
                            width = if (selectedThis) 2.dp else 1.dp,
                            color = if (selectedThis) colorScheme.primary else colorScheme.outline,
                            shape = shapes.small
                        )
                        .clickable {
                            selectedVariantState.value = index
                            selectedSizeState.value = 0
                        },
                    contentDescription = null,
                    model = variant.media.selection,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Size(
    product: Product, selectedVariant: Int, selectedSizeState: MutableState<Int>
) {
    SpacerVerticalOne()
    LazyRow {
        product.variant[selectedVariant].size.forEachIndexed { index, size ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedSizeState.value
                FilterChip(selected = selectedThis,
                    onClick = { selectedSizeState.value = index },
                    label = { Text(size.size) },
                    leadingIcon = if (selectedThis) {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.cancel),
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null)
            }
        }
    }
    SpacerVerticalOne()
}

@Composable
fun Details(product: Product) {
    ExpandingCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.gird_two),
    ) { expandedCard ->
        Column(
            modifier = Modifier.padding(
                horizontal = Dimens.gird_two, vertical = Dimens.gird_one
            )
        ) {
            Text(
                stringResource(id = R.string.details), style = typography.headlineSmall
            )
            SpacerVerticalOne()
            product.details.forEachIndexed { index, map ->
                var title = true
                map.forEach {
                    if ((!expandedCard) && index > 4) {
                        return@forEachIndexed
                    }
                    if (title) {
                        Text(
                            text = it.value,
                            style = typography.titleMedium.copy(colorScheme.onSurfaceVariant)
                        )
                        title = false
                    } else Text(
                        text = it.value,
                        style = typography.bodySmall.copy(colorScheme.onSurfaceVariant)
                    )
                }
                SpacerVerticalOne()
            }
        }

    }

}

@Composable
fun Rating(starState: MutableState<Int>, review: Response<Review>, onClick: () -> Unit) {
    when (review) {
        is Error -> WriteReview(starState, onClick)
        Loading -> Loading()
        is Success -> UserReview(onClick, review.data)
    }
}

@Composable
fun UserReview(onClick: () -> Unit, review: Review) {
    SpacerVerticalOne()
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_four),
        text = stringResource(id = R.string.your_review),
        style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
    )
    SpacerVerticalOne()
    ReviewContent(
        paddingValues = PaddingValues(horizontal = Dimens.gird_four),
        review = review,
        likeButtonVisibility = false,
        onClick
    )
}

@Composable
fun WriteReview(stars: MutableState<Int>, onClick: () -> Unit) {
    SpacerVerticalTwo()
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_four),
        text = stringResource(id = R.string.rate_this_product),
        style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
    )
    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_four),
        text = stringResource(R.string.review_explain),
        style = typography.bodySmall.copy(colorScheme.onSurfaceVariant)
    )
    SpacerVerticalOne()
    Box(
        modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
    ) {
        Star(onClick = {
            stars.value = it
            onClick()
        }, stars = stars.value)
    }

}

@Composable
fun ReviewContainer(review: Review) {
    Card(
        modifier = Modifier
            .padding(horizontal = Dimens.gird_two)
            .fillMaxWidth(),
    ) {
        ReviewContent(paddingValues = PaddingValues(Dimens.gird_two), review = review)
    }
}

@Composable
fun ReviewContent(
    paddingValues: PaddingValues,
    review: Review,
    likeButtonVisibility: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column(modifier = Modifier.padding(paddingValues)) {
        SpaceBetweenContainer(modifier = Modifier.fillMaxWidth()) {
            Row {
                CircularImage(
                    modifier = Modifier.size(36.dp),
                    contentDescription = null,
                    image = review.profileImage
                )
                SpacerHorizontalOne()
                Column {
                    Text(
                        text = review.userName,
                        style = typography.titleSmall.copy(colorScheme.onSurfaceVariant)
                    )
                    Row {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .size(20.dp),
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                        SpacerHorizontalHalf()
                        Text(
                            text = review.rating.toString(),
                            style = typography.bodySmall.copy(colorScheme.primary)
                        )
                        SpacerHorizontalOne()
                        Text(
                            text = "• ${timeStampHandler(review.timeStamp)}",
                            style = typography.bodySmall.copy(colorScheme.primary),
                            maxLines = 1
                        )
                    }
                }
            }
            if (likeButtonVisibility) {
                var checked by remember { mutableStateOf(false) }
                FilledIconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
                    if (checked) {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Localized description"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.cancel),
                            contentDescription = "Localized description"
                        )
                    }
                }
            } else {
                OutlinedIconButton(onClick = { onClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.cancel),
                        contentDescription = null
                    )
                }
            }
        }

        SpacerVerticalOne()
        Text(
            text = review.content, style = typography.bodyMedium.copy(
                colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
fun FrontScreenLoading() {
    Surface(tonalElevation = Dimens.surface_elevation_4) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.gird_three)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .placeholder(true), content = {})
            for (i in 1..6 step 1) {
                SpacerVerticalTwo()
                Box(modifier = Modifier
                    .fillMaxWidth(
                        Random
                            .nextDouble(0.3, 1.0)
                            .toFloat()
                    )
                    .height(16.dp)
                    .placeholder(true), content = {})
            }
        }
    }
}*/

/*suspend fun showSnackbar(
    message: String,
    actionLabel: String? = null,
    snackbarHostState: SnackbarHostState,
    dismissed: () -> Unit = {},
    actionPerformed: () -> Unit = {}
) = when (snackbarHostState.showSnackbar(
    message = message,
    actionLabel = actionLabel,
)) {
    SnackbarResult.Dismissed -> dismissed.invoke()
    SnackbarResult.ActionPerformed -> actionPerformed.invoke()
}*/
/*

@Preview
@Composable
fun IntroductionPreview() {
    RobinAppPreview {
        TitleDescription(
            product = Product(
                name = "Regular Fit Oxford shirt",
                description = stringResource(id = R.string.lorem_ipsum),
                brand = Brand(
                    name = "Flame", url = stringResource(id = R.string.profile_photo_test)
                ),
                rating = Rating(count = 20020, stars = 4.0f),
                variant = listOf(
                    Variant(
                        size = listOf(
                            Size(
                                Price(
                                    retail = 500.7
                                )
                            )
                        )
                    )
                )
            ), selectedVariant = 0, selectedSize = 0
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun IntroductionPreviewDark() {
    RobinAppPreview {
        TitleDescription(
            product = Product(
                name = "Regular Fit Oxford shirt",
                description = stringResource(id = R.string.lorem_ipsum),
                brand = Brand(
                    name = "Flame", url = stringResource(id = R.string.profile_photo_test)
                ),
                rating = Rating(count = 20020, stars = 4.0f),
                variant = listOf(
                    Variant(
                        size = listOf(
                            Size(
                                Price(
                                    retail = 500.7
                                )
                            )
                        )
                    )
                )
            ), selectedVariant = 0, selectedSize = 0
        )
    }
}*/