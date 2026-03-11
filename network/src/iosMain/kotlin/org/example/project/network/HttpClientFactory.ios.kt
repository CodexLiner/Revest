package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual object HttpClientFactory {
    actual fun create(): HttpClient = HttpClient(Darwin) {
        configureDefaults()
    }
}
