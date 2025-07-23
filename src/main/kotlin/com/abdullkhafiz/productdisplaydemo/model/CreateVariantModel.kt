package com.abdullkhafiz.productdisplaydemo.model

data class CreateVariantModel(
    val title: String,
    val taxable: Boolean? = false,
    val price: String,
    val grams: Int
)
