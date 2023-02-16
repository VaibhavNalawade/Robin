package com.vaibhav.robin.data

import androidx.core.net.toUri
import com.vaibhav.robin.data.models.Product
import com.vaibhav.robin.domain.model.ProfileData

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
        brandName = "Lorem",
        categoryId = "asdf34Fdsf",
        categoryName = "Ipsum",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras auctor urna id justo volutpat blandit. Vivamus rhoncus ac enim vitae ornare. Mauris vitae velit eu nulla aliquet viverra sit amet sed diam. Donec tincidunt risus massa, non lobortis purus feugiat id. Cras egestas maximus tellus, eget viverra tortor luctus faucibus. Duis et faucibus ligula. Vivamus pretium lacus sit amet quam dapibus, et ullamcorper urna efficitur. Pellentesque vel luctus velit. Nulla dignissim interdum sem ut venenatis. Donec volutpat diam leo, quis vehicula orci iaculis a. Proin in blandit ante. Pellentesque ex lorem, rhoncus eget pellentesque vel, imperdiet nec ipsum. Donec nec bibendum dui. Cras ut suscipit justo, nec feugiat justo. Interdum et malesuada fames ac ante ipsum primis in faucibus.",
        maxPrice = 1230.00,
        minPrice = 1834.00,
        ratingStars = 3.4f,
        ratingCount = 4004,
        name = "Lorem ipsum",
        id = "ARSFZDfse",
        status = "Avilable",

    )

}