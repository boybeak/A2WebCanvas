package com.github.boybeak.canvas.image

import android.graphics.Bitmap

interface IWebImage {
    val id: String
    val bitmap: Bitmap?
    val width: Int get() = bitmap?.width ?: 0
    val height: Int get() = bitmap?.height ?: 0
}