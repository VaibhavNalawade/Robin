package com.vaibhav.robin.presentation.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.AddressAndPhoneValidation
import com.vaibhav.robin.entities.ui.state.TextFieldState

class AddressPhoneViewModel:ViewModel() {
    private val _streetAddressAndCity= mutableStateOf(TextFieldState())
    val streetAddressAndCity get() = _streetAddressAndCity

    private val _apartmentSuitesBuilding= mutableStateOf(TextFieldState())
    val apartmentSuitesBuilding get() = _apartmentSuitesBuilding

    private val _postcode= mutableStateOf(TextFieldState())
    val postcode get() = _postcode

    private val _phone= mutableStateOf(TextFieldState())
    val phone get() = _phone

    fun predateAddressAndPhone(postValidation: () -> Boolean) {
        AddressAndPhoneValidation(postValidation)
            .validate(_streetAddressAndCity,_apartmentSuitesBuilding,_postcode,_phone)

    }
}