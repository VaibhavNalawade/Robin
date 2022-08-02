package com.vaibhav.robin.ui.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.auth.AuthState
import com.vaibhav.robin.domain.AddressAndPhoneValidation
import com.vaibhav.robin.domain.DateAndGenderValidator
import com.vaibhav.robin.domain.PersonalDetailsValidators
import com.vaibhav.robin.domain.SignUpValidation
import com.vaibhav.robin.entities.ui.state.DropdownState
import com.vaibhav.robin.entities.ui.state.SelectableState
import com.vaibhav.robin.entities.ui.state.TextFieldState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AccountSharedViewModel : ViewModel() {

    private val _signUpEmail = mutableStateOf(TextFieldState())
    val signUpEmail get() = _signUpEmail

    private val _signUpPassword = mutableStateOf(TextFieldState())
    val signUpPassword get() = _signUpPassword

    private val _signUpConfirmPassword = mutableStateOf(TextFieldState())
    val signUpConfirmPassword get() = _signUpConfirmPassword

    private val _authState = MutableStateFlow<AuthState>(AuthState.Init)
    val authState get() = _authState

     fun predateSignUp(postValidation: () -> Unit) {
         if(authState.value is AuthState.Init)
         viewModelScope.launch(Dispatchers.Default) {
             authState.value=AuthState.Loading()
             SignUpValidation(postValidation).validate(
                 _signUpEmail,
                 _signUpPassword,
                 _signUpConfirmPassword,
                 _authState
             )
         }
    }

    private val _firstName = mutableStateOf(TextFieldState())
    val firstName get() = _firstName

    private val _lastName = mutableStateOf(TextFieldState())
    val lastName get() = _lastName

    fun predatePersonalDetails(postValidation:()-> Unit) {
        PersonalDetailsValidators(postValidation).validate(_firstName,_lastName)
    }
    private val _date = mutableStateOf(SelectableState())
    val date get() = _date

    private val _gender = mutableStateOf(DropdownState())
    val gender get() = _gender
    
    fun predateDateGender(optionList: Array<String>, postValidation: () -> Unit) {
        DateAndGenderValidator(postValidation).validate(_date,_gender,optionList)
    }

    private val _streetAddressAndCity= mutableStateOf(TextFieldState())
    val streetAddressAndCity get() = _streetAddressAndCity

    private val _apartmentSuitesBuilding= mutableStateOf(TextFieldState())
    val apartmentSuitesBuilding get() = _apartmentSuitesBuilding

    private val _postcode= mutableStateOf(TextFieldState())
    val postcode get() = _postcode

    private val _phone= mutableStateOf(TextFieldState())
    val phone get() = _phone

    fun predateAddressAndPhone(postValidation: () -> Boolean) {
        AddressAndPhoneValidation(postValidation)
            .validate(_streetAddressAndCity,_apartmentSuitesBuilding,_postcode,_phone)

    }
}
