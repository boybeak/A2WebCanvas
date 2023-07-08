package com.github.boybeak.webcanvas.twod.gradient

import android.graphics.BitmapShader
import android.graphics.Shader
import android.graphics.Shader.TileMode
import com.github.boybeak.webcanvas.image.IWebImage
import java.lang.IllegalArgumentException

class CanvasPattern internal constructor(val image: IWebImage, private val repetition: String) : CanvasGradient() {
    override fun toShader(): Shader {
        // TODO("Effect not match")
        val (repeatX, repeatY) = when(repetition) {
            "repeat" -> Pair(TileMode.REPEAT, TileMode.REPEAT)
            "repeat-x" -> Pair(TileMode.REPEAT, TileMode.CLAMP)
            "repeat-y" -> Pair(TileMode.CLAMP, TileMode.REPEAT)
            "no-repeat" -> Pair(TileMode.CLAMP, TileMode.CLAMP)
            else -> throw IllegalArgumentException("Unsupported repetition=$repetition")
        }
        return BitmapShader(image.bitmap!!, repeatX, repeatY)
    }
}