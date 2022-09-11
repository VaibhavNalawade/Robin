@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.product

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values.*
import com.vaibhav.robin.presentation.theme.Values.Dimens.appbarSize
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
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class
)
@Composable
fun ProductDetails(
    viewModel: ProductViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {

    val id = navController.currentBackStackEntry?.arguments?.getString("Id") ?: ""
    LaunchedEffect(key1 = true, block = { viewModel.setProductId(id) })
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val expandedFab by remember { derivedStateOf { bottomSheetState.bottomSheetState.isCollapsed } }


    Scaffold(modifier = Modifier,
        floatingActionButton = {
            /*    val fabState = fabState(addToCart = addToCart,viewModel,navController,snackBarHostState,
                    rememberCoroutineScope())*/

            ExtendedFloatingActionButton(
                onClick = {}, /*fabState.OnClick*/
                expanded = expandedFab,
                icon = /*fabState.icon*/{
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = stringResource(R.string.added_to_cart),
                        tint = Color.Green
                    )
                },
                text = { Text(text = /*fabState.text*/"Add to cart") },
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValue ->
            BottomSheet(
                viewModel = viewModel,
                navController = navController,
                snackBarHostState = snackBarHostState,
                bottomSheetState = bottomSheetState,
                padding = paddingValue
            )
        })
}
/*
data class FabState(
    val text: String = "", val icon: @Composable () -> Unit = {}, val OnClick: () -> Unit = {}
)

@Composable
fun fabState(
    addToCart: AddCartItemUiState,
    viewModel: ProductViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope
): FabState {
    return when (addToCart) {
        is AddCartItemUiState.Success -> FabState(text = stringResource(R.string.added_to_cart),
            icon = {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = stringResource(R.string.added_to_cart),
                    tint = Color.Green
                )
            },
            OnClick = {
                scope.launch {
                    showSnackbar(message = "Item Added To Cart",
                        actionLabel = "Go to Cart",
                        snackBarHostState,
                        actionPerformed = {
                            navController.navigate(RobinDestinations.CART)
                        })
                }
            })
        is AddCartItemUiState.Loading -> FabState(text = stringResource(R.string.loading), icon = {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp), strokeWidth = 2.dp
            )
        }, OnClick = {
            scope.launch {
                showSnackbar(
                    message = "Loading...",
                    snackbarHostState = snackBarHostState,

                    )
            }
        })
        is AddCartItemUiState.Error -> FabState(text = stringResource(R.string.error_occurred),
            icon = {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = stringResource(R.string.error_occurred),
                    tint = Color.Red
                )
            },
            OnClick = {
                scope.launch {
                    showSnackbar(message = "Something Went Wrong",
                        actionLabel = "Reload",
                        snackBarHostState,
                        actionPerformed = {
                            TODO()
                        })
                }
            })
        is AddCartItemUiState.AlreadyExits -> FabState(text = stringResource(R.string.already_exist_in_cart),
            icon = {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = stringResource(id = R.string.already_exist_in_cart),
                    tint = Color.Green
                )
            },
            OnClick = {
                scope.launch {
                    showSnackbar(message = "Item already in Cart",
                        actionLabel = "Go to Cart",
                        snackBarHostState,
                        actionPerformed = {
                            navController.navigate(RobinDestinations.CART)
                        })
                }
            })
        is AddCartItemUiState.Ready -> FabState(text = stringResource(R.string.add_to_cart),
            icon = {
                Icon(
                    imageVector = Icons.Filled.AddShoppingCart,
                    contentDescription = stringResource(R.string.add_to_cart),
                )
            },
            OnClick = {

            })
    }
}*/


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    viewModel: ProductViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
    bottomSheetState: BottomSheetScaffoldState,
    padding: PaddingValues
) {
    val onObjectHeight = remember { mutableStateOf(0) }
    val response = viewModel.productResponse

    BottomSheetScaffold(
        modifier = Modifier.padding(padding),
        sheetContent = {
            when (response) {
                is Success -> {
                    FrontLayer(viewModel, navController)
                }
                is Loading -> {
                    FrontScreenLoading()
                }
                is Error -> {
                    //todo nedd atteb=ntion here
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                    )
                }
            }
        },
        scaffoldState = bottomSheetState,
        content = {
            BackLayer(
                viewModel, onObjectHeight, navController,
            )
        },
        sheetShape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
        sheetPeekHeight = calculatePeakHeight(onObjectHeight.value)
    )
}


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
                        bannerImage = response.data.variant[viewModel.selectedVariant.value].media.images,
                        contentScale = ContentScale.Crop,
                        urlParam = "&w=640&q=80",
                    ) {}
                }
            }
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(appbarSize)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonContainer {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                    }
                }
                ButtonContainer {
                    var checked by remember { mutableStateOf(false) }
                    IconToggleButton(checked = checked, onCheckedChange = { checked = it }) {
                        val tint by animateColorAsState(
                            if (checked) Color(0xFFEC407A) else Color(
                                0xFFB0BEC5
                            )
                        )
                        Icon(
                            Icons.Filled.Favorite,
                            contentDescription = "Localized description",
                            tint = tint
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FrontLayer(viewModel: ProductViewModel, navController: NavHostController) {
    /**
     * Todo- Remember To remove Surface layer When Using Material 3 BottomSheet
     * Well it is workaround for Material2 Background Issue With Material 3 wrapper
     * */
    Surface(modifier = Modifier.fillMaxSize(),
        tonalElevation = Dimens.surface_elevation_1,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calculateFrontLayoutHeight(height = appbarSize))
            ) {
                BottomSheetHandle()
                when (val product = viewModel.productResponse) {
                    is Error -> TODO()
                    Loading -> TODO()
                    is Success -> {
                        val scrollState = rememberScrollState()
                        Column(modifier = Modifier.verticalScroll(scrollState)) {
                            val selectedVariant = viewModel.selectedVariant
                            val selectedSize = viewModel.selectedSize
                            TitleDescription(
                                product.data,
                                selectedVariant.value,
                                selectedSize.value
                            )
                            VariantSelector(
                                product.data,
                                selectedVariant,
                                selectedSize
                            )
                            Size(product.data, selectedVariant.value, selectedSize)
                            Details(product.data)
                            Rating(viewModel.stars) {
                                navController.navigate(
                                    RobinDestinations.review(
                                        Id = navController.currentBackStackEntry?.arguments?.getString(
                                            "Id"
                                        )!!,
                                        name = product.data.name,
                                        image = URLEncoder.encode(
                                            product.data.variant[selectedVariant.value].media.selection,
                                            StandardCharsets.UTF_8.toString()
                                        ),
                                        star = viewModel.stars.value
                                    )
                                )
                            }
                            Review(viewModel.commentResponse)
                        }
                    }
                }
            }
        })
}


