package com.vaibhav.robin.domain.use_case.database

import androidx.compose.runtime.MutableState
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.DatabaseRepository
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject

class AddPayment @Inject constructor(private val repo: DatabaseRepository) {
    suspend operator fun invoke(
        pan: MutableState<TextFieldState>,
        expiryDate: MutableState<TextFieldState>,
        cvv: MutableState<TextFieldState>,
        cardholderName: MutableState<TextFieldState>
    ): Flow<Response<Boolean>> {
        /*pan.value=Validators.checkPan(pan.value)
        expiryDate.value=Validators.checkExpiryDate(expiryDate.value)
        return if (
            !pan.value.error &&
            !expiryDate.value.error
        )*/
         return   repo.addPayment(
                PaymentData(
                    UUID.randomUUID().toString(),
                    pan.value.text,
                    cvv.value.text.toInt(),
                    expiryDate.value.text,
                    cardholderName.value.text
                )

            )
       // else flow{ Response.Error(ValidationFailedException())}
    }
}
