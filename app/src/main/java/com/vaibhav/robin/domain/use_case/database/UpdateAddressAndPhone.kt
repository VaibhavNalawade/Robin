package com.vaibhav.robin.domain.use_case.database

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.FirestoreDatabaseRepository
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateAddressAndPhone @Inject constructor(private val firestoreRepository: FirestoreDatabaseRepository) {
    suspend operator fun invoke(
        apartmentSuitesBuilding: MutableState<TextFieldState>,
        streetAddressAndCity: MutableState<TextFieldState>,
        postcode: MutableState<TextFieldState>,
        phone: MutableState<TextFieldState>
    ): Flow<Response<Boolean>> {

        apartmentSuitesBuilding.value = Validators.checkAddress(apartmentSuitesBuilding.value)
        streetAddressAndCity.value = Validators.checkAddress(streetAddressAndCity.value)
        postcode.value = Validators.checkPostCode(postcode.value)
        phone.value = Validators.checkPhone(phone.value)

        if (!streetAddressAndCity.value.error &&
            !apartmentSuitesBuilding.value.error &&
            !postcode.value.error &&
            !phone.value.error
        ) {

            val hashmap=hashMapOf<String, Any>(
                "phone" to phone.value.text,
                "address" to hashMapOf<String, Any>(
                    "streetAddressAndCity" to streetAddressAndCity.value.text,
                    "apartmentSuitesBuilding" to apartmentSuitesBuilding.value.text,
                    "postcode" to postcode.value.text,
                )
            )
            return firestoreRepository.updateProfile(hashmap)
        } else return flow { emit(Response.Error("Predate Failed")) }
    }
}
