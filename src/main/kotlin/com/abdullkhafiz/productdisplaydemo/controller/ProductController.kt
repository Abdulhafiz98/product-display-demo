package com.abdullkhafiz.productdisplaydemo.controller

import com.abdullkhafiz.productdisplaydemo.model.CreateProductModel
import com.abdullkhafiz.productdisplaydemo.model.CreateVariantModel
import com.abdullkhafiz.productdisplaydemo.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
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
        model.addAttribute("products", productService.getAllProducts())
        return "index"
    }

    @PostMapping("/products/create")
    fun createProduct(@ModelAttribute product: CreateProductModel): String {
        productService.creteProduct(product)
        return "redirect:/"
    }

    @PostMapping("/products/{id}/variants/create")
    fun createProductVariant(
        @PathVariable("id") productId: Long,
        @ModelAttribute variant: CreateVariantModel
    ): String {
        productService.creteVariant(productId, variant)
        return "redirect:/"
    }

    @GetMapping("/product-form")
    fun productForm(model: Model): String {
        model.addAttribute("product", null)
        return "index :: productModal"
    }

    @GetMapping("/product/edit/{id}")
    fun editProductForm(@PathVariable id: Long, model: Model): String {
        model.addAttribute("product", productService.getProductById(id))
        return "index :: productModal"
    }

    @PostMapping("/products/update/{id}")
    fun updateProduct(@PathVariable id: Long, @ModelAttribute product: CreateProductModel): String {
        productService.updateProduct(id, product)
        return "redirect:/"
    }

    @DeleteMapping("/products/delete/{id}")
    fun deleteProduct(@PathVariable id: Long, model: Model): String {
        productService.deleteProduct(id)
        model.addAttribute("products", productService.getAllProducts())
        return "index :: productTable"
    }
}
