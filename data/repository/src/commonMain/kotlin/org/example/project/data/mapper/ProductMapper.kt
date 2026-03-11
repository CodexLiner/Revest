package org.example.project.data.mapper

import org.example.project.data.dto.ProductDto
import org.example.project.models.Product

fun ProductDto.toDomain(): Product = Product(
    id = id,
    title = title,
    description = description,
    brand = brand.orEmpty(),
    price = price,
    rating = rating,
    thumbnail = thumbnail,
    images = images
)
