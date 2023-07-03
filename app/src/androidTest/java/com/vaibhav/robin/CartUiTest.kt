package com.vaibhav.robin

import android.content.res.Resources
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.vaibhav.robin.data.PreviewMocks
import com.vaibhav.robin.presentation.models.state.CartUiState
import com.vaibhav.robin.presentation.ui.cart.Cart
import com.vaibhav.robin.presentation.ui.cart.CartTestTags
import com.vaibhav.robin.presentation.ui.common.rememberMessageBarState
import com.vaibhav.robin.presentation.ui.theme.RobinTheme
import org.junit.Rule
import org.junit.Test
import java.util.UUID


class CartUiTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    private val resources: Resources =
        InstrumentationRegistry.getInstrumentation().targetContext.resources
    private val cartItem =
        List(100) { PreviewMocks.cartItem.copy(cartId = UUID.randomUUID().toString()) }



    @Test
    fun test_Ui_State_Empty() {
        composeTestRule.setContent {
            RobinTheme {
                Cart(
                    cartUiState = CartUiState.EmptyCart,
                    messageBarState = rememberMessageBarState(),
                    itemRemoveState = remember { mutableStateOf(null) },
                    onBackNavigation = {assert(true) },
                    onRemoveCartItem = {},
                    onCheckout = {  },
                    onBrowse = { assert(true) }) {

                }
            }
        }
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

}
