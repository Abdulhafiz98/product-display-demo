package com.abdullkhafiz.productdisplaydemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class ProductDisplayDemoApplication

fun main(args: Array<String>) {
    runApplication<ProductDisplayDemoApplication>(*args)
}
