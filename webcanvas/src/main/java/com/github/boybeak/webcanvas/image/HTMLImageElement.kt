package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap

class HTMLImageElement internal constructor(private val decoder: ISrcDecoder): AbsWebImage() {

    var src: String? = null
        set(value) {
            field = value
            bitmap = decoder.decode(value)
        }

    override var bitmap: Bitmap? = null
        private set
}