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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.vaibhav.robin.R
import com.vaibhav.robin.data.models.Media
import com.vaibhav.robin.data.models.Price
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.data.models.Size
import com.vaibhav.robin.data.models.Variant
import com.vaibhav.robin.domain.model.ProfileData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.presentation.RobinAppBarType
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.RobinNavigationType
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.common.*
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
                toggleFilter =filter
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
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobinAppBar(
    profileData: ProfileData?,
    messageBarState: MessageBarState,
    toggleDrawer: () -> Unit,
    scrollState: LazyGridState,
    navigationType: RobinNavigationType,
    appBarType: RobinAppBarType,
    toggleFilter: MutableState<Boolean>
) {
    when (navigationType) {
        RobinNavigationType.PERMANENT_NAVIGATION_DRAWER -> {
            if (appBarType != RobinAppBarType.COLLAPSING_APPBAR)
                PermanentAppBar()
            else CollapsingAppBar(
                profileData = profileData,
                messageBarState = messageBarState,
                toggleDrawer = toggleDrawer,
                scrollState = scrollState,
                toggleFilter =toggleFilter
            )
        }

        RobinNavigationType.NAVIGATION_DRAWER -> {
             CollapsingAppBar(messageBarState, profileData, toggleDrawer, scrollState, toggleFilter)
        }

        RobinNavigationType.NAVIGATION_RAILS -> {
            if (appBarType != RobinAppBarType.COLLAPSING_APPBAR)
                PermanentAppBar()
            else CollapsingAppBar(
                messageBarState,
                profileData,
                toggleDrawer,
                scrollState,
                toggleFilter
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
    toggleFilter: MutableState<Boolean>
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
        modifier = Modifier.graphicsLayer { translationY = position },
        color = colorScheme.surfaceColorAtElevation(Dimens.surface_elevation_2),
        shadowElevation = 1.dp
    ) {
        Column {
            SpacerVerticalOne()
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(horizontal = Dimens.gird_three)
                    .statusBarsPadding()
                    .height(height = 48.dp),
                tonalElevation = Dimens.surface_elevation_5,
                shadowElevation = 1.dp,
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
                            Icon(modifier=Modifier.size(24.dp),
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.gird_two),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                val scope = rememberCoroutineScope()
                LazyRow(
                    modifier = Modifier,
                    content = {
                        item {
                            FilterChip(
                                true,
                                onClick = {
                                },
                                label = { Text(text = "#Trending") }
                            )
                        }
                        item {
                            SpacerHorizontalOne()
                        }
                        item {
                            FilterChip(
                                true,
                                onClick = {

                                },
                                label = {
                                    Text(text = "#2022")
                                }
                            )
                        }
                    })
                SpacerHorizontalFour()
                Divider(
                    modifier = Modifier
                        .height(FilterChipDefaults.Height)
                        .width(1.dp)
                        .padding(vertical = Dimens.gird_quarter),
                    thickness = 1.dp
                )
                SpacerHorizontalOne()
                ElevatedFilterChip(selected = toggleFilter.value,
                    onClick = {
                        toggleFilter.value=true
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
fun PermanentAppBar() {
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
                    checked = false,
                    onCheckedChange = {},
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
                text = "28 Products",
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
                model = product.variant[0].media.images[0]
            )
            SpacerVerticalOne()
            Column(
                modifier = Modifier
                    .padding(horizontal = Dimens.gird_two)
            ) {
                Text(
                    text = product.name,
                    style = typography.bodyLarge,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
                Text(
                    text = "₹ ${product.variant[0].size[0].price.retail}",
                    style = typography.bodyMedium,
                    color = colorScheme.primary,
                    maxLines = 1
                )
                SpacerVerticalOne()
            }
        }
    }
}


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

@Preview(group = "AppBar", widthDp = 840)
@Composable
private fun PermanentAppbarPreviewLight() {
    RobinAppPreview {
        PermanentAppBar()
    }
}

@Preview(group = "AppBar", uiMode = UI_MODE_NIGHT_YES, widthDp = 840)
@Composable
private fun PermanentAppbarPreviewDark() {
    RobinAppPreview {
        PermanentAppBar()
    }
}