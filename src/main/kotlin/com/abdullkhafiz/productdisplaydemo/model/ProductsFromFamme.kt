package com.abdullkhafiz.productdisplaydemo.model

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductsFromFamme(
    val products: List<ProductFromFamme> = listOf(),
) {
    data class ProductFromFamme(
        val id: Long? = null,
        val title: String? = null,
        val vendor: String? = null,
        @JsonProperty("product_type")
        val productType: String? = null,
        val variants: List<VariantFromFamme>? = null,
        @JsonProperty("published_at")
        val publishedAt: String? = null,
        @JsonProperty("created_at")
        val createdAt: String? = null,
        @JsonProperty("updated_at")
        val updatedAt: String? = null,
    ) {
        data class VariantFromFamme(
            val id: Long? = null,
            val title: String? = null,
            val option1: String? = null,
            val option2: String? = null,
            val option3: String? = null,
            val taxable: Boolean? = null,
            val price: String? = null,
            val grams: Int? = null,
            @JsonProperty("created_at")
            val createdAt: String? = null,
            @JsonProperty("updated_at")
            val updatedAt: String? = null,
        )
    }
}
