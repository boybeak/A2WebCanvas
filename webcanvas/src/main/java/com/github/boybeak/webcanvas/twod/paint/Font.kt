package com.github.boybeak.webcanvas.twod.paint

import android.graphics.Paint
import android.graphics.Typeface

class Font private constructor(val textSize: Float, val typeface: Typeface) {
    companion object {
        val textSizeRegex = Regex("\\d+[.\\d+]?\\w+")
        val textSizeNumRegex = Regex("\\d+[.\\d+]?")
        private val defaultTextSizePx = Paint().textSize
        fun from(font: String): Font {
            var textSizeInPx = defaultTextSizePx
            textSizeRegex.find(font)?.run {
                val textSizeNum = textSizeNumRegex.find(this.value)!!.value.toFloat()
                val unit = textSizeNumRegex.replace(this.value, "")

                textSizeInPx = when(unit.toLowerCase()) {
                    "px" -> {
                        textSizeNum/* * pixelRatio*/
                    }
                    else -> { throw IllegalArgumentException("Unknown text unit($unit) in Font#from method.") }
                }
            }

            val isItalic = font.contains("italic", ignoreCase = true)
            val isBold = font.contains("bold", ignoreCase = true)

            val typeface = if (isItalic && isBold) {
                Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD_ITALIC)
            } else if (isBold) {
                Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
            } else if (isItalic) {
                Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC)
            } else {
                Typeface.SANS_SERIF
            }

            return Font(textSizeInPx, typeface)
        }
    }
}