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
import com.vaibhav.robin.presentation.generateSingleLineAddress
import com.vaibhav.robin.presentation.calculateSummary
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(private val useCases: DatabaseUseCases) : ViewModel() {

    private val _pan = mutableStateOf(TextFieldState())
    val pan get() = _pan
    private val _expiryDate = mutableStateOf(TextFieldState())
    val expiryDate get() = _expiryDate
    private val _cvv = mutableStateOf(TextFieldState())
    val cvv get() = _cvv
    private val _cardholderName = mutableStateOf(TextFieldState())
    val cardholderName get() = _cardholderName
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
                shippingAddress = generateSingleLineAddress(address),
                id = UUID.randomUUID().toString(),
                paymentId = UUID.randomUUID().toString(),
                paymentMethod = "${payment.pan.take(4)}${"#".repeat(8)}${payment.pan.takeLast(4)}",
                totalPrice = calculateSummary(cartItem).total
            ).apply {
                expectedDeliveryDate = Calendar.getInstance().let {
                    it.add(Calendar.DATE, 7)
                    it.time
                }
            }
        ).collect {
            when (it) {
                is Error -> Log.e("T", it.exception.message ?: it.exception.stackTraceToString())
                Loading -> Log.e("T", "Loding")
                is Success -> {
                    useCases.clearCartItem().collect {
                        orderResponse = it
                    }
                }
            }
        }
    }
var addPaymentResponse by mutableStateOf<Response<Boolean>>(Loading)
    private set
    fun addPaymentMethod() = viewModelScope.launch(Dispatchers.IO) {
        useCases.addPayment(
            _pan,
            _expiryDate,
            _cvv,
            _cardholderName
        ).collect {
            addPaymentResponse=it
        }
    }

    private val _addressFullName = mutableStateOf(TextFieldState())
    val addressFullName get() = _addressFullName
    private val _streetAddress = mutableStateOf(TextFieldState())
    val streetAddress get() = _streetAddress
    private val _city = mutableStateOf(TextFieldState())
    val city get() = _city
    private val _apartmentAddress = mutableStateOf(TextFieldState())
    val apartmentAddress get() = _apartmentAddress
    private val _state = mutableStateOf(TextFieldState())
    val state get() = _state
    private val _pinCode = mutableStateOf(TextFieldState())
    val pinCode get() = _pinCode
    private val _phoneNumber = mutableStateOf(TextFieldState())
    val phoneNumber get() = _phoneNumber
    var addAddressResponse by mutableStateOf<Response<Boolean>>(Loading)
        private set
    fun addAddress()=viewModelScope.launch {
        useCases.addAddress(
            _addressFullName,
            _apartmentAddress,
            _streetAddress,
            _city,
            _state,
            _pinCode,
            _phoneNumber
        ).collect{
            addAddressResponse=it
        }
    }

}

