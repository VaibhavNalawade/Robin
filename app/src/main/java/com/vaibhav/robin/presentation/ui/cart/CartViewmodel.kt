package com.vaibhav.robin.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaibhav.robin.R
import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.model.Response.Loading
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.presentation.doNothing
import com.vaibhav.robin.presentation.UiText
import com.vaibhav.robin.presentation.models.state.MessageBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 *Unit Test [com.vaibhav.unit.viewmodels.CartViewModelTest]*/
@HiltViewModel
class CartViewModel @Inject constructor(
    private val databaseUseCases: DatabaseUseCases
) : ViewModel() {

    private lateinit var messageBarState: MessageBarState
    private var currentRunningJobs = mutableListOf<Pair<String, Job>>()
    private var itemCount = 0
    private var timerResetJob: Job? = null
    fun setMessageBarState(messageBarState: MessageBarState) {
        this.messageBarState = messageBarState
    }

    /**
     * [removeCartItem] predates that remove-item job currently running or launch new one.
     * @param[cartId] is required to identifies specific item
     * */
    fun removeCartItem(cartId: String) {
        if (isThisNewJob(cartId))
            currentRunningJobs.add(cartId to removeCartItemJob(cartId))
        else {
            val jobPair = getJobFromId(cartId)
            if (jobPair?.second?.isActive == true)
                messageBarState.addError(UiText.StringResource(R.string.remove_cart_message_bar_removing))
            /*else if (jobPair?.second?.isCancelled == true)
                removeFromRunningQueue(jobPair.first).also {
                    currentRunningJobs.add(cartId to removeCartItemJob(cartId))
                }*/
        }
    }

    /**
     * [removeCartItemJob] launch new coroutines to remove cartItem. upon response send appropriate
     * message.
     * @param[cartId] is required to identifies specific item
     * @return [Job] to manage its status
     * */
    private fun removeCartItemJob(cartId: String) = viewModelScope.launch(Dispatchers.IO) {
        databaseUseCases.removeCartItems(cartId)
            .catch {
                messageBarState
                    .addError(UiText.StringResource(R.string.remove_cart_message_bar_error))
                    .also { removeFromRunningQueue(cartId) }
            }.collect {
                when (it) {
                    is Response.Error -> messageBarState
                        .addError(UiText.StringResource(R.string.remove_cart_message_bar_error))
                        .also { removeFromRunningQueue(cartId) }

                    is Response.Success -> {
                        itemCount++
                        messageBarState.addSuccess(
                            UiText.StringResource(
                                R.string.remove_cart_message_bar_success,
                                arrayOf(itemCount)
                            )
                        )
                        timerResetJob?.cancel()
                        timerResetJob = resetCountTimer()
                        removeFromRunningQueue(cartId)
                    }

                    Loading -> doNothing()
                }
            }
    }

    private fun removeFromRunningQueue(cartId: String) =
        currentRunningJobs.removeAll { it.first == cartId }

    private fun getJobFromId(cartId: String) = currentRunningJobs.find { it.first == cartId }
    private fun isThisNewJob(cartId: String) = currentRunningJobs.any { it.first == cartId }.not()
    private suspend fun resetCountTimer() = viewModelScope.launch {
        delay(3000L)
        itemCount = 0
    }
}