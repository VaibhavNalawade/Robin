package com.vaibhav.robin.domain.use_case.database

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.DatabaseRepository
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class AddAddress @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(
        addressFullName: MutableState<TextFieldState>,
        apartmentAddress: MutableState<TextFieldState>,
        streetAddress: MutableState<TextFieldState>,
        city: MutableState<TextFieldState>,
        state: MutableState<TextFieldState>,
        pinCode: MutableState<TextFieldState>,
        phoneNumber: MutableState<TextFieldState>
    ): Flow<Response<Boolean>> {
        streetAddress.value = Validators.checkAddress(streetAddress.value)
        city.value = Validators.checkAddress(city.value)
        state.value = Validators.checkAddress(state.value)
        pinCode.value = Validators.checkPostCode(pinCode.value)
        phoneNumber.value = Validators.checkPhone(phoneNumber.value)
        addressFullName.value = Validators.personalDetails(addressFullName.value)
        apartmentAddress.value = Validators.checkAddress(apartmentAddress.value)
        return if (
            !addressFullName.value.error &&
            !streetAddress.value.error &&
            !city.value.error &&
            !pinCode.value.error &&
            !phoneNumber.value.error &&
            !state.value.error &&
            !apartmentAddress.value.error
        ) {

            repository.addAddress(
                Address(
                    UUID.randomUUID().toString(),
                    addressFullName.value.text,
                    streetAddress.value.text,
                    apartmentAddress.value.text,
                    city.value.text,
                    state.value.text,
                    pinCode.value.text,
                    phoneNumber.value.text
                )
            )
        } else flow { Response.Error(ValidationFailedException()) }
    }
}
