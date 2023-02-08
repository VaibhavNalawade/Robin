package com.vaibhav.robin.presentation.ui.delivery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaceOrderViewModel @Inject constructor(private val useCases: DatabaseUseCases) :
    ViewModel() {
var respose by mutableStateOf<Response<List<Map<String,Any>>>>(Response.Loading)
    var selectedAddressId by mutableStateOf<String?>(null)

    fun getAddresses() = viewModelScope.launch {
        useCases.getAddress().collect {
            respose=it
        }
    }
}
