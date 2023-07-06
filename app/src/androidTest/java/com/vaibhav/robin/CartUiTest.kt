package com.vaibhav.robin

import android.content.res.Resources
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseNetworkException
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.presentation.RobinTestTags
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.ui.cart.Cart
import com.vaibhav.robin.presentation.ui.cart.CartTestTags
import com.vaibhav.robin.presentation.ui.common.rememberMessageBarState
import com.vaibhav.robin.presentation.ui.theme.RobinTheme
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test


class CartUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources


    @Test
    fun test_Ui_State_Empty() {
        composeTestRule.setContent {
            RobinTheme {
                Cart(
                    cartUiState = CartUiState.EmptyCart,
                    messageBarState = rememberMessageBarState(),
                    itemRemoveState = remember { mutableStateOf(null) },
                    onBackNavigation = { assert(true) },
                    onRemoveCartItem = {},
                    onCheckout = { },
                    onBrowse = { assert(true) },
                    retry = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag(CartTestTags.EMPTY_CART_ANIMATED_ILLUSTRATE)
            .assertExists()
        composeTestRule
            .onNodeWithTag(CartTestTags.BACK_BUTTON)
            .assertExists()
            .performClick()
        composeTestRule
            .onNodeWithText(resources.getString(R.string.empty_cart_msg))
            .assertExists()
        composeTestRule
            .onNodeWithText(resources.getString(R.string.start_shopping))
            .assertExists()
            .performClick()
    }

    @Test
    fun test_Ui_state_Loading() {
        composeTestRule.setContent {
            RobinTheme {
                Cart(
                    cartUiState = CartUiState.Loading,
                    messageBarState = rememberMessageBarState(),
                    itemRemoveState = remember { mutableStateOf(null) },
                    onBackNavigation = { assert(true) },
                    onRemoveCartItem = {},
                    onCheckout = { },
                    onBrowse = { assert(true) },
                    retry = {}
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule
            .onNodeWithTag(RobinTestTags.LOADING)
            .assertExists()
        composeTestRule
            .onNodeWithTag(CartTestTags.BACK_BUTTON)
            .assertExists()
            .performClick()
    }

    @Test
    fun test_Ui_state_Success() {

        val mutableCartUiState = MutableStateFlow(CartUiState.Success(PreviewMocks.cartItem))
        composeTestRule.setContent {
            fun removeCartItem(id: String) {
                val updatedState = mutableCartUiState.value.cartItems.filter { it.cartId == id }
                mutableCartUiState.value = CartUiState.Success(updatedState)
            }
            RobinTheme {
                Cart(
                    cartUiState = mutableCartUiState.collectAsState().value,
                    messageBarState = rememberMessageBarState(),
                    itemRemoveState = remember { mutableStateOf(null) },
                    onBackNavigation = { assert(true) },
                    onRemoveCartItem = { removeCartItem(it) },
                    onCheckout = { assert(true) },
                    onBrowse = { assert(false) },
                    retry = { assert(false) }
                )
            }
        }
        composeTestRule.waitForIdle()

        // Test Check items count showing right count
        composeTestRule
            .onNodeWithText(
                resources.getString(
                    R.string.items_in_your_cart,
                    mutableCartUiState.value.cartItems.size
                )
            )
            .assertExists()
        // Test Check Remove Item Works
        composeTestRule
            .onAllNodesWithTag(CartTestTags.REMOVE_BUTTON)
            .onFirst()
            .assertExists()
            .performClick()
        // Test Check Remove Item Updated Count
        composeTestRule
            .onNodeWithText(
                resources.getString(
                    R.string.items_in_your_cart,
                    mutableCartUiState.value.cartItems.size
                )
            ).assertExists()
        //Test OrderSummary
        composeTestRule.onNodeWithTag(RobinTestTags.SUMMARY).assertExists()
        composeTestRule.onNodeWithTag(RobinTestTags.SUMMARY_CHECKOUT_BUTTON)
            .assertExists()
            .performClick()
        //Test Back Button
        composeTestRule
            .onNodeWithTag(CartTestTags.BACK_BUTTON)
            .assertExists()
            .performClick()
    }

    @Test
    fun test_ui_state_errorCart() {
        composeTestRule.setContent {
            RobinTheme {
                Cart(
                    cartUiState = CartUiState.Error(FirebaseNetworkException(DUMMY_ERROR_MSG)),
                    messageBarState = rememberMessageBarState(),
                    itemRemoveState = remember { mutableStateOf(null) },
                    onBackNavigation = { assert(true) },
                    onRemoveCartItem = { assert(false) },
                    onCheckout = { assert(false) },
                    onBrowse = { assert(false) },
                    retry = { assert(true) }
                )
            }
        }
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(RobinTestTags.ERROR_MESSAGE).assertExists()
        composeTestRule.onNodeWithTag(RobinTestTags.ERROR_RESOLVE_BUTTON).assertExists()
            .performClick()
        composeTestRule.onNodeWithTag(RobinTestTags.ERROR_TITLE).assertExists()
        composeTestRule.onNodeWithTag(RobinTestTags.ERROR_VISUALS).assertExists()
        composeTestRule.onNodeWithTag(CartTestTags.BACK_BUTTON).assertExists()
    }
    companion object{
        const val DUMMY_ERROR_MSG="Unable to connect to server"
    }
}
