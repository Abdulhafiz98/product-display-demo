package com.abdullkhafiz.productdisplaydemo.repository

import com.abdullkhafiz.productdisplaydemo.model.Product
import com.abdullkhafiz.productdisplaydemo.repository.model.ProductDO
import com.abdullkhafiz.productdisplaydemo.repository.model.VariantDO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Timestamp
import kotlin.collections.component1
import kotlin.collections.component2

@Repository
class ProductRepository {

    @Value("\${spring.datasource.url}")
    private lateinit var url: String

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String

    private val connection: Connection
        get() = DriverManager.getConnection(url, username, password)

    fun getProductsCount(): Long {
        val sql = """SELECT COUNT(*) FROM product""".trimIndent()

        connection.use { it ->
            it.prepareStatement(sql).use { statement ->
                statement.executeQuery().use { resultSet ->
                    return if (resultSet.next()) resultSet.getLong(1) else 0L
                }
            }
        }
    }

    fun getAllProducts(): List<Product> {
        val sql = """
                SELECT p.id as product_id, 
                       p.title as product_title, 
                       p.vendor,  
                       p.product_type,
                       v.id as variant_id,
                       v.title as variant_title,
                       v.option1,
                       v.option2,
                       v.option3,
                       v.taxable,
                       v.price,
                       v.grams,
                       p.published_at,
                       p.created_at,
                       p.updated_at
                FROM product p
                LEFT JOIN variant v ON p.id = v.product_id
                    """.trimIndent()

        val rows = mutableListOf<Map<String, Any?>>()

        connection.use { it ->
            it.prepareStatement(sql).use { statement ->
                statement.executeQuery().use { resultSet ->
                    val meta = resultSet.metaData
                    while (resultSet.next()) {
                        val row = mutableMapOf<String, Any?>()
                        for (i in 1..meta.columnCount) {
                            row[meta.getColumnLabel(i)] = resultSet.getObject(i)
                        }
                        rows.add(row)
                    }
                }
            }
        }

        return mapToProductList(rows)
    }

    fun getProductWithVariantsById(id: Long): Product? {
        val sql = """
                SELECT p.id as product_id, 
                       p.title as product_title, 
                       p.vendor,  
                       p.product_type,
                       v.id as variant_id,
                       v.title as variant_title,
                       v.option1,
                       v.option2,
                       v.option3,
                       v.taxable,
                       v.price,
                       v.grams,
                       p.published_at,
                       p.created_at,
                       p.updated_at
                FROM product p
                LEFT JOIN variant v ON p.id = v.product_id
                WHERE p.id = :id
                    """.trimIndent()

        val rows = mutableListOf<Map<String, Any?>>()

        connection.use { it ->
            it.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                statement.executeQuery().use { resultSet ->
                    val meta = resultSet.metaData
                    while (resultSet.next()) {
                        val row = mutableMapOf<String, Any?>()
                        for (i in 1..meta.columnCount) {
                            row[meta.getColumnLabel(i)] = resultSet.getObject(i)
                        }
                        rows.add(row)
                    }
                }
            }
        }

