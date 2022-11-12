package com.vaibhav.robin.domain

import androidx.core.util.PatternsCompat
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.models.state.DropdownState
import com.vaibhav.robin.presentation.models.state.SelectableState
import com.vaibhav.robin.presentation.models.state.TextFieldState
import com.vaibhav.robin.presentation.UiText

/*
* ToDo Need to implement pin-code and phone number validation*/
object Validators {
    private val PHONE_REGEX =
        "((\\+*)((0[ -]*)*|((91 )*))((\\d{12})+|(\\d{10})+))|\\d{5}([- ]*)\\d{6}".toRegex()
    private val POSTCODE_REGEX = "^[1-9][0-9]{5}\$".toRegex()
    fun email(email: TextFieldState): TextFieldState {
        if (email.text.isBlank())
            return email.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.email_is_empty)
            )
        if (!email.text.matches(PatternsCompat.EMAIL_ADDRESS.pattern().toRegex()))
            return email.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.email_is_invalid)
            )
        return email.copy(error = false, errorMessage = null)
    }

    fun password(password: TextFieldState): TextFieldState {
        if (password.text.isBlank())
            return password.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_empty)
            )
        if (password.text.length <= 5)
            return password.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_length)
            )
        return password.copy(error = false, errorMessage = null)
    }

    fun confirmPassword(confirmPassword: TextFieldState, password: TextFieldState): TextFieldState {
        if (confirmPassword.text.isBlank())
            return password.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_empty)
            )
        if (confirmPassword.text != password.text)
            return confirmPassword.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_not_match)
            )
        return confirmPassword.copy(error = false, errorMessage = null)
    }

    fun personalDetails(textFieldState: TextFieldState): TextFieldState {
        if (textFieldState.text.isBlank())
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.field_is_empty)
            )
        return textFieldState.copy(error = false, errorMessage = null)
    }

    fun date(selectableState: SelectableState): SelectableState {
        if (selectableState.text.isBlank())
            return selectableState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.select_a_date)
            )
        return selectableState.copy(error = false, errorMessage = null)
    }

    fun gender(dropdownState: DropdownState, array: Array<String>): DropdownState {
        if (dropdownState.text == array[0])
            return dropdownState.copy(
                error = true,
                errorMessage = UiText.DynamicString(array[0])
            )
        return dropdownState.copy(error = false, errorMessage = null)
    }

    fun checkAddress(textFieldState: TextFieldState): TextFieldState {
        if (textFieldState.text.isBlank())
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.address_empty)
            )
        return textFieldState.copy(error = false, errorMessage = null)
    }

    fun checkPostCode(textFieldState: TextFieldState): TextFieldState {
        if (textFieldState.text.isBlank())
            return textFieldState.copy(error = true, errorMessage = UiText.StringResource(R.string.postcode_empty)
        )
        if (!textFieldState.text.matches(POSTCODE_REGEX))
            return textFieldState.copy(
            error = true,
            errorMessage = UiText.StringResource(R.string.postcode_Invalid)
        )
        return textFieldState.copy(error = false, errorMessage = null)
    }

    fun checkPhone(textFieldState: TextFieldState): TextFieldState {
        if (textFieldState.text.isBlank())
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_empty)
            )
        if (!textFieldState.text.matches(PHONE_REGEX))
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.phone_invalid)
            )
        return textFieldState.copy(error = false, errorMessage = null)
    }

    fun checkReview(textFieldState: TextFieldState): TextFieldState {
        if (textFieldState.text.isBlank())
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.password_empty)
            )
        if (textFieldState.text.length>500){
            return textFieldState.copy(
                error = true,
                errorMessage = UiText.StringResource(R.string.phone_invalid)
            )
        }
        return textFieldState.copy(error = false, errorMessage = null)
    }
}



