package com.github.boybeak.a2webcanvas.app.v8

import com.eclipsesource.v8.V8
import com.github.boybeak.a2webcanvas.app.game.GamePlayground

open class V8GamePlayground(name: String) : GamePlayground(name) {
    private var v8Inner: V8? = null
    val v8: V8 get() = v8Inner!!
    final override fun onPrepared() {
        v8Inner = V8.createV8Runtime()
        onPrepared(v8Inner!!)
    }
    open fun onPrepared(v8: V8) {}
    open fun v8GameRun(run: V8.() -> Unit) {
        gameRun {
            run.invoke(v8Inner!!)
        }
    }

    override fun stop() {
        v8GameRun {
            release(false)
            v8Inner = null
        }
        super.stop()
    }
}