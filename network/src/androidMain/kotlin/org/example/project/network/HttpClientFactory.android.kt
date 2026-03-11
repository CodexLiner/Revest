package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

actual object HttpClientFactory {
    actual fun create(): HttpClient = HttpClient(OkHttp) {
        configureDefaults()
    }
}
