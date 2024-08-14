package com.vaibhav.robin.data

import androidx.core.net.toUri
import com.vaibhav.robin.data.models.Address
import com.vaibhav.robin.data.models.CartItem
import com.vaibhav.robin.data.models.OrderItem
import com.vaibhav.robin.data.models.PaymentData
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.CurrentUserProfileData
import com.vaibhav.robin.presentation.generateSingleLineAddress
import java.util.UUID

object PreviewMocks {
    val profileMockData = CurrentUserProfileData(
        userAuthenticated = true,
        name = "Vaibhav Nalawade",
        image = "https://picsum.photos/200".toUri(),
        email = "Vn123@test.com",
        phone = "8668835535",
        userIsVerified = false,
        uid = "12345678456789"
    )

    //TODO outdated product definition
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
        variantIndex = listOf("0"),
        sizes = mapOf(
            "0" to listOf(
                mapOf(
                    "discount" to false,
                    "discountedPrice" to 0,
                    "price" to 2299,
                    "size" to "32",
                    "stock" to "32"
                )
            )
        ),
        media = mapOf("0" to listOf("","",""))
    )
    val cartItem = listOf(
        CartItem(
            brandName = "Loop",
            productImage = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/Product%2Fu0TkO3sMDEAoQqnAzFmh%2F58056.jpg?alt=media&token=831f730d-ae21-4d7d-997e-fe902dcf462b",
            productId = "u0TkO3sMDEAoQqnAzFmh",
            productVariant = "variant_0",
            price = 2299.00,
            productSize = 0,
            cartId = "8bfdba13-728d-4ded-936d-f1f287f17077",
            brandLogo = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/brandProfileImage%2FArtboard%206.png?alt=media&token=39141dd7-112e-4be4-8b40-e76f74c1d252",
            productName = "Shopper",
        ),
        CartItem(
            brandName = "Flame",
            productImage = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/Product%2F0JeBFgoTTrXW5UaG4zWb%2F12565.jpg?alt=media&token=648d2a2a-060a-43dc-b3b6-3b9d3988f98a",
            productId = "0JeBFgoTTrXW5UaG4zWb",
            productVariant = "variant_1",
            price = 1999.00,
            productSize = 0,
            cartId = "8f24d952-cb57-4168-aa0a-eb79861de91d",
            brandLogo = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/brandProfileImage%2FArtboard%201.png?alt=media&token=f83bdcb4-3e9c-4c67-bc91-68b95b075cda",
            productName = "Linen-blend shirt",
        ),
        CartItem(
            brandName = "Loop",
            productImage = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/Product%2FXMd8KkntoI3aDhwiLarO%2F49710.jpg?alt=media&token=779cda11-aa24-4e1c-87f8-b3e3ea50b4e6",
            productId = "XMd8KkntoI3aDhwiLarO",
            productVariant = "variant_0",
            price = 699.00,
            productSize = 0,
            cartId = "a3f08372-6d76-4358-a3af-19713971995e",
            brandLogo = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/brandProfileImage%2FArtboard%206.png?alt=media&token=39141dd7-112e-4be4-8b40-e76f74c1d252",
            productName = "Belt",
        ),
        CartItem(
            brandName = "Flame",
            productImage = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/Product%2FI7uIHF772ag4WRglBAJV%2F64522.jpg?alt=media&token=216c464d-91ba-4a14-b829-9dad519d3ab7",
            productId = "I7uIHF772ag4WRglBAJV",
            productVariant = "variant_0",
            price = 1499.00,
            productSize = 0,
            cartId = "d2a373bb-0b46-4bed-8a5d-1ce877e1b9b3",
            brandLogo = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/brandProfileImage%2FArtboard%201.png?alt=media&token=f83bdcb4-3e9c-4c67-bc91-68b95b075cda",
            productName = "Wide twill trousers",
        ),
        CartItem(
            brandName = "Loop",
            productImage = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/Product%2F77tSL1hcwIeeVaPyj08T%2F82559.jpg?alt=media&token=0d805288-7945-4532-a945-095d1c72fb67",
            productId = "77tSL1hcwIeeVaPyj08T",
            productVariant = "variant_0",
            price = 2299.00,
            productSize = 0,
            cartId = "e1b446f3-4450-4e83-ace9-f0bb5b1de209",
            brandLogo = "https://firebasestorage.googleapis.com/v0/b/projectrobin-a74a8.appspot.com/o/brandProfileImage%2FArtboard%206.png?alt=media&token=39141dd7-112e-4be4-8b40-e76f74c1d252",
            productName = "Trainers",
        )
    )

    val address = Address(
        fullName = "John Muir",
        streetAddress = "Harni Road, Opp Lbs School,",
        apartmentAddress = "712 Suhas Society",
        city = "Bangalore",
        state = "Karnataka",
        pinCode = "416409",
        phoneNumber = "9876543210"
    )
    val paymentData = PaymentData(
        id = product.id,
        pan = "4111111111111111",
        cvv = 234,
        expiryDate = "23/Jan/2013",
        cardHolderName = "John Muir"
    )
    val orderItem = OrderItem(
        items = cartItem,
        shippingAddress = generateSingleLineAddress(address),
        totalPrice = 4444.00
    )
}