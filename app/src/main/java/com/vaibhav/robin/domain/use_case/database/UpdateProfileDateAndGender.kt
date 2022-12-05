package com.vaibhav.robin.domain.use_case.database

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.DatabaseRepository
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.models.state.SelectableState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateProfileDateAndGender @Inject constructor(private val firestoreRepository: DatabaseRepository) {
    suspend operator fun invoke(
        date: MutableState<SelectableState>,
        gender: MutableState<DropdownState>,
        listOptions: Array<String>
    ): Flow<Response<Boolean>> {
        date.value = Validators.date(date.value)
        gender.value = Validators.gender(gender.value, listOptions)
        if (!date.value.error && !gender.value.error) {
            val hashmap = hashMapOf<String,Any>(
                "BirthDate" to date.value.text,
                "Gender" to gender.value.text
            )
            return firestoreRepository.updateProfile(hashmap)
        }
        else  return flow { emit(Response.Error(ValidationFailedException())) }
    }
}
