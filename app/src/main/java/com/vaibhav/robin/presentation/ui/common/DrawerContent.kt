package com.vaibhav.robin.presentation.ui.common

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.flowlayout.FlowRow
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.FilterChipState
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import com.vaibhav.robin.presentation.ui.theme.Values.Shapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    userAuthenticated: Boolean,
    navController: NavHostController,
    signOut: () -> Unit,
    closeDrawer: () -> Unit,
    brandsUiState: SnapshotStateList<FilterChipState>,
    categoriesUiState: SnapshotStateList<FilterChipState>,
    navContent:@Composable ()->Unit,
    showNavContent: MutableState<Boolean>,
    onApply:()->Unit
) {


    Surface(tonalElevation = Dimens.surface_elevation_1) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .fillMaxHeight()
                .statusBarsPadding()
        ) {
            SpacerVerticalTwo()
            Text(
                modifier = Modifier.padding(horizontal = Dimens.gird_two),
                text = stringResource(id = R.string.app_name),
                style = typography.titleLarge,
                color = colorScheme.primary
            )
            if (!showNavContent.value)
                NavigationItems(
                    navController=navController,
                    closeDrawer=closeDrawer,
                    userAuthenticated=userAuthenticated,
                    signOut=signOut
                )
            else     if (navController.currentBackStackEntryAsState().value?.destination?.route == RobinDestinations.HOME)
                Column(modifier = Modifier.padding(horizontal = Dimens.gird_two)) {
                    SortUi(
                        showNavContent=showNavContent,
                        brandsUiState = brandsUiState,
                        categoriesUiState = categoriesUiState,
                        closeDrawer=closeDrawer,
                        onApply=onApply
                    )
                }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationItems(
    navController: NavHostController,
    closeDrawer: () -> Unit,
    userAuthenticated: Boolean,
    signOut: () -> Unit
) {
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    val ctx = LocalContext.current
    Column(
        modifier = Modifier.verticalScroll(state = rememberScrollState())
    ) {
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = route == RobinDestinations.HOME,
            icon = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(id = R.string.home))
            },
            onClick = {
                if (route != RobinDestinations.HOME)
                    navController.navigate(RobinDestinations.HOME) {
                        popUpTo(RobinDestinations.HOME) {
                            inclusive = true
                        }
                    }
                closeDrawer()
            }
        )

        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = false,
            onClick = closeDrawer,
            icon = {
                Icon(
                    painterResource(id = R.drawable.shopping_bag),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.your_orders))
            }
        )
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = route == RobinDestinations.CART,
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.shopping_cart
                    ),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.cart))
            },
            onClick = {
                if (route != RobinDestinations.CART)
                    navController.navigate(RobinDestinations.CART)
                closeDrawer()
            }
        )
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            selected = false,
            onClick = closeDrawer,
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.account_circle
                    ),
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(id = R.string.profile))
            }
        )
        AuthUserNavigationItem(
            userAuthenticated,
            signOut = signOut,
            selected = route == RobinDestinations.LOGIN,
            signIn = {
                if (route != RobinDestinations.LOGIN) {
                    navController.navigate(RobinDestinations.LOGIN)
                    closeDrawer()
                }
            }
        )
        DividerHorizontal()
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(
                        id = R.drawable.settings
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Settings") },
            selected = false,
            onClick = closeDrawer,
        )
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(id = R.drawable.support),
                    contentDescription = null
                )
            },
            label = { Text("Help") },
            selected = false,
            onClick = closeDrawer,
        )
        NavigationDrawerItem(
            shape = Shapes.NavigationItemShape,
            modifier = Modifier.padding(end = Dimens.gird_two),
            icon = {
                Icon(
                    painterResource(id = R.drawable.cancel),
                    contentDescription = null
                )
            },
            label = { Text("Exit") },
            selected = false,
            onClick = {
                (ctx as Activity).finishAndRemoveTask()
            },
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthUserNavigationItem(
    userAuthenticated: Boolean,
    signOut: () -> Unit,
    selected: Boolean,
    signIn: () -> Unit,
) {
    val userAction: String
    val icon: Painter
    val onclick: () -> Unit
    if (userAuthenticated) {
        userAction = stringResource(id = R.string.sign_out)
        icon = painterResource(id = R.drawable.signout)
        onclick = signOut
    } else {
        userAction = stringResource(id = R.string.sign_in)
        icon = painterResource(id = R.drawable.signin)
        onclick = signIn
    }
    NavigationDrawerItem(
        shape = Shapes.NavigationItemShape,
        modifier = Modifier.padding(end = Dimens.gird_two),
        icon = {
            Icon(
                painter = icon,
                contentDescription = null
            )
        },
        label = { Text(userAction) },
        selected = selected,
        onClick = onclick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortUi(
    categoriesUiState: SnapshotStateList<FilterChipState>,
    brandsUiState: SnapshotStateList<FilterChipState>,
    showNavContent: MutableState<Boolean>,
    closeDrawer: () -> Unit,
    onApply: () -> Unit
) {
    SpacerVerticalOne()
    Text(
        text = stringResource(id = R.string.filter,),
        style = typography.titleMedium
    )
    SpacerVerticalOne()
   Text(text = "Brands", style = typography.bodyMedium)
    SpacerVerticalOne()
    FlowRow(mainAxisSpacing = Dimens.gird_one) {
        brandsUiState.forEachIndexed {i,state->
            FilterChip(
                selected = state.selected,
                onClick = {brandsUiState[i]= state.copy(selected = !state.selected) },
                label = {
                    Text(text = state.label)
                }
            )

        }
    }
    SpacerVerticalOne()
    Text(text = "Category" ,style = typography.bodyMedium)
    SpacerVerticalOne()
    FlowRow(mainAxisSpacing = Dimens.gird_one) {
                categoriesUiState.forEachIndexed { index, filterChipState ->
                    FilterChip(
                        selected =filterChipState.selected ,
                        onClick = {categoriesUiState[index]=filterChipState.copy(selected = !filterChipState.selected) },
                        label = {
                            Text(text = filterChipState.label)
                        }
                    )

                }
            }
    SpacerVerticalOne()
    SpaceBetweenContainer(modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.gird_two)) {
        FilledTonalButton(onClick = { showNavContent.value=false;closeDrawer() }) {
            Text("Cancel")
        }
        Button(onClick = onApply) {
            Text("Apply")
        }
    }
}

/*
@Preview
@Composable
fun DrawerContentPreview() {
    val ctx = LocalContext.current
    RobinAppPreview {
        DrawerContent(
            userAuthenticated = true,
            navController = NavHostController(ctx),
            signOut = {},
            closeDrawer = {},
            brandsUiState = Response.Success(emptyList()),
            categoriesUiState = Response.Success(emptyList()),
        )
    }
}
*/
