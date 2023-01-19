@file:OptIn(ExperimentalMaterial3Api::class)

package com.vaibhav.robin.presentation.ui.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppBarType
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.*
import com.vaibhav.robin.presentation.ui.theme.Values
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavHostController,
    profileUiState: ProfileData?,
    toggleDrawer: () -> Unit,
    productUiState: Response<List<Product>>,
    messageBarState: MessageBarState,
    navigationType: RobinNavigationType,
    appBarType: RobinAppBarType,
    filter: MutableState<Boolean>,
) {
    val lazyGridState = rememberLazyGridState()
    var items by remember {
        mutableStateOf(0)
    }
    Scaffold(
        modifier = Modifier,
        topBar = {
            RobinAppBar(
                profileData = profileUiState,
                toggleDrawer = toggleDrawer,
                messageBarState = messageBarState,
                scrollState = lazyGridState,
                navigationType = navigationType,
                appBarType = appBarType,
                toggleFilter = filter,
                productSize = items
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(RobinDestinations.CART)
                },
                content = {
                    Icon(
                        painterResource(id = R.drawable.shopping_cart),
                        contentDescription = "Localized description"
                    )
                }
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier.statusBarsPadding()
            ) {
                when (productUiState) {
                    is Response.Error -> ShowError(productUiState.message) {}
                    is Response.Loading -> Loading()
                    is Response.Success -> {
                        items = productUiState.data.size
                        if (items != 0)
                            LazyVerticalGrid(
                                contentPadding = PaddingValues(
                                    vertical = 110.dp,
                                    horizontal = Dimens.gird_one
                                ),
                                horizontalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                                verticalArrangement = Arrangement.spacedBy(Dimens.gird_one),
                                columns = GridCells.Adaptive(minSize = 164.dp),
                                state = lazyGridState
                            ) {
                                items(items = productUiState.data) { product ->
                                    GridItem(product = product) { id ->
                                        navController.navigate(
                                            RobinDestinations.product(id).also { Log.e("nav", it) })
                                    }
                                }
                            }
                        else EmptyProduct()
                    }
                }
            }
        }
    )
}


@Composable
fun RobinAppBar(
    profileData: ProfileData?,
    messageBarState: MessageBarState,
    toggleDrawer: () -> Unit,
    scrollState: LazyGridState,
    navigationType: RobinNavigationType,
    appBarType: RobinAppBarType,
    toggleFilter: MutableState<Boolean>,
    productSize: Int
) {
    when (navigationType) {
        RobinNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            if (appBarType != RobinAppBarType.COLLAPSING_APPBAR)
                PermanentAppBar(toggleFilter, productSize = productSize)
            else CollapsingAppBar(
                profileData = profileData,
                messageBarState = messageBarState,
                toggleDrawer = toggleDrawer,
                scrollState = scrollState,
                toggleFilter = toggleFilter,
                productSize = productSize
            )
        }

        RobinNavigationType.NAVIGATION_DRAWER -> {
            CollapsingAppBar(
                messageBarState,
                profileData,
                toggleDrawer,
                scrollState,
                toggleFilter,
                productSize
            )
        }

        RobinNavigationType.NAVIGATION_RAILS -> {
            if (appBarType != RobinAppBarType.COLLAPSING_APPBAR)
                PermanentAppBar(toggleFilter, productSize = productSize)
            else CollapsingAppBar(
                messageBarState,
                profileData,
                toggleDrawer,
                scrollState,
                toggleFilter,
                productSize
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingAppBar(
    messageBarState: MessageBarState,
    profileData: ProfileData?,
    toggleDrawer: () -> Unit,
    scrollState: LazyGridState,
    toggleFilter: MutableState<Boolean>,
    productSize: Int
) {
    var oldPosition by remember { mutableStateOf(0) }
    val scrollUp = remember { mutableStateOf(false) }

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex }
            .collect {
                if (oldPosition < it) {
                    oldPosition = it
                    scrollUp.value = true
                } else {
                    scrollUp.value = false
                    oldPosition = it
                }
            }
    }
    val position by animateFloatAsState(
        if (scrollUp.value) {
            -450f
        } else 0f
    )
    Surface(
        modifier = Modifier
            .graphicsLayer { translationY = position },
    ) {
        Column {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(horizontal = Dimens.gird_three)
                    .statusBarsPadding()
                    .height(height = 48.dp),
                tonalElevation = Dimens.surface_elevation_5,
                shape = RoundedCornerShape(percent = 100)
            ) {
                Box(
                    modifier = Modifier.padding(horizontal = Dimens.gird_one)
                ) {
                    IconButton(
                        modifier = Modifier.align(alignment = Alignment.CenterStart),
                        onClick = toggleDrawer,
                        content = {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(
                                    id = R.drawable.menu
                                ),
                                contentDescription = "",
                                tint = colorScheme.onSurfaceVariant
                            )
                        }
                    )
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.app_name),
                        style = typography.titleLarge.copy(
                            colorScheme.onSurfaceVariant
                        )
                    )
                    IconButton(modifier = Modifier.align(alignment = Alignment.CenterEnd),
                        onClick = { messageBarState.addError("Not Implemented") }
                    ) {
                        if (profileData?.image == null)
                            Icon(
                                modifier = Modifier.size(24.dp),
                                painter = painterResource(
                                    id = R.drawable.profile_placeholder
                                ),
                                contentDescription = "",
                                tint = colorScheme.onSurfaceVariant
                            )
                        else
                            CircularImage(
                                modifier = Modifier.size(size = 32.dp),
                                contentDescription = "",
                                image = profileData.image
                            )
                    }
                }
            }
            SpaceBetweenContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_four),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$productSize Products",
                    style = typography.titleSmall
                )
                SpacerHorizontalFour()
                SpacerHorizontalOne()
                FilterChip(
                    selected = toggleFilter.value,
                    onClick = {
                        toggleFilter.value = true
                        toggleDrawer()
                    },
                    label = { Text(text = stringResource(R.string.filter)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(
                                id = R.drawable.filter_alt
                            ),
                            contentDescription = null
                        )
                    },
                )
            }
        }
    }
}

