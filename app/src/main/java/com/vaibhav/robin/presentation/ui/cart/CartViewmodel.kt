package com.vaibhav.robin.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.timer

@HiltViewModel
class CartViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    val itemIsRemovedState = MutableStateFlow<CartItemRemoveState?>(null)
    private var itemCount = 0


    fun removeCartItem(cartId: String) = viewModelScope.launch {
        databaseUseCases.removeCartItems(cartId).collect {

            when (it) {
                is Response.Error -> itemIsRemovedState.emit(
                    CartItemRemoveState(
                        UiText.StringResource(
                            R.string.remove_cart_message_bar_error
                        ),
                        isError = true,
                    )
                )

                Loading -> {}
                is Response.Success -> {
                    itemCount++
                    itemIsRemovedState.emit(
                        CartItemRemoveState(
                            message = UiText.StringResource(
                                R.string.remove_cart_message_bar_success,
                                arrayOf(itemCount)
                            )
                        )
                    )
                    timer(initialDelay = 3000L, period = 3000L) {
                        itemCount = 0
                        this.cancel()
                    }
                }
            }
        }
    }
}

data class CartItemRemoveState(
    val message: UiText,
    val isError: Boolean = false
)
