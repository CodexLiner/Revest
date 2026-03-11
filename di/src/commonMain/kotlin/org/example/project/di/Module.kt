package org.example.project.di

import org.koin.core.context.startKoin
import org.example.project.data.repositoryModule
import org.example.project.domain.domainModule

val appModule = listOf(
    repositoryModule,
    domainModule
)

fun initKoin() {
    runCatching {
        startKoin {
            modules(appModule)
        }
    }
}