@Composable
fun BottomSheetHandle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.gird_one),
        contentAlignment = Alignment.Center
    ) {
        Surface(modifier = Modifier
            .height(4.dp)
            .width(32.dp),
            color = colorScheme.onSurfaceVariant.copy(0.40f),
            shape = shapes.extraSmall,
            content = {})
    }
}

@Composable
fun TitleDescription(product: Product, selectedVariant: Int, selectedSize: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.gird_two),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.gird_two,
                    vertical = Dimens.gird_one
                )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = product.name,
                style = typography.headlineMedium.copy(color = colorScheme.onSurfaceVariant)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Filled.CurrencyRupee,
                    contentDescription = null,
                    tint = colorScheme.tertiary
                )
                SpacerHorizontalOne()
                Text(
                    text = product.variant[selectedVariant].size[selectedSize].price.retail.toInt()
                        .toString(),
                    style = typography.titleLarge.copy(colorScheme.tertiary)
                )
                SpacerHorizontalOne()
                Icon(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    imageVector = Icons.Filled.StarHalf,
                    contentDescription = null,
                    tint = colorScheme.tertiary
                )
                SpacerHorizontalOne()
                Text(
                    text = "Not Rated",
                    style = typography.titleLarge.copy(colorScheme.tertiary)
                )
                SpacerHorizontalOne()
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "(0 reviews)",
                    style = typography.labelSmall.copy(colorScheme.tertiary)
                )
            }
            SpacerVerticalOne()
            Text(
                text = product.description,
                overflow = TextOverflow.Ellipsis,
                style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant),
            )
            SpacerVerticalOne()
            BulletPoints(product)
            SpacerVerticalOne()
        }
    }
}

