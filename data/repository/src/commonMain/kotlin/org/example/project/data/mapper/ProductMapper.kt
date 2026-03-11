package org.example.project.data.mapper

import org.example.project.data.dto.ProductDto
import org.example.project.models.Dimensions
import org.example.project.models.Meta
import org.example.project.models.Product
import org.example.project.models.Review

fun ProductDto.toDomain(): Product = Product(
    id = id,
    title = title,
    description = description,
    category = category.orEmpty(),
    price = price,
    discountPercentage = discountPercentage ?: 0.0,
    rating = rating,
    stock = stock ?: 0,
    tags = tags,
    brand = brand.orEmpty(),
    sku = sku.orEmpty(),
    weight = weight ?: 0.0,
    dimensions = dimensions?.let {
        Dimensions(
            width = it.width ?: 0.0,
            height = it.height ?: 0.0,
            depth = it.depth ?: 0.0
        )
    } ?: Dimensions(),
    warrantyInformation = warrantyInformation.orEmpty(),
    shippingInformation = shippingInformation.orEmpty(),
    availabilityStatus = availabilityStatus.orEmpty(),
    reviews = reviews.map {
        Review(
            rating = it.rating,
            comment = it.comment,
            date = it.date,
            reviewerName = it.reviewerName,
            reviewerEmail = it.reviewerEmail
        )
    },
    returnPolicy = returnPolicy.orEmpty(),
    minimumOrderQuantity = minimumOrderQuantity ?: 0,
    meta = meta?.let {
        Meta(
            createdAt = it.createdAt.orEmpty(),
            updatedAt = it.updatedAt.orEmpty(),
            barcode = it.barcode.orEmpty(),
            qrCode = it.qrCode.orEmpty()
        )
    } ?: Meta(),
    thumbnail = thumbnail,
    images = images
)