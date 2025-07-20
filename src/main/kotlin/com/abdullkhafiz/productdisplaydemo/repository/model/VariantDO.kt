package com.abdullkhafiz.productdisplaydemo.repository.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class VariantDO(
    val id: Long,
    val productId: Long,
    val title: String? = null,
    val taxable: Boolean? = null,
    val price: String? = null,
    val grams: Int? = null,
    @JsonProperty("created_at")
    val createdAt: LocalDateTime? = null,
    @JsonProperty("updated_at")
    val updatedAt: LocalDateTime? = null
)
