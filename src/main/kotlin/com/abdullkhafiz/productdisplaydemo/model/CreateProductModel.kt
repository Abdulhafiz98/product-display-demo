package com.abdullkhafiz.productdisplaydemo.model


data class CreateProductModel(
    val id: Long? = null,
    val title: String,
    val vendor: String,
    val productType: String
)