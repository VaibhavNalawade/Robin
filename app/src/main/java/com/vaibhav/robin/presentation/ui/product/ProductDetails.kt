package com.vaibhav.robin.presentation.ui.product

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ScaleFactor
import androidx.compose.ui.layout.lerp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.*
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.timeStampHandler
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values.*
import kotlin.math.abs


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun ProductDetails(
    viewModel: ProductViewModel,
    navController: NavHostController,
    selectedProductUiState: Product,
    cartItems: Response<List<CartItem>>,
    messageBarState: MessageBarState
) {
    LaunchedEffect(
        key1 = true,
        block = {
            viewModel.loadCurrentUserReview(productId = selectedProductUiState.id)
            viewModel.loadReview(productId = selectedProductUiState.id)
            viewModel.checkFavorite(productId = selectedProductUiState.id)
            when(cartItems){
                is Success -> {
                    cartItems.data.forEach {
                        if (it.productId==selectedProductUiState.id) {
                            messageBarState.addError("Item already exits in your cart")
                            return@forEach
                        }
                    }
                }
                else->{}
            }
        }
    )

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Details",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconToggleButton(
                        checked = viewModel.favouriteToggleButtonState,
                        onCheckedChange = {
                            if (viewModel.getAuthState())
                                if (it)
                                    viewModel.addFavorite(selectedProductUiState)
                                else
                                    viewModel.removeFavorite(selectedProductUiState.id)
                            else
                                navController.navigate(RobinDestinations.LOGIN_ROUTE) {
                                    popUpTo(RobinDestinations.LOGIN_ROUTE) {
                                        inclusive = true
                                    }
                                }
                        },
                        content = {
                            if (viewModel.favouriteToggleButtonState)
                                Icon(
                                    painter = painterResource(id = R.drawable.favorite_fill),
                                    contentDescription = "Localized description",
                                )
                            else Icon(
                                painter = painterResource(id = R.drawable.favorite),
                                contentDescription = "Localized description",
                            )
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                val pager= rememberPagerState()
                Surface(tonalElevation = Dimens.surface_elevation_5) {
                    selectedProductUiState.media[viewModel.selectedVariant.value
                        ?: selectedProductUiState.variantIndex[0]].let { item ->
                        if (item != null) {
                            HorizontalPager(
                               state = pager ,
                                pageCount = item.size,
                                pageSize = PageSize.Fill,
                                contentPadding = PaddingValues(horizontal = Dimens.gird_four),
                                pageSpacing = Dimens.gird_half
                            ) {
                                AsyncImage(
                                    modifier= Modifier
                                        .aspectRatio(0.6f)
                                        .graphicsLayer {
                                            lerp(
                                                start = ScaleFactor(.8f, .8f),
                                                stop = ScaleFactor(1f, 1f),
                                                fraction = if (it == pager.currentPage) 1f
                                                else abs(pager.currentPageOffsetFraction) + .5f
                                            ).also { scale ->
                                                scaleX = scale.scaleX
                                                scaleY = scale.scaleY
                                            }

                                            alpha = lerp(
                                                start = ScaleFactor(.5f, .5f),
                                                stop = ScaleFactor(1f, 1f),
                                                fraction = 1f - pager.currentPageOffsetFraction
                                            ).scaleY
                                        },
                                    model = item[it],
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }
                SpacerVerticalTwo()
                TitleDescription(
                    product = selectedProductUiState,
                    selectedVariant = viewModel.selectedVariant.value
                        ?: selectedProductUiState.variantIndex[0],
                    selectedSize = viewModel.selectedSize.value ?: 0
                )
                SpacerVerticalOne()
                Box(modifier = Modifier.padding(horizontal = Dimens.gird_three)){
                Button(modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.addCartItem(selectedProductUiState) },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.add_shopping_cart),
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Add To Cart")
                    }
                )
                }
                SpacerVerticalOne()
                VariantSelector(
                    product = selectedProductUiState,
                    selectedVariantState = viewModel.selectedVariant,
                    selectedSizeState = viewModel.selectedSize
                )
                Size(
                    product = selectedProductUiState,
                    selectedVariant = viewModel.selectedVariant.value
                        ?: selectedProductUiState.variantIndex[0],
                    selectedSizeState = viewModel.selectedSize
                )
                Details(product = selectedProductUiState)
                Rating(
                    starState = viewModel.stars,
                    review = viewModel.yourReviewResponse,
                    onClick = {
                        navController.navigate(RobinDestinations.review(star = viewModel.stars.value))
                    }
                )
                SpacerVerticalTwo()
                Text(
                    modifier = Modifier.padding(horizontal = Dimens.gird_four),
                    text = stringResource(id = R.string.rating_and_reviews),
                    style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
                )
                SpacerVerticalOne()
                when (val reviewResponse = viewModel.reviewsResponse) {
                    is Error -> ShowError(exception = reviewResponse.message) {}

                    Loading -> Loading()
                    is Success -> {
                        if (reviewResponse.data.isNotEmpty())
                            reviewResponse.data.forEach { review ->
                                ReviewContainer(review = review)
                            }
                        else
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    model = R.drawable.desert,
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
                SpacerVerticalThree()
            }

        }
    )
}

@Composable
fun TitleDescription(product: Product, selectedVariant: String, selectedSize: Int) {
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
                    image = product.brandLogo,
                    contentDescription = null
                )
                SpacerHorizontalOne()
                Text(
                    text = product.brandName,
                    style = typography.bodyMedium.copy(colorScheme.primary)
                )
                SpacerHorizontalOne()
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.star_rate_half),
                    contentDescription = null,
                    tint = colorScheme.primary
                )
                Text(
                    text = product.ratingStars?.toString() ?: "Not Rated",
                    style = typography.bodyMedium.copy(colorScheme.primary)
                )
                SpacerHorizontalOne()
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "(${product.ratingCount} reviews)",
                    style = typography.labelSmall.copy(colorScheme.primary)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    modifier = Modifier,
                    text = "₹ ${product.sizes[selectedVariant]?.get(selectedSize)?.get("price")}",
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
    selectedVariantState: MutableState<String?>,
    selectedSizeState: MutableState<Int?>
) {
    SpacerVerticalOne()
    LazyRow {

        product.variantIndex.forEach { variant ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = variant == selectedVariantState.value
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
                            selectedVariantState.value = variant
                            selectedSizeState.value = 0
                        },
                    contentDescription = null,
                    model = (product.media[variant] as List<*>)[0],
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Size(
    product: Product, selectedVariant: String, selectedSizeState: MutableState<Int?>
) {
    SpacerVerticalOne()
    LazyRow {
        product.sizes[selectedVariant]?.forEachIndexed { index, size ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedSizeState.value
                FilterChip(selected = selectedThis,
                    onClick = { selectedSizeState.value = index },
                    label = { Text(size.get("size") as String) },
                    leadingIcon = if (selectedThis) {
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.check_circle),
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
                if (!review.profileImage.isBlank())
                    CircularImage(
                        modifier = Modifier.size(36.dp),
                        contentDescription = null,
                        image = review.profileImage
                    )
                else
                    ProfileInitial(profileName = review.userName)
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
                            painter = painterResource(id = R.drawable.star_rate_half),
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
                            painter = painterResource(id = R.drawable.favorite_fill),
                            contentDescription = "Localized description"
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.favorite),
                            contentDescription = "Localized description"
                        )
                    }
                }
            } else {
                FilledTonalIconButton(onClick = { onClick() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.drive_file_rename_outline),
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
