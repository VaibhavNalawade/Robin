package com.vaibhav.robin.domain

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.entities.ui.state.TextFieldState

class AddressAndPhoneValidation(private val postValidate: () -> Boolean) {

    fun validate(
        streetAddressAndCity: MutableState<TextFieldState>,
        apartmentSuitesBuilding: MutableState<TextFieldState>,
        postCode: MutableState<TextFieldState>,
        phone: MutableState<TextFieldState>
    ) {
        streetAddressAndCity.value = Validators.checkAddress(streetAddressAndCity.value)
        apartmentSuitesBuilding.value = Validators.checkAddress(apartmentSuitesBuilding.value)
        postCode.value = Validators.checkPostCode(postCode.value)
        phone.value = Validators.checkPhone(phone.value)

        if (
            !streetAddressAndCity.value.error &&
            !apartmentSuitesBuilding.value.error &&
            !postCode.value.error &&
            !phone.value.error
        )
            postValidate.invoke()
    }
}
