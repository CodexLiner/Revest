package org.example.project

import org.koin.core.component.KoinComponent
import org.example.project.catalog.Catalog
import org.example.project.di.initKoin

fun initProductKit() {
    initKoin()
}

object ProductKit : KoinComponent {
    val catalog by lazy { Catalog() }
}
