package com.vaibhav.unit.viewmodels

import com.vaibhav.robin.domain.model.Response
import com.vaibhav.robin.domain.use_case.database.DatabaseUseCases
import com.vaibhav.robin.domain.use_case.database.RemoveCartItems
import com.vaibhav.robin.presentation.models.state.MessageBarState
import com.vaibhav.robin.presentation.ui.cart.CartViewModel
import com.vaibhav.unit.utility.MainDispatcherRule
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.stub
import java.lang.Exception
import java.util.UUID


@RunWith(MockitoJUnitRunner::class)
class CartViewModelTest {
    private lateinit var cartViewModel: CartViewModel

    @Mock
    private lateinit var databaseUseCases: DatabaseUseCases

    @Mock
    private lateinit var removeCartItems: RemoveCartItems

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        cartViewModel = CartViewModel(databaseUseCases)
    }

    @Test
    fun setMessageBarState() {
        val messageBarState = MessageBarState()
        cartViewModel.setMessageBarState(messageBarState)
        val t = cartViewModel.javaClass.getDeclaredField("messageBarState")
        t.isAccessible = true
        assert(t.get(cartViewModel) == messageBarState)
    }

    @Test
    fun `test removeCartItemJob Success`() {
        val cartId = UUID.randomUUID().toString()
        val messageBarState = MessageBarState()
        cartViewModel.setMessageBarState(messageBarState)

        runTest {
            databaseStub()
        }
        cartViewModel.removeCartItem(cartId)
        val field = cartViewModel.javaClass.getDeclaredField("currentRunningJobs")
        field.isAccessible = true
        var job = (field.get(cartViewModel) as MutableList<*>)
            .filterIsInstance<Pair<String, Job>>()

        runTest {
            while (job.first().second.isActive) {
                delay(1000)
            }
            assert(messageBarState.success != null)
            delay(5000)
            job = (field.get(cartViewModel) as MutableList<*>).filterIsInstance<Pair<String, Job>>()
            assert(job.isEmpty()) {
                "${job.first().first} is Running. size ${job.size}"
            }
        }
    }

    @Test
    fun `test removeCartItemJob Failure`() {
        val cartId = UUID.randomUUID().toString()
        val messageBarState = MessageBarState()
        cartViewModel.setMessageBarState(messageBarState)

        runTest {
            databaseStub(false)
        }

        cartViewModel.removeCartItem(cartId)
        val field = cartViewModel.javaClass.getDeclaredField("currentRunningJobs")
        field.isAccessible = true
        var job = (field.get(cartViewModel) as MutableList<*>)
            .filterIsInstance<Pair<String, Job>>()

        runTest {
            while (job.first().second.isActive) {
                delay(1000)
            }
            assert(messageBarState.error != null)
            delay(5000)
            job = (field.get(cartViewModel) as MutableList<*>).filterIsInstance<Pair<String, Job>>()
            assert(job.isEmpty()) {
                "${job.first().first} is Running. size ${job.size}"
            }
        }
    }

    @Test
    fun `test Duplicate removeCartItem job handle`() {
        val cartId = UUID.randomUUID().toString()
        val messageBarState = MessageBarState()
        cartViewModel.setMessageBarState(messageBarState)

        runTest {
            databaseStub(delay = Long.MAX_VALUE)
        }
        cartViewModel.removeCartItem(cartId)
        cartViewModel.removeCartItem(cartId)
        assert(messageBarState.error != null)
        val field = cartViewModel.javaClass.getDeclaredField("currentRunningJobs")
        field.isAccessible = true
        val job = (field.get(cartViewModel) as MutableList<*>)
            .filterIsInstance<Pair<String, Job>>()
        assert(job.size == 1)
    }
    /* @Test
     fun `test Cancelled removeCartItem job handle`(){
         val cartId = UUID.randomUUID().toString()
         val messageBarState = MessageBarState()
         cartViewModel.setMessageBarState(messageBarState)
         runTest {
           databaseStubLoop()
         }
         cartViewModel.removeCartItem(cartId)
         val field = cartViewModel.javaClass.getDeclaredField("currentRunningJobs")
         field.isAccessible = true
         var job = (field.get(cartViewModel) as MutableList<*>)
             .filterIsInstance<Pair<String, Job>>()
         assert(job.size==1)
         job.first().second.cancel()
         runTest {
             delay(3000)
              job = (field.get(cartViewModel) as MutableList<*>)
                 .filterIsInstance<Pair<String, Job>>()
             assert(job.isEmpty())
         }
     }*/

    @Test
    fun `test cause Exception in Flow`() {
        val cartId = UUID.randomUUID().toString()
        val messageBarState = MessageBarState()
        cartViewModel.setMessageBarState(messageBarState)

        runTest {
            databaseStubFlowException()
        }
        cartViewModel.removeCartItem(cartId)
        val field = cartViewModel.javaClass.getDeclaredField("currentRunningJobs")
        field.isAccessible = true
        var job = (field.get(cartViewModel) as MutableList<*>)
            .filterIsInstance<Pair<String, Job>>()
        runTest {
            while (job.first().second.isActive) {
                delay(1000)
            }
            assert(messageBarState.error != null)
            delay(5000)
            job = (field.get(cartViewModel) as MutableList<*>).filterIsInstance<Pair<String, Job>>()
            assert(job.isEmpty()) {
                "${job.first().first} is Running. size ${job.size}"
            }
        }
    }


    private suspend fun databaseStub(routeSuccess: Boolean = true, delay: Long = 100L) {
        removeCartItems.stub {
            onBlocking { invoke(any<String>()) }
                .doAnswer {
                    flow {
                        emit(Response.Loading)
                        delay(delay)
                        emit(
                            if (routeSuccess) Response.Success(true)
                            else Response.Error(Exception())
                        )
                    }
                }
        }
        databaseUseCases.stub {
            on { removeCartItems }.thenReturn(removeCartItems)
        }
    }

    private suspend fun databaseStubFlowException() {
        removeCartItems.stub {
            onBlocking { invoke(any<String>()) }
                .doAnswer {
                    flow {
                        throw (Exception())
                    }
                }
        }
        databaseUseCases.stub {
            on { removeCartItems }.thenReturn(removeCartItems)
        }
    }
}