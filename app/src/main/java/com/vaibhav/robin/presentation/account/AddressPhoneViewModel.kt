package com.vaibhav.robin.presentation.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.entities.ui.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressPhoneViewModel @Inject constructor( private val databaseUseCases: DatabaseUseCases):ViewModel() {
    private val _streetAddressAndCity= mutableStateOf(TextFieldState())
    val streetAddressAndCity get() = _streetAddressAndCity

    private val _apartmentSuitesBuilding= mutableStateOf(TextFieldState())
    val apartmentSuitesBuilding get() = _apartmentSuitesBuilding

    private val _postcode= mutableStateOf(TextFieldState())
    val postcode get() = _postcode

    private val _phone= mutableStateOf(TextFieldState())
    val phone get() = _phone

    var response by mutableStateOf<Response<Boolean>>(Response.Success(false))
    private set

    fun updateAddressAndPhone()=viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.updateAddressAndPhone(_apartmentSuitesBuilding,_streetAddressAndCity,_postcode,_phone).collect{
         response=it
        }
    }

    fun retry() {
        response=Success(false)
    }

}