package com.github.boybeak.webcanvas.twod.gradient

object CanvasGradientManager {

    private val gradientMap = HashMap<String, CanvasGradient>()

    fun createLinearGradient(x0: Float, y0: Float, x1: Float, y1: Float): LinearGradient {
        return LinearGradient(x0, y0, x1, y1).also {
            gradientMap[it.id] = it
        }
    }

    fun createRadialGradient(x0: Float, y0: Float, r0: Float, x1: Float, y1: Float, r1: Float): RadialGradient {
        return RadialGradient(x0, y0, r0, x1, y1, r1).also {
            gradientMap[it.id] = it
        }
    }

    fun getCanvasGradient(id: String): CanvasGradient {
        return gradientMap[id]!!
    }

    operator fun get(id: String): CanvasGradient {
        return getCanvasGradient(id)
    }
}