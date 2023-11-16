package com.github.boybeak.canvas._2d.geometry

import android.graphics.Path
import android.graphics.PointF
import android.graphics.RectF
import android.util.Log

class WebPath : Path() {

    companion object {
        private const val TAG = "WebPath"
        private val RECT_DIRECTION = arrayOf(Direction.CW, Direction.CCW)
    }

    private val anchorPointF = PointF()

    private var directionIndex = 0

    private val arcTo = ArcTo()

    fun arcTo(
        x1: Float,
        y1: Float,
        x2: Float,
        y2: Float,
        radius: Float
    ) {
        arcTo.set(anchorPointF.x, anchorPointF.y, x1, y1, x2, y2, radius)
        Log.d(TAG, "arcTo tangentPoint1=${arcTo.tangentPoint1}")
        lineTo(arcTo.tangentPoint1.x, arcTo.tangentPoint1.y)
        Log.d(TAG, "arcTo rect=${arcTo.rect} stDegrees=${arcTo.startDegrees} swDegrees=${arcTo.sweepDegrees}")
        arcTo(arcTo.rect, arcTo.startDegrees, arcTo.sweepDegrees, false)

    }

    override fun moveTo(x: Float, y: Float) {
        super.moveTo(x, y)
        anchorPointF.set(x, y)
    }

    override fun lineTo(x: Float, y: Float) {
        super.lineTo(x, y)
        anchorPointF.set(x, y)
    }

    override fun cubicTo(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) {
        super.cubicTo(x1, y1, x2, y2, x3, y3)
        anchorPointF.set(x3, y3)
    }

    override fun quadTo(x1: Float, y1: Float, x2: Float, y2: Float) {
        super.quadTo(x1, y1, x2, y2)
        anchorPointF.set(x2, y2)
    }

    fun addRect(rectF: RectF) {
        val direction = RECT_DIRECTION[directionIndex]
        super.addRect(rectF, direction)
        directionIndex = (directionIndex + 1) % RECT_DIRECTION.size
    }

    fun addRect(left: Float, top: Float, width: Float, height: Float) {
        val direction = RECT_DIRECTION[directionIndex]
        super.addRect(left, top, left + width, top + height, direction)
        directionIndex = (directionIndex + 1) % RECT_DIRECTION.size
    }

    override fun reset() {
        super.reset()
        directionIndex = 0
    }

    override fun close() {
        super.close()
        directionIndex = 0
    }

}