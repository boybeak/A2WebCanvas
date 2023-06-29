package com.github.boybeak.webcanvas.twod.geometry

import android.graphics.PointF
import android.graphics.RectF
import kotlin.math.sin
import kotlin.math.tan

/**
 * make a 2d coordinate system based on control point 1 as the origin point.
 * https://developer.mozilla.org/en-US/docs/Web/API/CanvasRenderingContext2D/arcTo
 */
class ArcTo() {

    companion object {
        private const val TAG = "ArcToF"
    }

    /**
     * Known points and lines
     */
    private val sPoint = PointF()   // start point
    private val oPoint = PointF()   // control point 1
    private val ePoint = PointF()   // control point 2

    private val osVec = VectorF2D() // O-S vector
    private val oeVec = VectorF2D()  // O-E vector

    private val cPoint = PointF()       // center point of the circle
    private val ocVec = VectorF2D()  // O-C vector

    private val ct1Vec = VectorF2D()
    private val ct2Vec = VectorF2D()

    /**
     * Intermediate product
     */

    /**
     * Make the cPoint as origin point to a new 2d coordinate,
     * to calculate the start degrees
     */
    private val cxpVec = VectorF2D()

    /**
     * Result fields
     */
    val rect: RectF = RectF()
    val tangentPoint1 = PointF()
    val tangentPoint2 = PointF()

    var startDegrees: Float = 0F
    var sweepDegrees: Float = 0F
        private set

    fun set(x0: Float, y0: Float, x1: Float, y1: Float, x2: Float, y2: Float, radius: Float) {
        sPoint.set(x0, y0)
        oPoint.set(x1, y1)
        ePoint.set(x2, y2)

        osVec.set(oPoint, sPoint)
        oeVec.set(oPoint, ePoint)

        val angleSoE = osVec.radianWith(oeVec)
        val angleSoC = angleSoE / 2

        val ocLen = radius / sin(angleSoC)

        osVec.angleMidTo(oeVec, ocVec)

        cPoint.set(oPoint.x + ocVec.normalized.x * ocLen, oPoint.y + ocVec.normalized.y * ocLen)
        cxpVec.set(cPoint.x + 1, cPoint.y)

        rect.set(cPoint.x - radius, cPoint.y - radius, cPoint.x + radius, cPoint.y + radius)

        // the distance between oPoint and tangentPoint1
        // distance from o-point to tangentPoint2 is the same value
        val otLen = radius / tan(angleSoC)
        tangentPoint1.set(oPoint.x + osVec.normalized.x * otLen, oPoint.y + osVec.normalized.y * otLen)
        tangentPoint2.set(oPoint.x + oeVec.normalized.x * otLen, oPoint.y + oeVec.normalized.y * otLen)

        ct1Vec.set(cPoint, tangentPoint1)
        ct2Vec.set(cPoint, tangentPoint2)
        sweepDegrees = ct1Vec.degreesWith(ct2Vec)

        startDegrees = ct1Vec.degreesWith(cxpVec)

    }

}