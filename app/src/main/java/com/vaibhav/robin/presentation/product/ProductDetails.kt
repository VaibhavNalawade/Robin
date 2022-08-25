@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.vaibhav.robin.presentation.product

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.navigation.RobinDestinations
import com.vaibhav.robin.presentation.common.*
import com.vaibhav.robin.presentation.theme.Values.Dimens
import com.vaibhav.robin.presentation.theme.Values.Dimens.appbarSize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.vaibhav.robin.domain.model.Response.*
import kotlin.random.Random

//Todo Add video support on back layer

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ProductDetails(
    viewModel: ProductViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState
) {

    val id = navController.currentBackStackEntry?.arguments?.getString("Id") ?: ""
  LaunchedEffect(key1 = true, block = {viewModel.setProductId(id)})
    val bottomSheetState = rememberBottomSheetScaffoldState()
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { bottomSheetState.bottomSheetState.isCollapsed } }

    Scaffold(floatingActionButton = {
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
        modifier = Modifier
            .padding(padding),
        sheetContent = {
            when (response) {
                is Success -> {
                    FrontLayer(viewModel)
                }
                is Loading -> {
                    FrontScreenLoading()
                }
                is Error -> {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red))
                    Log.e("Thist sukc",response.message)
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
    Surface(color = colorScheme.surfaceVariant) {
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
                        bannerImage = response.data.type[0].media.images,
                        contentScale = ContentScale.Crop,
                        urlParam = "&w=640&q=80",
                    ) {}
                }
            }
            SpaceBetweenContainer(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .height(appbarSize)
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
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
fun FrontLayer(viewModel: ProductViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(calculateFrontLayoutHeight(height = appbarSize))
            .background(color = colorScheme.surface)
    ) {
        SpacerVerticalOne()
        backdropHandle()
        SpacerVerticalOne()
        val scrollState = rememberScrollState()
        Column(modifier = Modifier.verticalScroll(scrollState)) {
/*            val selectedType by viewModel.selectedType
            BrandPrice(product, selectedType)
            DividerHorizontal()
            TitleDescription(product, selectedType)
            DividerHorizontal()
            Size(this, product, selectedType)
            DividerHorizontal()
            Color(columnScope = this, product, viewModel)
            DividerHorizontal()
            Specification(product, selectedType)

            ReviewRateing(LocalConfiguration.current.screenWidthDp)*/
        }
    }
}
/*
@Composable
fun Color(columnScope: ColumnScope, product: Product, viewModel: ProductViewModel) =
    with(columnScope) {

        SpacerVerticalOne()
        Text(
            modifier = Modifier.padding(horizontal = Dimens.gird_two),
            text = "Select Color",
            style = typography.titleLarge
        )

        SpacerVerticalOne()

        val state = rememberScrollState(0)
        Row(Modifier.horizontalScroll(state)) {

            product.type.forEachIndexed { i, it ->

                SpacerHorizontalTwo()
                CircularImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable { viewModel.selectedType.value = i },
                    contentDescrption = "",
                    Image = it.media.selection,
                    contentScale = ContentScale.Crop
                )
            }
        }
        SpacerVerticalTwo()
    }

@Composable
fun Size(columnScope: ColumnScope, product: Product, selectedType: Int) = with(columnScope) {
    Spacer(modifier = Modifier.height(Dimens.gird_one))

    Text(
        modifier = Modifier.padding(horizontal = Dimens.gird_two),
        text = "Select Size",
        style = typography.titleLarge
    )

    Spacer(modifier = Modifier.height(Dimens.gird_two))

    val state = rememberScrollState(0)
    Row(Modifier.horizontalScroll(state)) {

        product.type[selectedType].size.forEach {

            Spacer(modifier = Modifier.width(Dimens.gird_one))

            */
/** Todo Waiting for Chips replace button *//*

            TextButton(
                modifier = Modifier.size(42.dp),
                onClick = { */
/*TODO*//*
 },
                border = BorderStroke(
                    2.dp,
                    color = colorScheme.primary
                ),
                shape = shapes.small
            ) {
                Text(text = it)
            }
        }
    }
    Spacer(modifier = Modifier.height(Dimens.gird_two))
}

@Composable
fun BrandPrice(productData: Product, selectedType: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical =Dimens.gird_one,horizontal = Dimens.gird_two)
    ) {
        SpaceBetweenContainer(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpaceBetweenContainer(verticalAlignment = Alignment.CenterVertically)
            {
                CircularImage(
                    Modifier.size(brandingImageSize),
                    contentDescrption = stringResource(id = R.string.branding_Image),
                    Image = productData.brand.url
                )
                Spacer(modifier = Modifier.width(Dimens.gird_half))

                Text(
                    text = productData.brand.name,
                    style = typography.bodyMedium.copy(
                        color = colorScheme.tertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = "${productData.type[selectedType].price.currency} ${productData.type[selectedType].price.price}",
                style = typography.titleLarge.copy(color = colorScheme.tertiary)
            )
        }
    }
}


@Composable
fun ReviewRateing(widthDp: Int) {
    Text(
        text = "Customer reviews", style = typography.headlineSmall
    )
    val lazyListState = rememberLazyListState()
    LazyRow(
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    )
    {
        items(MockProvider().commentMock.size) { index ->
            ReviewCard(widthDp = widthDp, MockProvider().commentMock[index], 240f)
        }
    }
}

@Composable
fun SpecificationBlock(loop: DetailsPoint) {
    Column(Modifier.fillMaxWidth()) {
        loop.one?.let {
            Text(
                text = it,
                style = typography.titleMedium,
            )
        }
        loop.two?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.three?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.four?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.five?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.six?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.seven?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.eight?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.nine?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
        loop.ten?.let {
            Text(
                text = it,
                style = typography.bodyMedium,
            )
        }
    }
}

@Composable
fun Specification(product: Product, selectedType: Int) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        SpacerVerticalOne()
        Text(
            text = "Specification",
            style = typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(Dimens.gird_two))
            product.type[selectedType].details.forEach {
                SpecificationBlock(it)
                SpacerVerticalTwo()
            }
    }
}

@Composable
fun TitleDescription(productData: Product, selectedType: Int) {
    Column(modifier = Modifier.padding(horizontal = Dimens.gird_two)) {
        SpacerVerticalOne()
        Text(
            text = productData.name,
            style = typography.headlineMedium
        )
        SpacerVerticalOne()
        Text(
            text = productData.type[selectedType].description.description ?: "",
            overflow = TextOverflow.Ellipsis,
            style = typography.bodyMedium.copy(colorScheme.onSurface.copy(subtextAlpha)),
        )
        SpacerVerticalOne()
        BulletPoints(productData,selectedType)
        SpacerVerticalOne()
    }
}

@Composable
fun BulletPoints(productData: Product, selectedType: Int) {
    productData.type[selectedType].description.subDescription.forEach {
        SpaceBetweenContainer(verticalAlignment = Alignment.CenterVertically) {
            it.one?.let { it1 -> Text(text = it1, maxLines = 1, style = typography.bodyMedium) }
            Divider(modifier = Modifier
                .width(24.dp)
                .padding(horizontal = 8.dp))
            it.two?.let { it1 -> Text(text = it1, maxLines = 1, style = typography.bodySmall) }
        }
    }
}
*/
@Composable
fun ButtonContainer(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier
            .size(42.dp),
        color = colorScheme.surface.copy(.6f),
        shape = CircleShape,
    ) {
        Box(Modifier.padding(Dimens.gird_half)) {
            content()
        }
    }
}

