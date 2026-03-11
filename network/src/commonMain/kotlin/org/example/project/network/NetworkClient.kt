package org.example.project.network

import io.ktor.client.HttpClient

object NetworkClient {
    private val httpClient: HttpClient by lazy { HttpClientFactory.create() }

    val client: HttpClient
        get() = httpClient
}
