package com.vaibhav.unit

import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.entities.ui.state.DropdownState
import com.vaibhav.robin.entities.ui.state.SelectableState
import com.vaibhav.robin.entities.ui.state.TextFieldState
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ValidatorsUnitTest {
    @Test
    fun email_blank() {
        Validators.email(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun email_invalid() {
        Validators.email(TextFieldState("vn14989@.com")).apply {
            assert(error)
        }
    }

    @Test
    fun email_valid() {
        Validators.email(TextFieldState("abc@g.com")).apply {
            assert(!error)
        }
    }

    @Test
    fun password_blank() {
        Validators.password(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun password_less_than_six_digit() {
        Validators.password(TextFieldState("12345")).apply {
            assert(error)
        }
    }

    @Test
    fun password_valid() {
        Validators.password(TextFieldState("123456")).apply {
            assert(!error)
        }
    }

    @Test
    fun confirm_password_blank() {
        Validators.confirmPassword(TextFieldState(""), TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun confirm_password_not_match() {
        Validators.confirmPassword(TextFieldState("12345"), TextFieldState("123456")).apply {
            assert(error)
        }
    }

    @Test
    fun confirm_password_match() {
        Validators.confirmPassword(TextFieldState("123456"), TextFieldState("123456")).apply {
            assert(!error)
        }
    }

    @Test
    fun personal_details_Blank() {
        Validators.personalDetails(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun personal_details_Right() {
        Validators.personalDetails(TextFieldState("vaibhav")).apply {
            assert(!error)
        }
    }

    @Test
    fun date_not_selected() {
        Validators.date(SelectableState("")).apply {
            assert(error)
        }
    }

    @Test
    fun date_selected() {
        Validators.date(SelectableState("07/02/1996")).apply {
            assert(!error)
        }
    }

    @Test
    fun gender_not_selected() {
        Validators.gender(DropdownState("NotSelected"), arrayOf("NotSelected")).apply {
            assert(error)
        }
    }

    @Test
    fun gender_selected() {
        Validators.gender(DropdownState("A"), arrayOf("NotSelected", "A")).apply {
            assert(!error)
        }
    }

    @Test
    fun check_address_blank() {
        Validators.checkAddress(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun check_address_valid() {
        Validators.checkAddress(TextFieldState("ABC")).apply {
            assert(!error)
        }
    }

    @Test
    fun check_postcode_blank() {
        Validators.checkPostCode(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun check_postcode_invalid() {
        Validators.checkPostCode(TextFieldState("41409")).apply {
            assert(error)
        }
    }

    @Test
    fun check_postcode_valid() {
        Validators.checkPostCode(TextFieldState("416409")).apply {
            assert(!error)
        }
    }

    @Test
    fun check_phone_blank() {
        Validators.checkPhone(TextFieldState("")).apply {
            assert(error)
        }
    }

    @Test
    fun check_phone_Invalid() {
        Validators.checkPhone(TextFieldState("198")).apply {
            assert(error)
        }
    }

    @Test
    fun check_phone_valid() {
        Validators.checkPhone(TextFieldState("+91 9456211568")).apply {
            assert(!error)
        }
    }
    @Test
    fun check_review_valid(){
        Validators.checkReview(TextFieldState("Some to do")).apply {
            assert(!error)
        }
    }

    @Test
    fun check_review_empty(){
        Validators.checkReview(TextFieldState("")).apply {
            assert(error)
        }
    }
    @Test
    fun check_review_large(){
        Validators.checkReview(TextFieldState("A".repeat(501))).apply {
            assert(error)
        }
    }
}
