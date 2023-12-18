package com.github.boybeak.canvas._2d.geometry

import android.graphics.PointF
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

class VectorF2D {

    var x: Float
        private set
    var y: Float
        private set

    private val normalizedPoint: PointF = PointF()
    private var normalizedInner: VectorF2D? = null
    val normalized: VectorF2D
        get() {
        if (normalizedInner == null) {
            normalizedInner = VectorF2D(normalizedPoint.x, normalizedPoint.y)
        }
        return normalizedInner!!
    }

    constructor(x: Float, y: Float) {
        this.x = x
        this.y = y
        update()
    }

    constructor(from: PointF, to: PointF): this(to.x - from.x, to.y - from.y)

    constructor(): this(0F, 0F)

    fun set(x: Float, y: Float) {
        if (this.x == x && this.y == y) {
            return
        }
        this.x = x
        this.y = y
        update()
    }

    fun set(from: PointF, to: PointF) {
        set(to.x - from.x, to.y - from.y)
    }

    private fun update() {
        if (isZero()) {
            normalizedPoint.set(0F, 0F)
        } else {
            normalizedPoint.set(x / module(), y / module())
        }
        normalizedInner = null
    }

    operator fun plus(vector: VectorF2D): VectorF2D {
        return VectorF2D(x + vector.x, y + vector.y)
    }

    operator fun minus(vector: VectorF2D): VectorF2D {
        return VectorF2D(x - vector.x, y - vector.y)
    }

    fun crossProduct(vector: VectorF2D): FloatArray {
        return floatArrayOf(0F, 0F, x * vector.y - vector.x * y)
    }

    fun innerProduct(vector: VectorF2D): Float {
        return x * vector.x + y * vector.y
    }

    fun plusTo(vector: VectorF2D, plusTo: VectorF2D) {
        plusTo.set(x + vector.x, y + vector.y)
    }

    fun minusTo(vector: VectorF2D, minusTo: VectorF2D) {
        minusTo.set(x - vector.x, y - vector.y)
    }

    fun angleMidWith(vector: VectorF2D): VectorF2D {
        return VectorF2D(normalizedPoint.x + vector.normalizedPoint.x,
            normalizedPoint.y + vector.normalizedPoint.y)
    }

    fun angleMidTo(vector: VectorF2D, angleMidTo: VectorF2D) {
        angleMidTo.set(normalizedPoint.x + vector.normalizedPoint.x,
            normalizedPoint.y + vector.normalizedPoint.y)
    }

    fun isZero(): Boolean {
        return x == 0F && y == 0F
    }

    fun module(): Float {
        return sqrt(x.pow(2) + y.pow(2))
    }

    fun radianWith(vector: VectorF2D): Float {
        val cos = innerProduct(vector) / (module() * vector.module())
        return acos(cos)
    }
    fun degreesWith(vector: VectorF2D): Float {
        return Math.toDegrees(radianWith(vector).toDouble()).toFloat()
    }

    override fun toString(): String {
        return "VectorF2D(x=$x, y=$y)"
    }

}