package org.example.project.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.network.NetworkClient
import org.jetbrains.skia.Image

@Composable
actual fun NetworkImage(
    url: String,
    modifier: Modifier,
    contentScale: ContentScale
) {
    val imageState = remember(url) { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(url) {
        imageState.value = runCatching {
            val bytes: ByteArray = NetworkClient.client.get(url).body()
            Image.makeFromEncoded(bytes).toComposeImageBitmap()
        }.getOrNull()
    }

    if (imageState.value != null) {
        Image(
            bitmap = imageState.value!!,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale
        )
    } else {
        Box(
            modifier = modifier.background(Color.LightGray)
        )
    }
}
