package com.abdullkhafiz.productdisplaydemo.service

import com.abdullkhafiz.productdisplaydemo.model.CreateProductModel
import com.abdullkhafiz.productdisplaydemo.model.CreateVariantModel
import com.abdullkhafiz.productdisplaydemo.model.Product
import com.abdullkhafiz.productdisplaydemo.model.ProductsFromFamme
import com.abdullkhafiz.productdisplaydemo.repository.ProductRepository
import com.abdullkhafiz.productdisplaydemo.repository.model.ProductDO
import com.abdullkhafiz.productdisplaydemo.repository.model.VariantDO
import com.abdullkhafiz.productdisplaydemo.util.idGenerator
import com.abdullkhafiz.productdisplaydemo.util.toProductDO
import com.abdullkhafiz.productdisplaydemo.util.toVariantDO
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime

@Service
class ProductService(
    @Autowired
    private val productRepository: ProductRepository
) {

    @Value("\${fetchData.url}")
    private lateinit var url: String

    private val objectMapper = jacksonObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    private val restTemplate = RestTemplate()


    @Scheduled(initialDelay = 0, fixedRate = 60000)
    fun fetchAndSaveProducts() {
        if (productRepository.getProductsCount() > 0) {
            return
        }
        try {
            val response = restTemplate.getForObject(url, String::class.java)
            val productsFromFamme: ProductsFromFamme = objectMapper.readValue(response ?: "[]")

            productsFromFamme.products.take(20).forEach { productFromFamme ->
                val productDO = productFromFamme.toProductDO()
                productRepository.saveProduct(productDO)
                productFromFamme.variants?.forEach { variantFromFamme ->
                    val variantDO = variantFromFamme.toVariantDO(productDO.id)
                    productRepository.saveVariant(variantDO)
                }
            }
        } catch (ex: Exception) {
            println("Error fetching or saving products: ${ex.message}")
        }
    }

    fun getAllProducts(): List<Product> =
        productRepository.getAllProducts().sortedByDescending { it.publishedAt }

    fun getProductById(productId: Long): Product =
        productRepository.getProductWithVariantsById(productId)
            ?: throw Exception("Product not found")

    fun creteProduct(product: CreateProductModel) {
        val productId = product.id ?: idGenerator()
        val now = LocalDateTime.now()
        productRepository.saveProduct(
            ProductDO(
                productId,
                product.title,
                product.vendor,
                product.productType,
                now,
                now,
                now
            )
        )
    }

    fun creteVariant(productId: Long, variant: CreateVariantModel) {
        val variantId = variant.id ?: idGenerator()
        val now = LocalDateTime.now()
        productRepository.saveVariant(
            VariantDO(
                variantId,
                productId,
                variant.title,
                variant.option1,
                variant.option2,
                variant.option3,
                variant.taxable,
                variant.price,
                variant.grams,
                now,
                now
            )
        )
    }

    fun updateProduct(productId: Long, productModel: CreateProductModel) {
        val productDO = productRepository.getProductDOById(productId)
            ?: throw Exception("Product not found")
        productRepository.updateProduct(
            ProductDO(
                productDO.id,
                productModel.title,
                productModel.vendor,
                productModel.productType,
                productDO.publishedAt,
                productDO.createdAt,
                productDO.updatedAt
            )
        )
    }

    fun deleteProduct(productId: Long) =
        productRepository.deleteProductById(productId)
}