package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap

interface ISrcDecoder {
    fun decode(src: String?, callback: Callback)

    interface Callback {
        fun onLoad(bitmap: Bitmap)
        fun onError(e: Throwable)
    }
}