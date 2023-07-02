package com.github.boybeak.a2webcanvas.app.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes
import com.github.boybeak.webcanvas.image.IWebImage

class ResourceImage(context: Context, @DrawableRes resId: Int) : IWebImage {
    override val id: String
        get() = hashCode().toString()
    override val bitmap: Bitmap? = BitmapFactory.decodeResource(context.resources, resId)
}