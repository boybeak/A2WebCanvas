package com.github.boybeak.a2webcanvas.app.ext

fun <T> T?.or(create: () -> T): T {
    return this ?: create()
}