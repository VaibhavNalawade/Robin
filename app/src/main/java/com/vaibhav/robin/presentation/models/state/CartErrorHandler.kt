package com.vaibhav.robin.presentation.models.state

import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.vaibhav.robin.R
import com.vaibhav.robin.presentation.ErrorResolutionPolicy
import com.vaibhav.robin.presentation.ErrorVisualsType
import com.vaibhav.robin.presentation.UiText
import com.vaibhav.robin.presentation.handler.RobinErrorHandler
import com.vaibhav.robin.presentation.ui.common.DynamicProperties

class CartErrorHandler(
    val exception: Exception,
    onSupport: () -> Unit,
    onAttemptLater: () -> Unit,
    onRetry: () -> Unit
) : RobinErrorHandler {

    private var title: UiText = UiText.StringResource(R.string.unknown_error)
    private var message: UiText = UiText.StringResource(R.string.unknown_error)
    private var errorCode = UiText.DynamicString("-1")
    private var errorResolutionPolicy: ErrorResolutionPolicy =
        ErrorResolutionPolicy.Support(onSupport)
    private var errorVisualsType: ErrorVisualsType = ErrorVisualsType
        .LottieAnimationType(
            res = R.raw.trex,
            iterations = Int.MAX_VALUE,
            dynamicProperties = {  DynamicProperties.trex()}
            )

    init {
        when (exception) {
            is FirebaseException -> {
                when (exception) {
                    is FirebaseNetworkException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.network_error_message)
                        errorCode = UiText.DynamicString("F:404")
                        errorResolutionPolicy = ErrorResolutionPolicy.Retry(onRetry)
                       /**
                       common Error Don't require report
                       Firebase.crashlytics.recordException(exception.fillInStackTrace())
                       */
                    }

                    is FirebaseTooManyRequestsException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.network_error_message)
                        errorCode = UiText.DynamicString("F:429")
                        errorResolutionPolicy = ErrorResolutionPolicy.AttemptLater(onAttemptLater)
                        Firebase.crashlytics.recordException(exception.fillInStackTrace())
                    }

                    is FirebaseFirestoreException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.network_error_message)
                        errorCode = UiText.DynamicString("FS:${exception.code.value()}")
                        errorResolutionPolicy = ErrorResolutionPolicy.Retry(onRetry)
                        Firebase.crashlytics.recordException(exception.fillInStackTrace())
                    }

                    else -> {
                        title = UiText.StringResource(R.string.unknown_error)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = UiText.DynamicString("F")
                        errorResolutionPolicy = ErrorResolutionPolicy.Retry(onRetry)
                        Firebase.crashlytics.recordException(exception.fillInStackTrace())
                    }
                }
            }

            else -> {
                title = UiText.StringResource(R.string.unknown_error)
                message = UiText.StringResource(R.string.unknown_error)
                errorCode = UiText.DynamicString("NF")
                errorResolutionPolicy = ErrorResolutionPolicy.Retry(onRetry)
                Firebase.crashlytics.recordException(exception.fillInStackTrace())
            }
        }
    }

    override fun getTitle(): UiText = title
    override fun getMessage(): UiText = message
    override fun getErrorCode(): UiText = errorCode
    override fun getErrorResolutionPolicy(): ErrorResolutionPolicy = errorResolutionPolicy
    override fun getErrorVisualType(): ErrorVisualsType = errorVisualsType
}