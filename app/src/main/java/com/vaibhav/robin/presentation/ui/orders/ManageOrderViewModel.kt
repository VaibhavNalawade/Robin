package com.vaibhav.robin.presentation.ui.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class ManageOrderViewModel@Inject constructor(
    private val useCases: DatabaseUseCases,
    private val authUseCases: AuthUseCases,
):ViewModel(){
    private val _selectedOrderItemIndex = mutableStateOf<Int>(0)
    val selectedOrderItemIndex get() = _selectedOrderItemIndex
    fun getAuthState() = authUseCases.isUserAuthenticated()

}