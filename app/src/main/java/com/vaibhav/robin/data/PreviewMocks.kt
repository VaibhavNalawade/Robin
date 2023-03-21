package com.vaibhav.robin.data

import androidx.core.net.toUri
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData
import java.util.Date
import java.util.UUID

object PreviewMocks {
    val profileMockData = ProfileData(
        name = "Vaibhav Nalawade",
        image = "https://picsum.photos/200".toUri(),
        email = "Vn123@test.com",
        phone = "8668835535",
        userIsVerified = false,
    )
    val product = Product(
        brandId = "asdjr43ddD",
        brandLogo = "https://picsum.photos/200",
        brandName = "Apple",
        categoryId = "asdf34Fdsf",
        categoryName = "Ipsum",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras auctor urna id justo volutpat blandit. Vivamus rhoncus ac enim vitae ornare. Mauris vitae velit eu nulla aliquet viverra sit amet sed diam. Donec tincidunt risus massa, non lobortis purus feugiat id. Cras egestas maximus tellus, eget viverra tortor luctus faucibus. Duis et faucibus ligula. Vivamus pretium lacus sit amet quam dapibus, et ullamcorper urna efficitur. Pellentesque vel luctus velit. Nulla dignissim interdum sem ut venenatis. Donec volutpat diam leo, quis vehicula orci iaculis a. Proin in blandit ante. Pellentesque ex lorem, rhoncus eget pellentesque vel, imperdiet nec ipsum. Donec nec bibendum dui. Cras ut suscipit justo, nec feugiat justo. Interdum et malesuada fames ac ante ipsum primis in faucibus.",
        maxPrice = 1230.00,
        minPrice = 1834.00,
        ratingStars = 3.4f,
        ratingCount = 4004,
        name = "Apple Watch II",
        id = UUID.randomUUID().toString(),
        status = "Avilable",

    )
    val cartItem = CartItem(
        cartId = UUID.randomUUID().toString(),
        productId = product.id,
        productName = product.name,
        productVariant = "S",
        productSize = 0,
        productImage = "https://picsum.photos/200",
        price = 3243.00,
        brandName = product.brandName,
        brandLogo = product.brandLogo
    )
    val orderItem=OrderItem(
        items = listOf(cartItem, cartItem),
    )
    val address=Address(
        fullName = "John Muir",
        streetAddress = "Harni Road, Opp Lbs School,",
        apartmentAddress = "712 Suhas Society",
        city = "Bangalore",
        state = "Karnataka",
        pinCode = "416409",
        phoneNumber = "9876543210"
    )
    val paymentData=PaymentData(
        id = product.id,
        prn = "4111111111111111",
        cvv = 234,
        expiryDate = "23/Jan/2013",
        cardHolderName = "John Muir"
    )

}