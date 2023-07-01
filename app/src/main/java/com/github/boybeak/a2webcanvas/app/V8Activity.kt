package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.github.boybeak.a2webcanvas.app.adapter.JsApiItem
import com.github.boybeak.a2webcanvas.app.game.GameExecutor
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Method

class V8Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "V8Activity"
    }

    private val canvasView by lazy { findViewById<V8WebCanvasView>(R.id.canvasView) }
    private val jsApiRv by lazy { findViewById<RecyclerView>(R.id.jsApiRv) }

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

        jsApiRv.adapter = AnyAdapter().apply {
            addAll(
                listOf(
                    JsApi2D("hello.js", "js/hello.js"),
                    JsApi2D("rect.js", "js/rect.js"),
                    JsApi2D("clock.js", "js/clock.js")
                )
            ) { s, _ ->
                JsApiItem(s)
            }
            setOnClickFor(JsApiItem::class.java, object : OnItemClick<JsApiItem>() {
                override fun onClick(
                    view: View,
                    item: JsApiItem,
                    position: Int,
                    adapter: AnyAdapter
                ) {
                    val code = item.getJsCode(view.context)
                    Log.d(TAG, "onClick code=$code")
                    canvasView.queueEvent {
                        v8?.executeScript(code)
                    }
                }
            })
        }
    }
}