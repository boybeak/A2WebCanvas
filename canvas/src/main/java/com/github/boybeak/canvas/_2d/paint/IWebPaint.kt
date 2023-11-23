package com.github.boybeak.canvas._2d.paint

interface IWebPaint {
    var fillStyle: Style
    var strokeStyle: Style

    var filter: String?

    var font: String

    var globalAlpha: Float
    var globalCompositeOperation: String

    var lineCap: String
    var lineDash: LineDash?
    var lineJoin: String
    var lineWidth: Float

    var miterLimit: Float

    var shadowBlur: Float
    var shadowColor: String?
    var shadowOffsetX: Float
    var shadowOffsetY: Float

    var textAlign: String
    var textBaseline: String
}