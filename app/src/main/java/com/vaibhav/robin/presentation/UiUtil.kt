package com.vaibhav.robin.presentation

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.firebase.auth.FirebaseAuthException
import com.vaibhav.robin.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object UiUtil {
    @OptIn(ExperimentalPagerApi::class)
    fun autoscroll(pagerState: PagerState, scope: CoroutineScope) {
        scope.launch {
            (1..Int.MAX_VALUE).forEach { _ ->
                delay(5000)
                if (pagerState.pageCount > 0) {
                    when (pagerState.currentPage) {
                        pagerState.pageCount - 1 -> pagerState.animateScrollToPage(0)
                        else -> pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            }
        }
    }
}

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = emptyArray()
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id, args)
        }
    }
}

class AuthExceptionDecode(val exception:Exception) {
    lateinit var title: UiText
    lateinit var message: UiText
    lateinit var errorCode: String

    init {
        when((exception as? FirebaseAuthException)?.errorCode){
            "ERROR_INVALID_CUSTOM_TOKEN"-> "The custom token format is incorrect. Please check the documentation."
            "ERROR_CUSTOM_TOKEN_MISMATCH"-> "The custom token corresponds to a different audience."
            "ERROR_INVALID_CREDENTIAL"-> "The supplied auth credential is malformed or has expired."
            "ERROR_INVALID_EMAIL"-> "The email address is badly formatted."
            "ERROR_WRONG_PASSWORD"-> "The password is invalid or the user does not have a password."
            "ERROR_USER_MISMATCH"-> "The supplied credentials do not correspond to the previously signed in user."
            "ERROR_REQUIRES_RECENT_LOGIN"-> "This operation is sensitive and requires recent authentication. Log in again before retrying this request."
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL"-> "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address."
            "ERROR_EMAIL_ALREADY_IN_USE"-> {
                title= UiText.StringResource(R.string.email_already)
                message= UiText.StringResource(R.string.email_already_message)
                errorCode="ERROR_EMAIL_ALREADY_IN_USE"
            }
            "ERROR_CREDENTIAL_ALREADY_IN_USE"-> "This credential is already associated with a different user account."
            "ERROR_USER_DISABLED"-> "The user account has been disabled by an administrator."
            "ERROR_USER_TOKEN_EXPIRED"-> "The user\'s credential is no longer valid. The user must sign in again."
            "ERROR_USER_NOT_FOUND"-> "There is no user record corresponding to this identifier. The user may have been deleted."
            "ERROR_INVALID_USER_TOKEN"-> "The user\'s credential is no longer valid. The user must sign in again."
            "ERROR_OPERATION_NOT_ALLOWED"-> "This operation is not allowed. You must enable this service in the console."
            "ERROR_WEAK_PASSWORD"-> "The given password is invalid."
            "ERROR_MISSING_EMAIL"-> "An email address must be provided."
        }
        title= exception.message?.let { UiText.DynamicString(it) }!!
        message= UiText.DynamicString(exception.stackTraceToString())
        errorCode="ERROR_EMAIL_ALREADY_IN_USE"
    }
}
