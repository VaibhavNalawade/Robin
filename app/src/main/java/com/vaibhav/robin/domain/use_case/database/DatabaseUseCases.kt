package com.vaibhav.robin.domain.use_case.database

data class DatabaseUseCases (
    val updateProfileDateAndGender:UpdateProfileDateAndGender,
    val updateAddressAndPhone: UpdateAddressAndPhone,
    val initializeProfile: InitializeProfile,
    val getProduct: GetProduct,
    val getReview:GetReview,
    val writeReview:WriteReview
)