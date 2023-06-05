package com.vaibhav.robin.presentation


import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
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
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.domain.exceptions.RobinException
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.presentation.ui.theme.Values
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


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
            is StringResource -> stringResource(id, *args)
        }
    }
}

//TODO Need To add all Exception error message and Icons
class ExceptionHandler(val exception: Exception) {
    var title: UiText = UiText.StringResource(R.string.unknown_error)
        private set
    var message: UiText = UiText.StringResource(R.string.unknown_error)
        private set
    var errorCode: String? = null
        private set
    var icon: ImageVector = Icons.Default.Warning
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
                        // icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }

                    is FirebaseTooManyRequestsException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = null
                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }

                    is FirebaseAuthException -> {
                        when (exception) {
                            is FirebaseAuthActionCodeException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }

                            is FirebaseAuthEmailException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }

                            is FirebaseAuthInvalidCredentialsException -> {
                                if (exception is FirebaseAuthWeakPasswordException) {
                                    //todo messgeg
                                } else {
                                    title = UiText.StringResource(R.string.network_error_title)
                                    message = UiText.StringResource(R.string.unknown_error)
                                    errorCode = exception.errorCode
                                    //    icon= Icons.Default.SignalWifiConnectedNoInternet4
                                }
                            }

                            is FirebaseAuthInvalidUserException -> {
                                when (exception.errorCode) {
                                    "ERROR_USER_DISABLED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //    icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_USER_NOT_FOUND" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_USER_TOKEN_EXPIRED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_INVALID_USER_TOKEN" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //  icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }

                            is FirebaseAuthMultiFactorException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                //  icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }

                            is FirebaseAuthRecentLoginRequiredException -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                //  icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }

                            is FirebaseAuthUserCollisionException -> {

                                when (exception.errorCode) {
                                    "ERROR_EMAIL_ALREADY_IN_USE" -> {
                                        title = UiText.StringResource(R.string.email_already)
                                        message =
                                            UiText.StringResource(R.string.email_already_message)
                                        errorCode = exception.errorCode
                                        icon = Icons.Default.Warning
                                    }

                                    "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //    icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_CREDENTIAL_ALREADY_IN_USE" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }

                            is FirebaseAuthWebException -> {
                                when (exception.errorCode) {
                                    "ERROR_WEB_CONTEXT_ALREADY_PRESENTED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_WEB_CONTEXT_CANCELED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_WEB_STORAGE_UNSUPPORTED" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }

                                    "ERROR_WEB_INTERNAL_ERROR" -> {
                                        title = UiText.StringResource(R.string.network_error_title)
                                        message = UiText.StringResource(R.string.unknown_error)
                                        errorCode = exception.errorCode
                                        //    icon= Icons.Default.SignalWifiConnectedNoInternet4
                                    }
                                }
                            }

                            else -> {
                                title = UiText.StringResource(R.string.network_error_title)
                                message = UiText.StringResource(R.string.unknown_error)
                                errorCode = exception.errorCode
                                //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                            }
                        }
                    }

                    is FirebaseFirestoreException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = exception.code.value().toString()
                        //   icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                }

            }

            is RobinException -> {
                when (exception) {
                    is ValidationFailedException -> {
                        title = UiText.StringResource(R.string.network_error_title)
                        message = UiText.StringResource(R.string.unknown_error)
                        errorCode = exception.errorCode.toString()
                        //    icon= Icons.Default.SignalWifiConnectedNoInternet4
                    }
                }
            }

            else -> {
                title = UiText.StringResource(R.string.network_error_title)
                message = UiText.StringResource(R.string.unknown_error)
                errorCode = null
                //   icon= Icons.Default.SignalWifiConnectedNoInternet4
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
    val difference = Date().time - timestamp.toDate().time
    val sec = difference / 1000

    val min = sec / 60
    val hours = min / 60
    val days = hours / 24
    val week = days / 7
    val months = days / 30
    val years = months / 12

    return if (min < 1)
        stringResource(id = R.string.now)
    else if (min < 59)
        "$min ${stringResource(id = R.string.minutes)}"
    else if (hours < 24)
        "$hours ${stringResource(id = R.string.hours)}"
    else if (days < 7)
        "$days ${stringResource(id = R.string.days)}"
    else if (week < 4)
        "$week ${stringResource(id = R.string.weeks)}"
    else if (months < 12)
        "$months ${stringResource(id = R.string.months)}"
    else "$years ${stringResource(id = R.string.years)}"
}

enum class RobinNavigationType {
    NAVIGATION_DRAWER, NAVIGATION_RAILS
}

enum class RobinAppBarType {
    COLLAPSING_APPBAR, PERMANENT_APPBAR
}

enum class Order {
    ASCENDING,
    DESCENDING
}

