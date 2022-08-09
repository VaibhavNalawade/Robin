package com.vaibhav.robin.presentation.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.DateAndGenderValidator
import com.vaibhav.robin.entities.ui.state.DropdownState
import com.vaibhav.robin.entities.ui.state.SelectableState
import dagger.hilt.android.lifecycle.HiltViewModel


class DateAndGenderViewModel:ViewModel() {
    private val _date = mutableStateOf(SelectableState())
    val date get() = _date

    private val _gender = mutableStateOf(DropdownState())
    val gender get() = _gender

    fun predateDateGender(optionList: Array<String>, postValidation: () -> Unit) {
        DateAndGenderValidator(postValidation).validate(_date,_gender,optionList)
    }
}