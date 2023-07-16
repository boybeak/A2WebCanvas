package com.github.boybeak.a2webcanvas.app.game

import android.graphics.Color
import android.graphics.PointF
import com.github.boybeak.a2webcanvas.app.ext.context2DPost
import com.github.boybeak.canvas._2d.paint.Style
import com.github.boybeak.canvas.onscreen.WebCanvasOnscreen
import java.lang.IllegalArgumentException
import kotlin.random.Random

class StickGame(private val canvasView: WebCanvasOnscreen) : Runnable {

    private val stickContainer by lazy { StickContainer(10, canvasView.width, canvasView.height) }

    private val colorStyle = Style.ColorStyle(Color.BLACK)

    @Volatile
    private var finished = false
    var isRunning = false
        private set
    @Volatile
    var paused: Boolean = false

    fun start() {
        Thread(this).start()
    }

    fun pause() {
        paused = true
    }
    fun resume() {
        paused = false
    }
    fun finish() {
        finished = true
    }

    override fun run() {
        finished = false
        isRunning = true

        while (!finished) {
            canvasView.context2DPost {
                while (paused) {
                    continue
                }
                lineWidth = 10F
                lineCap = "round"
                for (stick in stickContainer.sticks) {
                    colorStyle.setColorStr(stick.colorStr)
//                    strokeStyle = colorStyle
                    beginPath()
                    moveTo(stick.p1.x, stick.p1.y)
                    lineTo(stick.p2.x, stick.p2.y)
                    closePath()

                    stroke()
                }
            }
            stickContainer.nextFrame()
            Thread.sleep(16)
        }
        isRunning = false
    }

    private class StickContainer(size: Int, val width: Int, val height: Int) {

        private val random = Random(System.currentTimeMillis())
        private val headerStick = HeaderStick(5, width, height)
        val sticks = Array<Stick>(size) {
            if (it == 0) {
                headerStick
            } else {
                Stick()
            }
        }
        fun nextFrame() {
            for (i in sticks.size - 1 downTo 1) {
                sticks[i].nextFrameFrom(sticks[i - 1])
            }
            headerStick.nextFrame(width, height)
        }
    }
    private open class Stick(val p1: PointF, val p2: PointF, var r: Int, var g: Int, var b: Int, var a: Float, var enabled: Boolean) {
        val colorStr get() = "rgba($r, $g, $b, $a)"
        constructor(): this(PointF(), PointF(), 0, 0, 0, 1F, false)
        fun nextFrameFrom(stick: Stick) {
            this.p1.set(stick.p1)
            this.p2.set(stick.p2)
            this.r = stick.r
            this.g = stick.g
            this.b = stick.b
            this.a = stick.a * 0.8F
            this.enabled = stick.enabled
        }
    }
    private class HeaderStick(
        val step: Int,
        p1: PointF,
        p2: PointF,
        r: Int,
        g: Int,
        b: Int,
        a: Float
    ) : Stick(p1, p2, r, g, b, a, true) {
        private val random = Random(System.currentTimeMillis())
        private val oneF get() = if (random.nextBoolean()) 1F else -1F
        private val d1 = PointF(oneF, oneF)
        private val d2 = PointF(oneF, oneF)

        private var deltaR = random.nextInt(4) + 1
        private var deltaG = random.nextInt(4) + 1
        private var deltaB = random.nextInt(4) + 1

        constructor(stepMax: Int, width: Int, height: Int): this(stepMax, PointF(), PointF(), 0, 0, 0, 1F) {
            p1.set(random.nextInt(width).toFloat(), random.nextInt(height).toFloat())
            p2.set(random.nextInt(width).toFloat(), random.nextInt(height).toFloat())
            r = random.nextInt(0xFF)
            g = random.nextInt(0xFF)
            b = random.nextInt(0xFF)
        }

        init {
            if (step <= 0) {
                throw IllegalArgumentException("stepMax must be > 0")
            }
        }
        
        fun nextFrame(width: Int, height: Int) {
            nextPosition(p1, d1, width, height)
            nextPosition(p2, d2, width, height)
            nextColor()
        }

        private fun nextPosition(point: PointF, delta: PointF, width: Int, height: Int) {
            var guessX = point.x + delta.x * step
            if (guessX < 0) {
                delta.x = -delta.x
                guessX = 0F
            } else if (guessX > width) {
                delta.x = -delta.x
                guessX = width.toFloat()
            }
            var guessY = point.y + delta.y * step
            if (guessY < 0) {
                delta.y = -delta.y
                guessY = 0F
            } else if (guessY > height) {
                delta.y = -delta.y
                guessY = height.toFloat()
            }
            point.set(guessX, guessY)
        }
        private fun nextColor() {
            var nextR = r + deltaR
            if (nextR < 0) {
                nextR = 0
                deltaR = -deltaR
            } else if (nextR > 0xFF) {
                nextR = 0xFF
                deltaR = -deltaR
            }

            var nextG = g + deltaG
            if (nextG < 0) {
                nextG = 0
                deltaG = -deltaG
            } else if (nextG > 0xFF) {
                nextG = 0xFF
                deltaG = -deltaG
            }

            var nextB = b + deltaB
            if (nextB < 0) {
                nextB = 0
                deltaB = -deltaB
            } else if (nextB > 0xFF) {
                nextB = 0xFF
                deltaB = -deltaB
            }

            r = nextR
            g = nextG
            b = nextB
        }

    }
}