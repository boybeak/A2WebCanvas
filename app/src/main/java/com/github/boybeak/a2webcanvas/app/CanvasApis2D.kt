package com.github.boybeak.a2webcanvas.app

import com.github.boybeak.webcanvas.WebCanvasView

class CanvasApis2D(private val canvasView: WebCanvasView) {
    val apis = listOf(
        Api2D("draw a house") {
            strokeRect(75F, 140F, 150F, 110F)
            fillRect(130F, 190F, 40F, 60F)

            beginPath()
            moveTo(50F, 140F)
            lineTo(150F, 60F)
            lineTo(250F, 140F)
            closePath()
            stroke()
        },
        Api2D("clearRect") {
            beginPath()
            fillStyle = "#ff6"
            fillRect(0F, 0F, canvasView.width.toFloat(), canvasView.height.toFloat())

            beginPath()
            fillStyle = "blue"
            moveTo(20F, 20F)
            lineTo(180F, 20F)
            lineTo(130F, 130F)
            closePath()
            fill()

            clearRect(10F, 10F, 120F, 100F)
        },
        Api2D("save and restore") {
            save()
            fillStyle = "green"
            fillRect(10F, 10F, 100F, 100F)
            restore()
            fillRect(150F, 40F, 100F, 100F)
        },
        Api2D("lineCap") {
            beginPath()
            moveTo(20F, 20F)
            lineWidth = 15F
            lineCap = "round"
            lineTo(100F, 100F)
            stroke()
        },
        Api2D("lineJoin") {
            lineWidth = 20F
            lineJoin = "round"
            beginPath()
            moveTo(20F, 20F)
            lineTo(190F, 100F)
            lineTo(280F, 20F)
            lineTo(280F, 150F)
            stroke()
        },
        Api2D("lineWidth") {
            lineWidth = 15F

            beginPath()
            moveTo(20F, 20F)
            lineTo(130F, 130F)
            stroke()
        },
        Api2D("fillText") {
            font = "50px serif"
            fillText("Hello world", 50F, 90F)
        },
        Api2D("font") {
            font = "bold 48px serif"
            strokeText("Hello world", 50F, 100F)
        }
    )
}