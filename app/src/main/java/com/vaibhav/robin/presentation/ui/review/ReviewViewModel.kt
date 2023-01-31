package com.vaibhav.robin.presentation.ui.review

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.*
import com.vaibhav.robin.domain.use_case.auth.AuthUseCases
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.models.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val database: DatabaseUseCases,
    private val auth: AuthUseCases
) : ViewModel() {
    var userName by mutableStateOf(String())
        private set
    var userPhoto: Uri by mutableStateOf(Uri.EMPTY)
        private set
    var comment = mutableStateOf(TextFieldState())

    var stars by mutableStateOf(0)

    var productID by mutableStateOf(String())

    var response by mutableStateOf<Response<Boolean>>(Success(false))
    private set


    init {
        viewModelScope.launch {
            auth.getAuthState().collect{
                val profile=auth.getProfileData()
                userName=profile?.name?:"Error"
                userPhoto=profile?.image?: Uri.EMPTY
            }
        }
    }

    fun createReview(product: Product)=viewModelScope.launch(Dispatchers.IO) {
        database.writeReview(userName,userPhoto,comment,stars,product.id).collect{
            response=it
        }
    }

    fun retry(product: Product) {
        createReview(product)
    }
    fun getAuthState() =auth.isUserAuthenticated()

}