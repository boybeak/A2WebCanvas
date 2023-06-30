package com.github.boybeak.webcanvas.twod.image

import android.graphics.Bitmap

interface IWebImage {
    val bitmap: Bitmap?
    val width: Int get() = bitmap?.width ?: 0
    val height: Int get() = bitmap?.height ?: 0
}