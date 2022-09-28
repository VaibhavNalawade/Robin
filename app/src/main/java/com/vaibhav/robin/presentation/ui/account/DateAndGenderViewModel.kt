package com.vaibhav.robin.presentation.ui.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Success
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.models.state.SelectableState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DateAndGenderViewModel @Inject constructor(private val databaseUseCases: DatabaseUseCases) :
    ViewModel() {
    private val _date = mutableStateOf(SelectableState())
    val date get() = _date

    private val _gender = mutableStateOf(DropdownState())
    val gender get() = _gender

    var response by mutableStateOf<Response<Boolean>>(Success(false))
    private set
    fun updateDateAndGender(listOptions:Array<String>) =viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.updateProfileDateAndGender(_date, _gender, listOptions).collect{
            response=it
        }
    }

    fun retry() {
        response=Success(false)
    }
}