        return mapToProductList(rows).firstOrNull()
    }

    fun getProductDOById(id: Long): ProductDO? {
        val sql = """
                SELECT p.id, 
                       p.title, 
                       p.vendor,  
                       p.product_type,
                       p.published_at,
                       p.created_at,
                       p.updated_at,
                FROM product p
                WHERE p.id = :id
                    """.trimIndent()

        val rows = mutableListOf<Map<String, Any?>>()

        connection.use { it ->
            it.prepareStatement(sql).use { statement ->
                statement.setLong(1, id)
                statement.executeQuery().use { resultSet ->
                    val meta = resultSet.metaData
                    while (resultSet.next()) {
                        val row = mutableMapOf<String, Any?>()
                        for (i in 1..meta.columnCount) {
                            row[meta.getColumnLabel(i)] = resultSet.getObject(i)
                        }
                        rows.add(row)
                    }
                }
            }
        }

        return mapToProductDO(rows.first())
    }


    fun saveProduct(product: ProductDO) {
        val insertProductSql = """
        INSERT INTO product (
            id, title, vendor, product_type, published_at, created_at, updated_at
        ) VALUES (?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        connection.use { it ->
            it.prepareStatement(insertProductSql).use { statement ->
                statement.setLong(1, product.id)
                statement.setString(2, product.title)
                statement.setString(3, product.vendor)
                statement.setString(4, product.productType)
                statement.setTimestamp(5, Timestamp.valueOf(product.publishedAt))
                statement.setTimestamp(6, Timestamp.valueOf(product.createdAt))
                statement.setTimestamp(7, Timestamp.valueOf(product.updatedAt))
                statement.executeUpdate() }
        }
    }

    fun updateProduct(product: ProductDO) {
        val updateProductSql = """
        UPDATE product SET
            title = ?,
            vendor = ?,
            product_type = ?,
            published_at = ?,
            created_at = ?,
            updated_at = ?
        WHERE id = ?
    """.trimIndent()

        connection.use { it ->
            it.prepareStatement(updateProductSql).use { statement ->
                statement.setLong(1, product.id)
                statement.setString(2, product.title)
                statement.setString(3, product.vendor)
                statement.setString(4, product.productType)
                statement.setTimestamp(5, Timestamp.valueOf(product.publishedAt))
                statement.setTimestamp(6, Timestamp.valueOf(product.createdAt))
                statement.setTimestamp(7, Timestamp.valueOf(product.updatedAt))
                statement.executeUpdate()
            }
        }
    }

    fun deleteProductById(id: Long) {
        val deleteProductSql = "DELETE FROM product WHERE id = ?"

        connection.use { it ->
            it.prepareStatement(deleteProductSql).use { statement ->
                statement.setLong(1, id)
                statement.executeUpdate()
            }
        }
    }

    fun saveVariant(variant: VariantDO) {

        val insertVariantSql = """
        INSERT INTO variant (
            id, product_id, title, option1, option2, option3, taxable, price, grams, created_at, updated_at
        ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """.trimIndent()

        connection.use { it ->
            it.prepareStatement(insertVariantSql).use { statement ->
                statement.setLong(1, variant.id)
                statement.setLong(2, variant.productId)
                statement.setString(3, variant.title)
                statement.setString(4, variant.option1)
                statement.setString(5, variant.option2)
                statement.setString(6, variant.option3)
                statement.setBoolean(7, variant.taxable ?: false)
                statement.setString(8, variant.price)
                statement.setInt(9, variant.grams ?: 0)
                statement.setTimestamp(10, Timestamp.valueOf(variant.createdAt))
                statement.setTimestamp(11, Timestamp.valueOf(variant.updatedAt))
                statement.executeUpdate()
            }
        }

    }

    fun mapToProductList(rows: List<Map<String, Any?>>): List<Product> {
        return rows
            .groupBy { it["product_id"] as Long }
            .map { (productId, groupedRows) ->
                val first = groupedRows.first()

                Product(
                    id = productId,
                    title = first["product_title"] as? String,
                    vendor = first["vendor"] as? String,
                    productType = first["product_type"] as? String,
                    publishedAt = (first["published_at"] as? Timestamp)?.toLocalDateTime(),
                    variants = groupedRows
                        .filter { it["variant_id"] != null }
                        .map { row ->
                            Product.Variant(
                                title = row["variant_title"] as? String,
                                option1 = row["option1"] as? String,
                                option2 = row["option2"] as? String,
                                option3 = row["option3"] as? String,
                                taxable = row["taxable"] as? Boolean,
                                price = row["price"] as? String,
                                grams = row["grams"] as? Int,
                            )
                        }
                )
            }
    }

    fun mapToProductDO(row: Map<String, Any?>): ProductDO {
        return ProductDO(
            id = row["id"] as Long,
            title = row["title"] as String,
            vendor = row["vendor"] as String,
            productType = row["product_type"] as String,
            publishedAt = (row["published_at"] as Timestamp).toLocalDateTime(),
            createdAt = (row["created_at"] as Timestamp).toLocalDateTime(),
            updatedAt = (row["updated_at"] as Timestamp).toLocalDateTime(),
        )
    }
}