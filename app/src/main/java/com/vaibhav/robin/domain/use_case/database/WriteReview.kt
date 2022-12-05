package com.vaibhav.robin.domain.use_case.database

import android.net.Uri
import androidx.compose.runtime.MutableState
import com.vaibhav.robin.data.models.Review
import com.vaibhav.robin.domain.Validators
import com.vaibhav.robin.domain.exceptions.ValidationFailedException
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.repository.DatabaseRepository
import com.vaibhav.robin.presentation.models.state.TextFieldState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WriteReview @Inject constructor(private val repo: DatabaseRepository) {

    suspend operator fun invoke(
        userName: String,
        userPhoto: Uri,
        comment: MutableState<TextFieldState>,
        stars: Int,
        productID: String
    ): Flow<Response<Boolean>> {
        comment.value = Validators.checkReview(comment.value)
        if (!comment.value.error)
            return repo.writeReview(
                productID, Review(
                    userName = userName,
                    profileImage = userPhoto.toString(),
                    content = comment.value.text,
                    rating = stars
                )
            )
        else return flow{(Response.Error(ValidationFailedException()))}
    }
}
