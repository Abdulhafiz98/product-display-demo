package com.abdullkhafiz.productdisplaydemo.controller

import com.abdullkhafiz.productdisplaydemo.model.CreateProductModel
import com.abdullkhafiz.productdisplaydemo.model.CreateVariantModel
import com.abdullkhafiz.productdisplaydemo.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("")
class ProductController(
    @Autowired
    private val productService: ProductService
) {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @GetMapping("/products")
    fun getProducts(model: Model): String {
        model.addAttribute("products", productService.getAllProducts())
        return "index :: productContainer"
    }

    @GetMapping("/product-form")
    fun productForm(model: Model): String {
        model.addAttribute("product", null)
        return "index :: productModal"
    }

    @GetMapping("/product-form/{id}")
    fun editProductForm(@PathVariable id: Long, model: Model): String {
        model.addAttribute("product", productService.getProductById(id))
        return "index :: productModal"
    }


    @PostMapping("/products/create")
    fun createProduct(@ModelAttribute product: CreateProductModel, model: Model): String {
        productService.creteProduct(product)
        model.addAttribute("products", productService.getAllProducts())
        return index(model)
    }

    @PostMapping("/products/update/{id}")
    fun updateProduct(@PathVariable id: Long, @ModelAttribute product: CreateProductModel, model: Model): String {
        productService.updateProduct(id, product)
        model.addAttribute("products", productService.getAllProducts())
        return index(model)
    }

    @GetMapping("/variant-form/{id}")
    fun variantForm(
        @PathVariable("id") productId: Long,
        model: Model
    ): String {
        model.addAttribute("productId", productId)
        return "index :: variantModal"
    }

    @PostMapping("/products/{id}/variants/create")
    fun createProductVariant(
        @PathVariable("id") productId: String,
        @ModelAttribute variant: CreateVariantModel,
        model: Model
    ): String {
        productService.creteVariant(productId.toLong(), variant)
        model.addAttribute("products", productService.getAllProducts())
        return index(model)
    }

    @GetMapping("/products/delete-confirm/{id}")
    fun deleteForm(@PathVariable("id") productId: Long, model: Model): String {
        model.addAttribute("productId", productId)
        return "index :: deleteModal"
    }

    @PostMapping("/products/delete/{id}")
    fun deleteProduct(@PathVariable id: Long, model: Model): String {
        productService.deleteProduct(id)
        model.addAttribute("products", productService.getAllProducts())
        return index(model)
    }
}
