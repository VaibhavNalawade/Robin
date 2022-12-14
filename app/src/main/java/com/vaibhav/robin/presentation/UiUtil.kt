package com.vaibhav.robin.presentation


import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SignalWifiConnectedNoInternet4
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuthActionCodeException
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthMultiFactorException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseAuthWebException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.exceptions.RobinException
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

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

class ExceptionHandler(val exception: Exception) {
     var title: UiText=UiText.StringResource(R.string.unknown_error)
        private set
      var message: UiText =UiText.StringResource(R.string.unknown_error)
        private set
     var errorCode: String?=null
        private set
     var icon: ImageVector=Icons.Default.Warning
    private val LOG_TAG = "TAG"

    init {
        Log.w(LOG_TAG, exception.stackTraceToString())
        when (exception) {
            is FirebaseException -> {
                when (exception) {
                    is FirebaseNetworkException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.network_error_message)
                        errorCode = null
                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                    is FirebaseTooManyRequestsException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = null
                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                    is FirebaseAuthException -> {
                        when (exception) {
                            is FirebaseAuthActionCodeException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                            is FirebaseAuthEmailException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                            is FirebaseAuthInvalidCredentialsException -> {
                                if (exception is FirebaseAuthWeakPasswordException) {
                                    //todo messgeg
                                } else {
                                    title = UiText.StringResource(R.string.network_error_title)
                                    message = UiText.StringResource(R.string.unknown_error)
                                    errorCode = exception.errorCode
                                    icon= Icons.Default.SignalWifiConnectedNoInternet4
                                }
                            }
                            is FirebaseAuthInvalidUserException -> {
                                when (exception.errorCode) {
                                    "ERROR_USER_DISABLED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_USER_NOT_FOUND" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_USER_TOKEN_EXPIRED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_INVALID_USER_TOKEN" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }
                            is FirebaseAuthMultiFactorException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                            is FirebaseAuthRecentLoginRequiredException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                            is FirebaseAuthUserCollisionException -> {

                                when (exception.errorCode) {
                                    "ERROR_EMAIL_ALREADY_IN_USE" -> {
                                        title = UiText.StringResource(R.string.email_already)
                                        message = UiText.StringResource(R.string.email_already_message)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.Warning
                                    }
                                    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_CREDENTIAL_ALREADY_IN_USE" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }
                            is FirebaseAuthWebException -> {
                                when (exception.errorCode) {
                                    "ERROR_WEB_CONTEXT_ALREADY_PRESENTED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_WEB_CONTEXT_CANCELED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_WEB_STORAGE_UNSUPPORTED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                    "ERROR_WEB_INTERNAL_ERROR" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }
                            else -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                        }
                    }
                    is FirebaseFirestoreException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = exception.code.value().toString()
                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                }

            }
            is RobinException-> {
                when(exception){
                    is ValidationFailedException ->{
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = exception.errorCode.toString()
                        icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                }
            }
            else -> {
                title = UiText.StringResource(R.string.network_error_title)
                message = UiText.StringResource(R.string.unknown_error)
                errorCode = null
                icon= Icons.Default.SignalWifiConnectedNoInternet4
            }
        }
/*        when ((exception as? FirebaseAuthException)?.errorCode) {
            "ERROR_INVALID_CUSTOM_TOKEN" -> "The custom token format is incorrect. Please check the documentation."
            "ERROR_CUSTOM_TOKEN_MISMATCH" -> "The custom token corresponds to a different audience."
            "ERROR_INVALID_CREDENTIAL" -> "The supplied auth credential is malformed or has expired."
            "ERROR_INVALID_EMAIL" -> "The email address is badly formatted."
            "ERROR_WRONG_PASSWORD" -> "The password is invalid or the user does not have a password."
            "ERROR_USER_MISMATCH" -> "The supplied credentials do not correspond to the previously signed in user."
            "ERROR_OPERATION_NOT_ALLOWED" -> "This operation is not allowed. You must enable this service in the console."
            "ERROR_MISSING_EMAIL" -> "An email address must be provided."
        }*/
    }
}

@Composable
fun timeStampHandler(timestamp: Timestamp): String {
    val difference= Date().time-timestamp.toDate().time
   val sec =difference/1000

    val min =sec/60
    val hours =min/60
    val days =hours/24
    val week =days/7
    val months=days/30
    val years =months/12

    return if (min<1)
        stringResource(id = R.string.now)
    else if (min<59)
        "$min ${stringResource(id = R.string.minutes)}"
    else if (hours<24)
        "$hours ${stringResource(id = R.string.hours)}"
    else if (days<7)
        "$days ${stringResource(id = R.string.days)}"
    else if (week<4)
        "$week ${stringResource(id = R.string.weeks)}"
    else if ( months<12)
        "$months ${stringResource(id = R.string.months)}"
    else   "$years ${stringResource(id = R.string.years)}"
}