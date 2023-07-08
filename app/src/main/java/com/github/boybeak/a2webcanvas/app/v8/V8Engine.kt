package com.github.boybeak.a2webcanvas.app.v8

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import com.eclipsesource.v8.V8

typealias OnPrepareListener = V8.() -> Unit
typealias OnResetListener = V8.() -> Unit

class V8Engine {
    private var v8Inner: V8? = null
    val v8: V8 get() = v8Inner!!
    private val v8Thread by lazy {
        object : HandlerThread("v8-thread") {
            override fun onLooperPrepared() {
                super.onLooperPrepared()
                v8Inner = V8.createV8Runtime()
                onPrepareListener?.invoke(v8)
                onPrepareListener = null
            }
        }
    }
    val v8Handler: Handler by lazy {
        if (!v8Thread.isAlive) {
            v8Thread.start()
        }
        Handler(v8Thread.looper)
    }
    val v8Looper: Looper by lazy {
        if (!v8Thread.isAlive) {
            v8Thread.start()
        }
        v8Thread.looper
    }

    private var onPrepareListener: OnPrepareListener? = null

    fun onV8Prepared(onPrepare: OnPrepareListener) {
        onPrepareListener = onPrepare
    }
    fun v8Run(block: V8.() -> Unit) {
        v8Handler.post {
            block.invoke(v8)
        }
    }
    fun reset(onReset: OnResetListener? = null) {
        v8Run {
            keys.forEach {
                this.addUndefined(it)
            }
            onReset?.invoke(this)
        }
    }
}