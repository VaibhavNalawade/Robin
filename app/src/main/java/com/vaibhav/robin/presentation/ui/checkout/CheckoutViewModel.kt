package com.vaibhav.robin.presentation.ui.checkout

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.GenrateSingleLineAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val useCases: DatabaseUseCases) : ViewModel() {
    var addressResponse by mutableStateOf<Response<List<Address>>>(Loading)
        private set
    var paymentsResponse by mutableStateOf<Response<List<PaymentData>>>(Loading)
        private set
    var orderResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    val selectedAddressId = mutableStateOf<String?>(null)
    val selectedPaymentId = mutableStateOf<String?>(null)
    fun loadAddresses() = viewModelScope.launch(Dispatchers.IO) {
        useCases.getAddress().collect {
            addressResponse = it
        }
    }

    fun loadPayments() = viewModelScope.launch(Dispatchers.IO) {
        useCases.getPayments().collect {
            paymentsResponse = it
        }
    }

    fun placeOrder(
        cartItem: List<CartItem>,
    ) = viewModelScope.launch(Dispatchers.IO) {
        orderResponse = Loading
        val address = (addressResponse as? Success)?.data?.find { address ->
            address.id == selectedAddressId.value

        } ?: throw NullPointerException()
        val payment: PaymentData = (paymentsResponse as? Success)?.data?.find { payment ->
            payment.id == selectedPaymentId.value
        } ?: throw NullPointerException()
        useCases.placeOrder(
            OrderItem(
                cartItem,
                shippingAddress = GenrateSingleLineAddress(address),
                id = UUID.randomUUID().toString(),
                paymentId = UUID.randomUUID().toString(),
                paymentMethod = "Card ending ${payment.prn.takeLast(4)}"
            ).apply {
                expectedDeliveryDate = Calendar.getInstance().let {
                    it.add(Calendar.DATE, 7)
                    it.time
                }
            }
        ).collect{
            when(it){
                is Error -> Log.e("T",it.exception.message?:it.exception.stackTraceToString())
                Loading -> Log.e("T","Loding")
                is Success -> {
                    useCases.clearCartItem().collect{
                        orderResponse = it
                    }
                }
            }
        }
    }
}