@Composable
fun PermanentAppBar(toggleFilter: MutableState<Boolean>, productSize: Int) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.gird_four, vertical = Dimens.gird_two),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Surface(
                    tonalElevation = Dimens.surface_elevation_5,
                    shape = RoundedCornerShape(100)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(horizontal = Dimens.gird_one)
                            .height(48.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = Dimens.gird_one)
                                .align(Alignment.CenterStart),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = ""
                            )
                            SpacerHorizontalTwo()
                            SearchEditText()
                        }
                        Button(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            onClick = { /*TODO*/ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = ""
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = "Search")
                        }
                    }
                }
                FilledIconToggleButton(
                    checked = toggleFilter.value,
                    onCheckedChange = { toggleFilter.value = it },
                    content = {
                        Icon(
                            painter = painterResource(id = R.drawable.filter_alt),
                            contentDescription = ""
                        )
                    }
                )
            }
            SpacerVerticalTwo()
            Text(
                text = "$productSize Products",
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Composable
fun SearchEditText() {
    var textState by remember {
        mutableStateOf("")
    }
    val style = typography.labelLarge.copy(colorScheme.onSurfaceVariant)
    BasicTextField(
        modifier = Modifier,
        value = textState,
        onValueChange = { textState = it },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // TODO:
            }
        ),
        singleLine = true,
        textStyle = style,
        decorationBox = { innerTextField ->

            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                if (textState.isEmpty()) {
                    Text(
                        "Search in Robin Store",
                        style = style
                    )
                }
                innerTextField()
            }

        })
}


@Composable
fun GridItem(
    product: Product,
    onclick: (id: String) -> Unit
) {
    Surface(
        tonalElevation = Dimens.surface_elevation_5,
        onClick = {
            onclick(product.id)
        },
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier) {
            RobinAsyncImage(
                modifier = Modifier
                    .defaultMinSize(
                        minHeight = 220.dp,
                        minWidth = 164.dp
                    )
                    .fillMaxWidth(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                model = product.media["variant_0"]?.get(0)
            )
            SpacerVerticalOne()
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimens.gird_two)
            ) {
                Text(
                    text = product.name,
                    style = typography.titleSmall,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                val price = remember {
                    if (product.minPrice != product.maxPrice)
                        "₹ ${product.minPrice.toInt()} - ${product.maxPrice.toInt()}"
                    else
                        "₹ ${product.minPrice.toInt()}"
                }

                Text(
                    text = price,
                    style = typography.bodyMedium,
                    color = colorScheme.primary,
                    maxLines = 1
                )
                SpacerVerticalOne()
            }
        }
    }
}

@Composable
fun EmptyProduct() {
    Box {
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(
                    horizontal = Dimens.gird_two,
                    vertical = Dimens.gird_four
                ),
            text = stringResource(id = R.string.app_name),
            style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(Dimens.gird_four),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            RobinAsyncImage(
                modifier = Modifier,
                model = R.drawable.empty_products,
                contentDescription = null,
            )

            SpacerVerticalTwo()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.nothing_to_see_here),
                textAlign = TextAlign.Center,
                style = typography.titleLarge.copy(colorScheme.onSurfaceVariant)
            )
            SpacerVerticalOne()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Please clear the filters and try again",
                textAlign = TextAlign.Center,
                style = typography.bodyMedium.copy(colorScheme.onSurfaceVariant)
            )
            SpacerVerticalTwo()
            Button(onClick = {

            }) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}

/*

@Preview(group = "Grid", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun GirdPreviewDark() {
    RobinAppPreview {
        GridItem(
            Product(
                name = "Lora ipsum", variant = listOf(
                    Variant(
                        media = Media(listOf("")),
                        size = listOf(Size(price = Price(retail = 1299.00)))
                    )
                )
            )
        ) {}
    }
}

@Preview(group = "Grid")
@Composable
private fun GirdPreviewLight() {
    RobinAppPreview {
        GridItem(
            Product(
                name = "Lora ipsum", variant = listOf(
                    Variant(
                        media = Media(listOf("")),
                        size = listOf(Size(price = Price(retail = 1299.00)))
                    )
                )
            )
        ) {}
    }
}
*/

@Preview(group = "AppBar", widthDp = 840)
@Composable
private fun PermanentAppbarPreviewLight() {
    RobinAppPreview {
        // PermanentAppBar(true)
    }
}

@Preview(group = "AppBar", uiMode = UI_MODE_NIGHT_YES, widthDp = 840)
@Composable
private fun PermanentAppbarPreviewDark() {
    RobinAppPreview {
        // PermanentAppBar(true)
    }
}