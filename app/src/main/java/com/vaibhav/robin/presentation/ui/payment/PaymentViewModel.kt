package com.vaibhav.robin.presentation.ui.payment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val useCases: DatabaseUseCases) : ViewModel() {
    private val _pan = mutableStateOf(
        TextFieldState(
            "${arrayOf(20, 40, 50, 60, 65).random()}" +
                    "${(10000000000000..99999999999999).random()}"
        )
    )
    val pan get() = _pan

    private val _expiryDate = mutableStateOf(TextFieldState("12/50"))
    val expiryDate get() = _expiryDate

    private val _cvv = mutableStateOf(TextFieldState("111"))
    val cvv get() = _cvv

    private val _cardholderName = mutableStateOf(TextFieldState("John Muir"))
    val cardholderName get() = _cardholderName

    var responseAdd by mutableStateOf<Response<Boolean>>(Response.Loading)
        private set

    var paymentsResponse by mutableStateOf<Response<List<PaymentData>>>(Response.Loading)
        private set

    fun addPayment(function: () -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        useCases.addPayment(
            pan = _pan,
            expiryDate = _expiryDate,
            cvv = _cvv,
            cardholderName = _cardholderName
        ).catch {
            Log.e(TAG, it.stackTraceToString())
            responseAdd = Response.Error(it as Exception)
        }.collect {
            responseAdd = it
            if (it is Response.Success)
                function.invoke()
        }
    }

    fun loadPayments() = viewModelScope.launch(Dispatchers.IO) {
        useCases.getPayments().catch {
            Log.e(TAG, it.stackTraceToString())
            paymentsResponse = Response.Error(it as Exception)
        }.collect {
            paymentsResponse = it
        }
    }

    fun removePaymentMethod(id: String) =viewModelScope.launch (Dispatchers.IO){
        useCases.removePayment(id).collect{

        }
    }

    companion object {
        private const val TAG = "PaymentViewModel"
    }
}