@Composable
fun BulletPoints(productData: Product) {
    val textColor = colorScheme.onSurfaceVariant
    productData.subDescription.forEach { map ->
        SpaceBetweenContainer(verticalAlignment = Alignment.CenterVertically) {
            map["0"]?.let { title ->
                Text(
                    text = title,
                    maxLines = 1,
                    style = typography.bodyMedium.copy(textColor)
                )
            }
            Divider(
                modifier = Modifier
                    .width(Dimens.gird_four)
                    .padding(horizontal = Dimens.gird_one),
                color = colorScheme.onSurfaceVariant
            )
            map["1"]?.let { text ->
                Text(
                    text = text,
                    maxLines = 1,
                    style = typography.bodySmall.copy(textColor)
                )
            }
        }
    }
}

@Composable
fun VariantSelector(
    product: Product,
    selectedVariant: MutableState<Int>,
    selectedSize: MutableState<Int>
) {
    SpacerVerticalTwo()
    LazyRow {

        product.variant.forEachIndexed { index, variant ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedVariant.value
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
                            selectedVariant.value = index
                            selectedSize.value = 0
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
    product: Product,
    selectedType: Int,
    selectedSize: MutableState<Int>
) {
    SpacerVerticalTwo()
    LazyRow {
        product.variant[selectedType].size.forEachIndexed { index, size ->
            item {
                SpacerHorizontalTwo()
                val selectedThis = index == selectedSize.value
                FilterChip(
                    selected = selectedThis,
                    onClick = { selectedSize.value = index },
                    label = { Text(size.size) },
                    leadingIcon = if (selectedThis) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else null
                )
            }
        }
    }
    SpacerVerticalTwo()
}

@Composable
fun Details(data: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.gird_two),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    horizontal = Dimens.gird_two,
                    vertical = Dimens.gird_one
                )
        ) {
            Text(
                stringResource(id = R.string.details),
                style = typography.headlineSmall
            )
            SpacerVerticalOne()
            data.details.forEach { map ->
                var title = true
                map.forEach {
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
fun Rating(stars: MutableState<Int>, onClick: () -> Unit) {
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
    Row(modifier = Modifier.padding(horizontal = Dimens.gird_two)) {
        for (i in 0..4 step 1) {
            IconButton(modifier = Modifier.size(64.dp),
                onClick = {
                    stars.value = i + 1
                    onClick()
                },
                content = {
                    Icon(
                        modifier = Modifier.size(48.dp),
                        imageVector = if (stars.value > i) Icons.Outlined.Star else
                            Icons.Outlined.StarOutline,
                        contentDescription = null,
                        tint = colorScheme.primary
                    )
                }
            )
        }
    }
}

@Composable
fun Review(commentResponse: Response<List<Review>>) {
    when(commentResponse){
        is Error -> TODO()
        Loading -> Loading()
        is Success ->
            commentResponse.data.forEach{
                    ReviewContainer(it)
            }
        }
    }


@Composable
fun ReviewContainer(review: Review) {
    Card(
        modifier = Modifier
            .padding(Dimens.gird_two)
            .fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(Dimens.gird_two)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularImage(
                    modifier = Modifier.size(52.dp),
                    contentDescription = null,
                    image = review.profileImage
                )
                SpacerHorizontalOne()
                Column {
                    Text(text = review.userName)
                    Row() {
                        Text(review.rating.toString())
                        Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                    }
                }
            }
            SpacerVerticalOne()
            Text(
                text = review.content,
                style = typography.bodyMedium.copy(
                    colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
fun ButtonContainer(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.size(42.dp),
        tonalElevation = Dimens.surface_elevation_4,
        shape = CircleShape,
    ) {
        Box(Modifier.padding(Dimens.gird_half)) {
            content()
        }
    }
}

@Composable
fun FrontScreenLoading() {
    Surface( tonalElevation = Dimens.surface_elevation_4) {
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
}

suspend fun showSnackbar(
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
}


/**
 *Fixme same problem as peak height
 */

@Composable
fun calculateFrontLayoutHeight(height: Dp) = LocalConfiguration.current.screenHeightDp.dp - height


/**
 *Calculate Peak & @return
 *  onObjectHeight */


/** FIXME: Bug Detected on Secondary Display height peak calculation not working also on some phones.
FIXME:Debug the Calculation of height sightly wrong*/

@Composable
fun calculatePeakHeight(onObjectHeight: Int) = with(LocalDensity.current) {
    (LocalConfiguration.current.screenHeightDp.dp - onObjectHeight.toDp()) + WindowInsets.statusBars.asPaddingValues()
        .calculateTopPadding() + Dimens.bottomSheetOnTop
}

