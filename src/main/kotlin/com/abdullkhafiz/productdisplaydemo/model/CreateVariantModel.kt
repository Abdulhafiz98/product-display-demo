package com.abdullkhafiz.productdisplaydemo.model

data class CreateVariantModel(
    val id: Long? = null,
    val title: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val taxable: Boolean,
    val price: String,
    val grams: Int
)
