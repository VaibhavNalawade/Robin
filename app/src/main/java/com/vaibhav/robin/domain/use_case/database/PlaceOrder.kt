package com.vaibhav.robin.domain.use_case.database

import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.domain.repository.DatabaseRepository
import javax.inject.Inject

class PlaceOrder@Inject constructor(private val repo:DatabaseRepository){
suspend operator fun invoke(orderItem: OrderItem)=repo.placeOrder(orderItem)
}
