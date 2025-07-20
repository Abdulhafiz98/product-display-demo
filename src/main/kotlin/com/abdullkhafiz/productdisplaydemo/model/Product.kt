package com.abdullkhafiz.productdisplaydemo.model

import java.time.LocalDateTime

data class Product(
    val id: Long,
    val title: String? = null,
    val vendor: String? = null,
    val productType: String? = null,
    val variants: List<Variant>? = null,
    val publishedAt: LocalDateTime? = null
) {
    data class Variant(
        val title: String? = null,
        val option1: String? = null,
        val option2: String? = null,
        val option3: String? = null,
        val taxable: Boolean? = null,
        val price: String? = null,
        val grams: Int? = null
    )
}