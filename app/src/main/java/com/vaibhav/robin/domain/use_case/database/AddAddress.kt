package com.vaibhav.robin.domain.use_case.database

import androidx.compose.runtime.MutableState
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
        streetAddressAndCity: MutableState<TextFieldState>,
        apartmentSuitesBuilding: MutableState<TextFieldState>,
        postcode: MutableState<TextFieldState>,
        phone: MutableState<TextFieldState>,
        isHome: Boolean,
        name: MutableState<TextFieldState>
    ): Flow<Response<Boolean>> {
        streetAddressAndCity.value = Validators.checkAddress(streetAddressAndCity.value)
        apartmentSuitesBuilding.value = Validators.checkAddress(apartmentSuitesBuilding.value)
        postcode.value = Validators.checkPostCode(postcode.value)
        phone.value = Validators.checkPhone(phone.value)
        name.value=Validators.personalDetails(name.value)
        if (
            !name.value.error &&
            !streetAddressAndCity.value.error &&
            !apartmentSuitesBuilding.value.error &&
            !postcode.value.error &&
            !phone.value.error
        ) {
            val data = mapOf(
                "id" to UUID.randomUUID().toString(),
                "name" to name.value.text,
                "address" to apartmentSuitesBuilding.value.text + streetAddressAndCity.value.text +"\n"+ postcode.value.text,
                "phone" to phone.value.text,
                "isHome" to isHome
            )
            return repository.addAddress(data)
        } else return flow { Response.Error(ValidationFailedException()) }
    }
}
