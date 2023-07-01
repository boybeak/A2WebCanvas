package com.github.boybeak.webcanvas.image

object WebImageManager {

    private const val TAG = "WebImageManager"

    private val imageMap = HashMap<String, IWebImage>()

    fun createHTMLImageElement(decoder: ISrcDecoder): HTMLImageElement {
        return HTMLImageElement(decoder).also {
            imageMap[it.id] = it
        }
    }

    fun getIWebImage(id: String): IWebImage {
        return imageMap[id]!!
    }

    operator fun get(id: String): IWebImage = imageMap[id]!!

}