package com.vaibhav.robin.presentation.ui.payment

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class PaymentViewModel @Inject constructor(private val useCases: DatabaseUseCases):ViewModel(){
    private val _pan = mutableStateOf(TextFieldState())
    val pan get() = _pan
    private val _expiryDate = mutableStateOf(TextFieldState())
    val expiryDate get()=_expiryDate
    private val _cvv = mutableStateOf(TextFieldState())
    val cvv get() = _cvv

    private val _cardholderName = mutableStateOf(TextFieldState())
    val cardholderName get() = _cardholderName
}