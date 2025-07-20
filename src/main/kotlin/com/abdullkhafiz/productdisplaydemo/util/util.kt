package com.abdullkhafiz.productdisplaydemo.util

import com.abdullkhafiz.productdisplaydemo.model.ProductsFromFamme
import com.abdullkhafiz.productdisplaydemo.repository.model.ProductDO
import com.abdullkhafiz.productdisplaydemo.repository.model.VariantDO
import java.time.LocalDateTime
import java.time.OffsetDateTime

fun ProductsFromFamme.ProductFromFamme.toProductDO()=
    ProductDO(
        id ?: idGenerator(),
        title,
        vendor,
        productType,
        publishedAt?.dateTimeParser(),
        createdAt?.dateTimeParser(),
        updatedAt?.dateTimeParser(),
    )


fun ProductsFromFamme.ProductFromFamme.VariantFromFamme.toVariantDO(productId: Long) =
    VariantDO(
        id ?: idGenerator(),
        productId,
        title,
        option1,
        option2,
        option3,
        taxable,
        price,
        grams,
        createdAt?.dateTimeParser(),
        updatedAt?.dateTimeParser(),
    )

fun String.dateTimeParser(): LocalDateTime {
    val offsetDateTime = OffsetDateTime.parse(this)
    return offsetDateTime.toLocalDateTime()
}

fun idGenerator(): Long =
    (1..1_000_000).random().toLong()