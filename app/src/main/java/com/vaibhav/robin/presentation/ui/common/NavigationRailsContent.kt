package com.vaibhav.robin.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.ui.navigation.RobinDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRailsContent(
    userAuthenticated: Boolean,
    navController: NavHostController,
    signOut: () -> Unit,
    cartItemSize: Int,
    orderItemsSize: Int
) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        val route = navController.currentBackStackEntryAsState().value?.destination?.route
        NavigationRailItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.home),
                    contentDescription = null
                )
            },
            label = { Text(stringResource(id = R.string.home)) },
            selected = route == RobinDestinations.HOME,
            onClick = {
                if (route != RobinDestinations.HOME)
                    navController.navigate(RobinDestinations.HOME) {
                        popUpTo(RobinDestinations.HOME) {
                            inclusive = true
                        }
                    }
            }
        )
SpacerVerticalOne()
        NavigationRailItem(
            icon = {
                BadgedBox(
                    badge = {
                        Badge {
                            Text(text = orderItemsSize.toString())
                        }
                    },
                    content = {
                        Icon(
                            painterResource(id = R.drawable.shopping_bag),
                            contentDescription = null
                        )
                    }
                )
            },
            label = { Text(stringResource(id = R.string.your_orders)) },
            selected = route == RobinDestinations.MANAGE_ORDERS,
            onClick = {
                if (route != RobinDestinations.MANAGE_ORDERS)
                    navController.navigate(RobinDestinations.MANAGE_ORDERS)
            }
        )
        SpacerVerticalOne()

        NavigationRailItem(
            icon = {
                BadgedBox(
                    badge = {
                        Badge {
                            Text(text = cartItemSize.toString())
                        }
                    },
                    content = {
                        Icon(
                            painterResource(id = R.drawable.shopping_cart),
                            contentDescription = null
                        )
                    }
                )
            },
            label = { Text(stringResource(id = R.string.cart)) },
            selected = route == RobinDestinations.CART,
            onClick = {
                if (route != RobinDestinations.CART)
                    navController.navigate(RobinDestinations.CART)
            }
        )
        SpacerVerticalOne()

        NavigationRailItem(
            icon = {
                Icon(
                    painter = painterResource(
                        id = R.drawable.account_circle
                    ),
                    contentDescription = null
                )
            },
            label = { Text("Account") },
            selected = false,
            onClick = {},
        )
        SpacerVerticalOne()

        NavigationRailItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.settings),
                    contentDescription = null
                )
            },
            label = { Text("Settings") },
            selected = false,
            onClick = {},
        )
        SpacerVerticalOne()

        NavigationRailItem(
            icon = {
                Icon(
                    painterResource(id = R.drawable.support),
                    contentDescription = null
                )
            },
            label = { Text("Help") },
            selected = false,
            onClick = {}
        )
        SpacerVerticalOne()

        AuthUserNavigationRailsItem(
            userAuthenticated = userAuthenticated,
            signOut = signOut,
            selected = route == RobinDestinations.LOGIN,
            signIn = {
                if (route != RobinDestinations.LOGIN)
                    navController.navigate(RobinDestinations.LOGIN)
            }
        )
    }
}


    @Composable
    fun AuthUserNavigationRailsItem(
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
        NavigationRailItem(
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
