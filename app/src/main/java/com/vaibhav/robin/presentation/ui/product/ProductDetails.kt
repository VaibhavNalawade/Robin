package com.vaibhav.robin.presentation.ui.product

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.vaibhav.robin.R
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.domain.model.CurrentUserProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Error
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.model.Response.Success
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.timeStampHandler
import com.vaibhav.robin.presentation.ui.common.CircularImage
import com.vaibhav.robin.presentation.ui.common.ExpandingCard
import com.vaibhav.robin.presentation.ui.common.Loading
import com.vaibhav.robin.presentation.ui.common.ProfileInitial
import com.vaibhav.robin.presentation.ui.common.RobinAsyncImage
import com.vaibhav.robin.presentation.ui.common.ShowError
import com.vaibhav.robin.presentation.ui.common.SlideInTopVisibilityAnimation
import com.vaibhav.robin.presentation.ui.common.SpaceBetweenContainer
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalHalf
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalOne
import com.vaibhav.robin.presentation.ui.common.SpacerHorizontalTwo
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalOne
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalThree
import com.vaibhav.robin.presentation.ui.common.SpacerVerticalTwo
import com.vaibhav.robin.presentation.ui.common.Star
import com.vaibhav.robin.presentation.ui.common.tweenSpec
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails(
    currentUserProfileData: MutableStateFlow<CurrentUserProfileData>,
    product: Product,
    cartItems: CartUiState.Success?,
    messageBarState: MessageBarState,
    productIsFavourite: Boolean,
    loadProductDetails: (String) -> Unit,
    reviewsResponse: Response<List<Review>>,
    userReviewResponse: Response<Review>,
    addItemToCart: (items: CartItemAdder) -> Unit,
    onBackPressed: () -> Unit,
    routeToLogin: () -> Unit
) {
    val currentUser = currentUserProfileData.collectAsStateWithLifecycle()
    val selectedVariant = rememberSaveable { mutableStateOf(product.variantIndex.first()) }
    val selectedSize = rememberSaveable { mutableIntStateOf(0) }
    val ratingStars = rememberSaveable { mutableIntStateOf(0) }

    LaunchedEffect(
        key1 = true,
        block = {
            loadProductDetails(product.id)
            cartItems?.cartItems?.forEach {
                if (it.productId == product.id) {
                    messageBarState.addError("Item already exits in your cart")
                    return@forEach
                }
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
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconToggleButton(
                        checked = productIsFavourite,
                        onCheckedChange = {
                            if (currentUser.value.userAuthenticated)
                            /*        if (it)TODO Disabled temporry
                                        viewModel.addFavorite(selectedProductUiState)
                                    else
                                        viewModel.removeFavorite(selectedProductUiState.id)*/
                            else routeToLogin()

                        },
                        content = {
                            if (productIsFavourite)
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
                product.media[selectedVariant.value]?.let { item ->
                    val pager = rememberCarouselState { item.size }
                    HorizontalUncontainedCarousel(
                        state = pager,
                        modifier = Modifier.fillMaxWidth(),
                        flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(pager),
                        itemWidth = 300.dp,
                        itemSpacing = 16.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { i ->
                        RobinAsyncImage(
                            modifier = Modifier
                                .height(450.dp)
                                .maskClip(shapes.extraLarge),
                            model = item[i],
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                SpacerVerticalTwo()
                TitleDescription(
                    product = product,
                    selectedVariant = selectedVariant.value,
                    selectedSize = selectedSize.intValue
                )
                SpacerVerticalOne()
                Box(modifier = Modifier.padding(horizontal = Dimens.gird_three)) {
                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            addItemToCart(
                                CartItemAdder(
                                    product = product,
                                    selectedVariant = selectedVariant.value,
                                    selectedSize = selectedSize.intValue
                                )
                            )
                        },
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
                    product = product,
                    selectedVariantState = selectedVariant,
                    selectedSizeState = selectedSize
                )
                Size(
                    product = product,
                    selectedVariant = selectedVariant.value,
                    selectedSizeState = selectedSize
                )
                Details(product = product)
                Rating(
                    starState = ratingStars,
                    review = userReviewResponse,
                    onClick = {
                        //  navController.navigate(RobinDestinations.review(star = viewModel.stars.value))
                    }
                )
                SpacerVerticalTwo()
                Text(
                    modifier = Modifier.padding(horizontal = Dimens.gird_four),
                    text = stringResource(id = R.string.rating_and_reviews),
                    style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
                )
                SpacerVerticalOne()
                when (reviewsResponse) {
                    is Error -> ShowError(exception = reviewsResponse.exception) {}

                    Loading -> Loading()
                    is Success -> {
                        if (reviewsResponse.data.isNotEmpty())
                            reviewsResponse.data.forEach { review ->
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
    selectedVariantState: MutableState<String>,
    selectedSizeState: MutableIntState
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
                            selectedSizeState.intValue = 0
                        },
                    contentDescription = null,
                    model = (product.media[variant] as List<*>)[0],
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}


@Composable
fun Size(
    product: Product, selectedVariant: String, selectedSizeState: MutableIntState
) {
    SpacerVerticalOne()
    LazyRow {
        product.sizes[selectedVariant]?.forEachIndexed { index, size ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedSizeState.intValue
                FilterChip(selected = selectedThis,
                    onClick = { selectedSizeState.intValue = index },
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

data class CartItemAdder(val product: Product, val selectedVariant: String, val selectedSize: Int)


@Preview
@Composable
private fun ProductDetailsPreview() {
    RobinAppPreview {
        ProductDetails(
            currentUserProfileData = MutableStateFlow(CurrentUserProfileData()),
            product = PreviewMocks.product,
            cartItems = CartUiState.Success(PreviewMocks.cartItem),
            messageBarState = MessageBarState(),
            productIsFavourite = true,
            loadProductDetails = {},
            reviewsResponse = Response.Error(Exception()),
            userReviewResponse = Response.Error(Exception()),
            addItemToCart = {},
            onBackPressed = {},
            routeToLogin = {}
        )
    }
}