@Composable
fun FrontScreenLoading() {
    Surface {
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

@Composable
fun backdropHandle() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Surface(
            color = colorScheme.onSurface.copy(.3f),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .width(48.dp)
                .height(8.dp),
            content = {}
        )
    }
}

suspend fun showSnackbar(
    message: String,
    actionLabel: String? = null,
    snackbarHostState: SnackbarHostState,
    dismissed: () -> Unit = {},
    actionPerformed: () -> Unit = {}
) = when (
    snackbarHostState.showSnackbar(
        message = message,
        actionLabel = actionLabel,
    )
) {
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
 * @param onObjectHeight */


/** FIXME: Bug Detected on Secondary Display height peak calculation not working also on some phones.
FIXME:Debug the Calculation of height sightly wrong*/

@Composable
fun calculatePeakHeight(onObjectHeight: Int) = with(LocalDensity.current) {
    (LocalConfiguration.current.screenHeightDp.dp - onObjectHeight.toDp()) +
            WindowInsets.statusBars.asPaddingValues().calculateTopPadding() +
            Dimens.bottomSheetOnTop
}

/*
@Preview(group = "LoadingState")
@Composable
fun LoadingStateLight() {
    RobinAppPreviewScaffold {
        FrontScreenLoading()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "LoadingState")
@Composable
fun LoadingStateDark() {
    RobinAppPreviewScaffold {
        FrontScreenLoading()
    }
}

@Preview(group = "TitleDescription")
@Composable
fun TitleDescriptionLight() {
    RobinAppPreviewScaffold {
        TitleDescription(Product(),0)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "TitleDescription")
@Composable
fun TitleDescriptionDark() {
    RobinAppPreviewScaffold {
        TitleDescription(Product(),0)
    }
}

@Preview(group = "BarndPrice")
@Composable
fun BarndPriceLight() {
    RobinAppPreviewScaffold {
        BrandPrice(Product(),0)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "BarndPrice")
@Composable
fun BrandPriceDark() {
    RobinAppPreviewScaffold {
        BrandPrice(Product(),0)
    }
}

@Preview(group = "Size")
@Composable
fun SizeLight() {
    RobinAppPreviewScaffold {
        Column {
            Size(this, Product(),0)
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "Size")
@Composable
fun SizeDark() {
    RobinAppPreviewScaffold {
        Column {
            Size(this, Product(),0)
        }
    }
}

@Preview(group = "Color")
@Composable
fun ColorLight() {
    RobinAppPreviewScaffold {
        Column(Modifier.fillMaxWidth()) {
            Color(this, Product(),ProductViewModel())
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "Color")
@Composable
fun ColorDark() {
    RobinAppPreviewScaffold {
        Column(Modifier.fillMaxWidth()) {
            Color(this, Product(), ProductViewModel())
        }
    }
}

@Preview(group = "Specification")
@Composable
fun SpecificationLight() {
    RobinAppPreviewScaffold {
        Specification(Product(),0)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES, group = "Specification")
@Composable
fun SpecificationDark() {
    RobinAppPreviewScaffold {
        Specification(Product(),0)
    }
}
*/
