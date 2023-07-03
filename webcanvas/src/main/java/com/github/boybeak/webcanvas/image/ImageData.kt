package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.util.UUID

class ImageData(override val bitmap: Bitmap) : IWebImage {
    override val id: String = UUID.randomUUID().toString()
    val data: ByteBuffer by lazy {
        ByteBuffer.allocateDirect(bitmap.byteCount).also {
            bitmap.copyPixelsToBuffer(it)
        }
    }
}