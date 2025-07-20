package com.abdullkhafiz.productdisplaydemo.repository.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class ProductDO(
    val id: Long,
    val title: String? = null,
    val vendor: String? = null,
    @JsonProperty("product_type")
    val productType: String? = null,
    @JsonProperty("published_at")
    val publishedAt: LocalDateTime? = null,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime? = null,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime? = null
)