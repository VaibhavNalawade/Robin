package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.RobinAppPreview
import com.vaibhav.robin.presentation.navigation.RobinDestinations
import com.vaibhav.robin.presentation.ui.theme.Values.Dimens
import com.vaibhav.robin.presentation.ui.theme.Values.Shapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(
    userAuthenticated: Boolean,
    navController: NavHostController,
    signOut: () -> Unit,
    closeDrawer: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val route = navController.currentBackStackEntryAsState().value?.destination?.route
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .width(320.dp)
    ) {
        SpacerVerticalTwo()
        Text(
            modifier = Modifier.padding(horizontal = Dimens.gird_two),
            text = stringResource(id = R.string.app_name),
            style = typography.titleLarge,
            color = colorScheme.primary
        )
        SpacerVerticalTwo()
        Column(
            modifier = Modifier.verticalScroll(
                state = scrollState
            )
        ) {
            NavigationDrawerItem(
                shape = Shapes.NavigationItemShape,
                modifier = Modifier.padding(end = Dimens.gird_two),
                icon = {
                    Icon(
                        painterResource(id = R.drawable.home_fill0_wght700_grad0_opsz24),
                        contentDescription = null
                    )
                },
                label = { Text("Home") },
                selected = route == RobinDestinations.HOME,
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
                icon = {
                    Icon(
                        painterResource(id = R.drawable.shopping_bag_fill0_wght700_grad0_opsz24),
                        contentDescription = null
                    )
                },
                label = { Text("Your Orders") },
                selected = false,
                onClick = {

                    closeDrawer()

                }
            )


            NavigationDrawerItem(
                shape = Shapes.NavigationItemShape,
                modifier = Modifier.padding(end = Dimens.gird_two),
                icon = {
                    Icon(
                        painterResource(
                            id = R.drawable.shopping_cart_fill0_wght700_grad0_opsz24
                        ),
                        contentDescription = null
                    )
                },
                label = { Text("Cart") },
                selected = route == RobinDestinations.CART,
                onClick = {
                    if (route != RobinDestinations.CART)
                        navController.navigate(RobinDestinations.CART)
                    closeDrawer()
                }
            )

            NavigationDrawerItem(
                shape = Shapes.NavigationItemShape,
                modifier = Modifier.padding(end = Dimens.gird_two),
                icon = {
                    Icon(
                        painterResource(
                            id = R.drawable.supervised_user_circle_fill0_wght700_grad0_opsz24
                        ),
                        contentDescription = null
                    )
                },
                label = { Text("Profile") },
                selected = false,
                onClick = {
                    closeDrawer()
                },

                )

            NavigationDrawerItem(
                shape = Shapes.NavigationItemShape,
                modifier = Modifier.padding(end = Dimens.gird_two),
                icon = {
                    Icon(
                        painterResource(
                            id = R.drawable.settings_fill0_wght700_grad0_opsz24
                        ),
                        contentDescription = null
                    )
                },
                label = { Text("Settings") },
                selected = false,
                onClick = {
                    closeDrawer()
                },

                )

            NavigationDrawerItem(
                shape = Shapes.NavigationItemShape,
                modifier = Modifier.padding(end = Dimens.gird_two),
                icon = {
                    Icon(
                        painterResource(
                            id = R.drawable.support_fill0_wght700_grad0_opsz24
                        ),
                        contentDescription = null
                    )
                },
                label = { Text("Help") },
                selected = false,
                onClick = {
                    closeDrawer()
                },

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
        }
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
        icon = painterResource(id = R.drawable.logout_fill0_wght700_grad0_opsz24)
        onclick = signOut
    } else {
        userAction = stringResource(id = R.string.sign_in)
        icon = painterResource(id = R.drawable.login_fill0_wght700_grad0_opsz24)
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

@Preview
@Composable
fun DrawerContentPreview() {

    val ctx = LocalContext.current
    RobinAppPreview {
        DrawerContent(
            userAuthenticated = true,
            navController = NavHostController(ctx),
            signOut = {}
        ) {}
    }
}
