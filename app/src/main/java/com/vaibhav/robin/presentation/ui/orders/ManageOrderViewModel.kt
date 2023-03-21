package com.vaibhav.robin.presentation.ui.orders

import androidx.lifecycle.ViewModel
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import javax.inject.Inject

class ManageOrderViewModel@Inject constructor(private val useCases: DatabaseUseCases):ViewModel(){
}