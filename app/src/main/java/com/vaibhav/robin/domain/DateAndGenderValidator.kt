package com.vaibhav.robin.domain

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.entities.ui.state.DropdownState
import com.vaibhav.robin.entities.ui.state.SelectableState

class DateAndGenderValidator(private val postValidate: () -> Unit) {
    fun validate(
        date: MutableState<SelectableState>,
        gender: MutableState<DropdownState>,
        navigate1: Array<String>
    ) {
        date.value=Validators.date(date.value)
        gender.value=Validators.gender(gender.value,navigate1)

        if (!date.value.error && !gender.value.error) postValidate.invoke()
    }
}