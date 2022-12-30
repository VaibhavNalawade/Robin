package com.vaibhav.robin.domain.use_case.database

data class DatabaseUseCases (
    val updateProfileDateAndGender:UpdateProfileDateAndGender,
    val updateAddressAndPhone: UpdateAddressAndPhone,
    val initializeProfile: InitializeProfile,
    val getProduct: GetProduct,
    val getReview:GetReview,
    val writeReview:WriteReview,
    val getUserReview:GetUserReview,
    val addFavourite:AddFavourite,
    val removeFavourite:RemoveFavourite,
    val checkFavourite:CheckFavourite,
    val addCartItem: AddCartItem,
    val getCartItem:GetCartItem,
    val getProducts:GetProducts,
    val listenForCartItems:ListenForCartItems,
    val removeCartItems: RemoveCartItems,
    val getCategory: GetCategory,
    val getBrands: GetBrands,
    val filterProducts:FilterProducts,
)