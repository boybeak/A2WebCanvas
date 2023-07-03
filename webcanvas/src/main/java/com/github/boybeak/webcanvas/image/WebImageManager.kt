package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap

object WebImageManager {

    private const val TAG = "WebImageManager"

    private val imageMap = HashMap<String, IWebImage>()

    fun createHTMLImageElement(decoder: ISrcDecoder): HTMLImageElement {
        return HTMLImageElement(decoder).also {
            imageMap[it.id] = it
        }
    }

    fun createImageData(bitmap: Bitmap): ImageData {
        return ImageData(bitmap).also {
            imageMap[it.id] = it
        }
    }

    fun <T : IWebImage> getIWebImage(id: String): T {
        return imageMap[id]!! as T
    }

    operator fun get(id: String): IWebImage = imageMap[id]!!

}