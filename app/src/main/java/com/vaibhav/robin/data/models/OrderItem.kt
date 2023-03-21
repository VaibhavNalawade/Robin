package com.vaibhav.robin.data.models

import com.google.firebase.Timestamp
import java.util.Date

data class OrderItem(
    val items: List<CartItem> = emptyList(),
    private var _expectedDeliveryDate: Timestamp = Timestamp.now(),
    val shippingAddress: String = "",
    val paymentMethod: String = "",
    private var _orderPlacedDate: Timestamp = Timestamp.now(),
    val id: String = "",
    val paymentId: String = ""
){
   var expectedDeliveryDate:Date
       get() = _expectedDeliveryDate.toDate()
       set(value) {
           _expectedDeliveryDate =  Timestamp(value)
       }
    var orderPlacedDate:Date
        get() = _orderPlacedDate.toDate()
        set(value) {
            _orderPlacedDate =  Timestamp(value)
        }
}