enum class CardType(@DrawableRes val id: Int) {
    Visa(R.drawable.visa_2021),
    MasterCard(R.drawable.mastercard_logo),
    Rupee(R.drawable.rupay),
    Other(R.drawable.credit_card)
}

fun getCardResourceByPan(pan: String): Int =
    if (pan.startsWith("4"))
        CardType.Visa.id
    else if (pan.startsWith("2") || pan.startsWith("5"))
        CardType.MasterCard.id
    else if (pan.startsWith("60") || pan.startsWith("65"))
        CardType.Rupee.id
    else CardType.Other.id

fun getCardTypeByPan(pan: String): CardType =
    if (pan.startsWith("4"))
        CardType.Visa
    else if (pan.startsWith("2") || pan.startsWith("5"))
        CardType.MasterCard
    else if (pan.startsWith("60") || pan.startsWith("65"))
        CardType.Rupee
    else CardType.Other



fun calculateSummary(list: List<CartItem>): OrderSummary {
    val s = OrderSummary()
    list.forEach {
        s.subTotal = s.subTotal + it.price
        s.tax = s.tax + (it.price * .18)
        s.shippiing = s.shippiing + 50.00
        s.total = s.total + (it.price + (it.price * .18) + 50.00)
    }
    return s
}

data class OrderSummary(
    var subTotal: Double = 0.0,
    var tax: Double = 0.0,
    var shippiing: Double = 0.0,
    var total: Double = 0.0
)

fun generateSingleLineAddress(address: Address): String {
    val string = StringBuilder()
    string.append(address.fullName)
    string.append("\n")
    string.append(address.apartmentAddress)
    string.append(", ")
    string.append(address.streetAddress)
    string.append(",\n")
    string.append(address.city)
    string.append(", ")
    string.append(address.state)
    string.append(",\n")
    string.append(address.pinCode)
    string.append("\n")
    string.append("Ph : ")
    string.append(address.phoneNumber)
    return string.toString()
}

val priceFormat = DecimalFormat("#,##0.00")
const val DATE_DD_MMM = "dd MMM"
const val DATE_TEXT_DAY_FORMAT = "EEEE dd/MM/yyyy"
const val DATE_TEXT_MONTH = "dd MMM yyyy"
fun generateDeliveryStatus(date: Date): UiText {
    val difference = date.time - Calendar.getInstance().timeInMillis
    val diffInDays = TimeUnit.MILLISECONDS.toDays(difference)
    return if (diffInDays == 0L) UiText.StringResource(R.string.delivered)
    else if (diffInDays >= 6L) UiText.StringResource(R.string.order_confirm)
    else if (diffInDays == 5L) UiText.StringResource(R.string.preparing_for_dispatch)
    else if (diffInDays >= 2L) UiText.StringResource(R.string.on_the_way)
    else if (diffInDays == 1L) UiText.StringResource(R.string.out_for_delivery)
    else UiText.StringResource(R.string.delivered)
}

fun generateOrderName(order: OrderItem) =
    "${order.items.firstOrNull()?.productName} ${if (order.items.size > 1) (order.items.size - 1).toString() + "Items" else ""}"

fun generateCardName(pan: String) = when (getCardTypeByPan(pan)) {
    CardType.Visa -> UiText.StringResource(R.string.visa_ending, arrayOf(pan.takeLast(4)))
    CardType.MasterCard -> UiText.StringResource(R.string.mastercard_ending, arrayOf(pan.takeLast(4)))
    CardType.Rupee -> UiText.StringResource(R.string.rupee_ending, arrayOf(pan.takeLast(4)))
    CardType.Other -> UiText.StringResource(R.string.card_ending, arrayOf(pan.takeLast(4)))
}

fun generateFakeCardPan() =
    "${arrayOf(20, 40, 50, 60, 65).random()}${(10000000000000..99999999999999).random()}"

@Composable
fun boxEmptyDynamicProperties(): LottieDynamicProperties {
    val pxValue = LocalDensity.current.run { 6.dp.toPx() }
  return  rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.STROKE_WIDTH,
            value = pxValue,
            keyPath = arrayOf(
                "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "Layer 8/boxgirl2 Outlines", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "Body/boxgirl2 Outlines", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "Legs/boxgirl2 Outlines", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primaryContainer.toArgb(),
            keyPath = arrayOf(
                "BOX/boxgirl2 Outlines", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "arms/boxgirl2 Outlines", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "head/boxgirl2 Outlines", "Group 8", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.surfaceColorAtElevation(Values.Dimens.surface_elevation_5)
                .toArgb(),
            keyPath = arrayOf(
                "head/boxgirl2 Outlines", "Group 7", "**"
            )
        ),
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR,
            value = MaterialTheme.colorScheme.primary.toArgb(),
            keyPath = arrayOf(
                "head/boxgirl2 Outlines", "Group 11", "**"
            )
        ),
    )
}
