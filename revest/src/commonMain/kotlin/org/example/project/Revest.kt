package org.example.project

import org.example.project.catalog.Catalog
import org.example.project.di.initKoin
import org.koin.core.component.KoinComponent

fun initProductKit() {
    initKoin()
}

object Revest : KoinComponent {
    val catalog by lazy { Catalog() }
}
