package com.github.boybeak.a2webcanvas.app

import android.graphics.PointF
import com.github.boybeak.a2webcanvas.app.image.AssetImage
import com.github.boybeak.a2webcanvas.app.image.ResourceImage
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import kotlin.math.PI

class CanvasApis2D(private val canvas: WebCanvasView) {

    val context = canvas.context
    val density = canvas.context.resources.displayMetrics.density

    val apis = listOf(
        newFixedApi("draw a house") {
            lineWidth = 10F
            strokeRect(75F, 140F, 150F, 110F)
            fillRect(130F, 190F, 40F, 60F)

            beginPath()
            moveTo(50F, 140F)
            lineTo(150F, 60F)
            lineTo(250F, 140F)
            closePath()
            stroke()
        },
        newFixedApi("clearRect") {
            beginPath()
            fillStyle = "#ff6"
            fillRect(0F, 0F, canvas.width.toFloat(), canvas.height.toFloat())

            beginPath()
            fillStyle = "blue"
            moveTo(20F, 20F)
            lineTo(180F, 20F)
            lineTo(130F, 130F)
            closePath()
            fill()

            clearRect(10F, 10F, 120F, 100F)
        },
        newFixedApi("save and restore") {
            save()
            fillStyle = "green"
            fillRect(10F, 10F, 100F, 100F)
            restore()
            fillRect(150F, 40F, 100F, 100F)
        },
        newFixedApi("filter") {
            filter = "blur(4px)"
            font = "48px serif"
            fillText("Hello world", 50F, 100F)
        },
        newFixedApi("lineCap") {
            beginPath()
            moveTo(20F, 20F)
            lineWidth = 15F
            lineCap = "round"
            lineTo(100F, 100F)
            stroke()
        },
        newFixedApi("lineJoin") {
            lineWidth = 20F
            lineJoin = "round"
            beginPath()
            moveTo(20F, 20F)
            lineTo(190F, 100F)
            lineTo(280F, 20F)
            lineTo(280F, 150F)
            stroke()
        },
        newFixedApi("lineWidth") {
            lineWidth = 15F

            beginPath()
            moveTo(20F, 20F)
            lineTo(130F, 130F)
            stroke()
        },
        newFixedApi("fillText") {
            font = "50px serif"
            fillText("Hello world", 50F, 90F)
        },
        newFixedApi("font") {
            font = "bold 48px serif"
            strokeText("Hello world", 50F, 100F)
        },
        newFixedApi("shadow") {
            // Shadow
            shadowColor = "red"
            shadowOffsetX = 25F
            shadowBlur = 10F

            // Rectangle
            fillStyle = "blue"
            fillRect(20F, 20F, 150F, 100F)
        },
        newFixedApi("textAlign") {
            val x = canvas.width / 2F

            beginPath()
            moveTo(x, 0F)
            lineTo(x, canvas.height.toFloat())
            stroke()

            font = "30px serif"

            textAlign = "left"
            fillText("left-aligned", x, 40F)

            textAlign = "center"
            fillText("center-aligned", x, 85F)

            textAlign = "right"
            fillText("right-aligned", x, 130F)
        },
        newFixedApi("textBaseline") {
            val baselines = arrayOf(
                "top",
                "hanging",
                "middle",
                "alphabetic",
                "ideographic",
                "bottom"
            )
            font = "36px serif"
            strokeStyle = "red"

            baselines.forEachIndexed { index, s ->
                textBaseline = s
                val y = 75 + index * 75
                beginPath()
                moveTo(0F, y + 0.5F)
                lineTo(550F, y + 0.5F)
                stroke()
                fillText("Abcdefghijklmnop ($s)", 0F, y.toFloat())
            }
        },
        newFixedApi("beginPath") {
            // First path
            beginPath()
            strokeStyle = "blue"
            moveTo(20F, 20F)
            lineTo(200F, 20F)
            stroke()

            // Second path
            beginPath()
            strokeStyle = "green"
            moveTo(20F, 20F)
            lineTo(120F, 120F)
            stroke()
        },
        newFixedApi("arc") {
            // Draw shapes
            for (i in 0 until 4) {
                for (j in 0 until 3) {
                    beginPath()
                    val x = 25 + j * 50 // x coordinate
                    val y = 25 + i * 50 // y coordinate
                    val radius = 20 // Arc radius
                    val startAngle = 0 // Starting point on circle
                    val endAngle = Math.PI + (Math.PI * j) / 2 // End point on circle
                    val counterclockwise = i % 2 == 1 // Draw counterclockwise
        
                    arc(x.toFloat(), y.toFloat(), radius.toFloat(), startAngle.toFloat(), endAngle.toFloat(), counterclockwise)
        
                    if (i > 1) {
                        fill()
                    } else {
                        stroke()
                    }
                }
            }
        },
        newFixedApi("arcTo - 1") {
            val x0 = 200F
            val y0 = 20F
            val x1 = 200F
            val y1 = 130F
            val x2 = 50F
            val y2 = 20F
            val r = 40F

            // Tangential lines
            beginPath()
            strokeStyle = "gray"
            moveTo(x0, y0)
            lineTo(x1, y1)
            lineTo(x2, y2)
            stroke()


            // Arc
            beginPath()
            strokeStyle = "black"
            lineWidth = 5F
            // lineTo(200, 200)
            // lineTo(200, 200)
            moveTo(x0, y0)
            arcTo(x1, y1, x2, y2, r)
            stroke()

            // Start point
            beginPath()
            fillStyle = "blue"
            arc(x0, y0, 5F, 0F, (2 * PI).toFloat())
            fill()

            // Control points
            beginPath()
            fillStyle = "red"
            arc(x1, y1, 5F, 0F, (2 * PI).toFloat()) // Control point one
            arc(x2, y2, 5F, 0F, (2 * PI).toFloat()) // Control point two
            fill()
        },
        newFixedApi("arcTo - 2") {
            val p0 = PointF(230F, 20F)
            val p1 = PointF(90F, 130F)
            val p2 = PointF(20F, 20F)

            fun labelPoint(p: PointF) {
                val offset = 15
                fillText("(${p.x}, ${p.y})", p.x + offset, p.y + offset)
            }

            beginPath()
            moveTo(p0.x, p0.y)
            arcTo(p1.x, p1.y, p2.x, p2.y, 50F)
            lineTo(p2.x, p2.y)

            labelPoint(p0)
            labelPoint(p1)
            labelPoint(p2)

            stroke()
        },
        newFixedApi("arcTo - 3") {
            beginPath()
            moveTo(180F, 90F)
            arcTo(180F, 130F, 110F, 130F, 130F)
            lineTo(110F, 130F)
            stroke()        
        },
        newFixedApi("bezierCurveTo  - 1") {
            // Define the points as {x, y}
            val start = PointF(50F, 20F)
            val cp1 = PointF(230F, 30F)
            val cp2 = PointF(150F, 80F)
            val end = PointF(250F, 100F)

            // Cubic Bézier curve
            beginPath()
            moveTo(start.x, start.y)
            bezierCurveTo(cp1.x, cp1.y, cp2.x, cp2.y, end.x, end.y)
            stroke()

            // Start and end points
            fillStyle = "blue"
            beginPath()
            arc(start.x, start.y, 5F, 0F, (2 * PI).toFloat()) // Start point
            arc(end.x, end.y, 5F, 0F, (2 * PI).toFloat()) // End point
            fill()

            // Control points
            fillStyle = "red"
            beginPath()
            arc(cp1.x, cp1.y, 5F, 0F, (2 * PI).toFloat()) // Control point one
            arc(cp2.x, cp2.y, 5F, 0F, (2 * PI).toFloat()) // Control point two
            fill()
        },
        newFixedApi("bezierCurveTo - 2") {
            beginPath()
            moveTo(30F, 30F)
            bezierCurveTo(120F, 160F, 180F, 10F, 220F, 140F)
            stroke()
        },
        newFixedApi("quadraticCurveTo - 1") {
            // Quadratic Bézier curve
            beginPath()
            moveTo(50F, 20F)
            quadraticCurveTo(230F, 30F, 50F, 100F)
            stroke()

            // Start and end points
            fillStyle = "blue"
            beginPath()
            arc(50F, 20F, 5F, 0F, (2 * PI).toFloat()) // Start point
            arc(50F, 100F, 5F, 0F, (2 * PI).toFloat()) // End point
            fill()

            // Control point
            fillStyle = "red"
            beginPath()
            arc(230F, 30F, 5F, 0F, (2 * PI).toFloat())
            fill()
        },
        newFixedApi("quadraticCurveTo - 2") {
            beginPath()
            moveTo(20F, 110F)
            quadraticCurveTo(230F, 150F, 250F, 20F)
            stroke()
        },
        newFixedApi("closePath") {
            beginPath()
            moveTo(20F, 140F) // Move pen to bottom-left corner
            lineTo(120F, 10F) // Line to top corner
            lineTo(220F, 140F) // Line to bottom-right corner
            closePath() // Line to bottom-left corner
            stroke()
        },
        newFixedApi("measureText") {
            val hw = "Hello world"
            val tm = measureText(hw)
            fillText(hw, 100F, 40F)
            font = "40px"
            fillText("text width: ${tm.width}", 100F, 100F)
        },
        newFixedApi("scale") {
            strokeStyle = "red"
            strokeRect(0F, 0F, 50F, 50F)

            strokeStyle = "green"
            scale(2F, 2F)
            strokeRect(0F, 0F, 50F, 50F)

            strokeStyle = "blue"
            scale(2F, 2F)
            strokeRect(0F, 0F, 50F, 50F)
        },
        newFixedApi("rotate") {
            font = "50px"
            fillStyle = "red"
            fillText("Hello world", 100F, 100F)

            rotate((45F / 180 * PI).toFloat())

            fillStyle = "green"
            fillText("Hello world", 100F, 100F)
        },
        newFixedApi("translate") {
            strokeStyle = "red"
            strokeRect(0F, 0F, 100F, 100F)

            translate(50F, 50F)

            strokeStyle = "green"
            strokeRect(0F, 0F, 100F, 100F)
        },
        newFixedApi("drawImage - 1") {
            drawImage(ResourceImage(context, R.drawable.flower), 0, 0)
        },
        newFixedApi("drawImage - 2") {
            drawImage(AssetImage(context, "hippo.png"), 0, 0)
        }
    )

    private fun newFixedApi(name: String, onDraw: (CanvasRenderingContext2D.() -> Unit)): Api2D {
        return Api2D(name, newFixedOnDraw(onDraw))
    }
    private fun newFixedOnDraw(onDraw: (CanvasRenderingContext2D.() -> Unit)): CanvasRenderingContext2D.() -> Unit {
        return {
            scale(density, density)
            onDraw.invoke(this)
        }
    }
}