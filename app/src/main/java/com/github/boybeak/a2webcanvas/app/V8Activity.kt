package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.a2webcanvas.app.game.GameExecutor
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.manager
import com.github.boybeak.v8x.ext.registerV8Methods
import com.github.boybeak.webcanvas.WebCanvasView

class V8Activity : AppCompatActivity() {

    private val canvasView by lazy { findViewById<V8WebCanvasView>(R.id.canvasView) }
    private var v8: V8? = null

    private val console = object : V8Binding {

        private val tag = "V8Console"

        override fun getBindingId(): String {
            return this.hashCode().toString()
        }

        override fun onBindingChanged(
            target: V8Object,
            fieldInfo: Key,
            newValue: Any?,
            oldValue: Any?
        ) {}

        @V8Method
        fun log(vararg args: Any) {
            Log.d(tag, args.joinToString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v8)

        canvasView.queueEvent {
            v8 = V8.createV8Runtime()
            canvasView.initialize(v8!!)
            v8?.add("canvas", canvasView.getMyBinding(v8!!))
            v8?.add("Console", console.getMyBinding(v8!!))

            v8?.executeScript("Console.log('Hello JS');")
        }
    }
}