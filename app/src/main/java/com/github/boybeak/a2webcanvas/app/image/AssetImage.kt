package com.github.boybeak.a2webcanvas.app.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.boybeak.webcanvas.image.IWebImage

class AssetImage(context: Context, path: String) : IWebImage {
    override val bitmap: Bitmap = context.assets.open(path).run {
        val bmp = BitmapFactory.decodeStream(this)
        this.close()
        bmp
    }
}