package com.github.boybeak.webcanvas.image

import android.graphics.Bitmap

interface ISrcDecoder {
    fun decode(src: String?): Bitmap?
}