package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Function
import com.eclipsesource.v8.V8Object
import com.github.boybeak.a2webcanvas.app.adapter.JsApiItem
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.V8BindingAdapter
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.guessName
import com.github.boybeak.webcanvas.IWebCanvas
import java.io.File

class V8Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "V8Activity"
    }

    private val canvasView by lazy { findViewById<V8WebCanvasView>(R.id.canvasView) }
    private val jsApiRv by lazy { findViewById<RecyclerView>(R.id.jsApiRv) }

    private var v8: V8? = null

    private val console = object : V8BindingAdapter {

        private val tag = "V8Console"

        override fun getBindingId(): String {
            return this.hashCode().toString()
        }

        @V8Method
        fun log(vararg args: Any) {
            Log.d(tag, args.joinToString())
        }
    }
    private val window = object : V8BindingAdapter {
        override fun getBindingId(): String {
            return this.hashCode().toString()
        }
        @V8Method
        fun requestAnimationFrame(function: V8Function) {
            val guessName = function.guessName
            canvasView.queueEvent(16L) {
                v8?.executeJSFunction(guessName)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v8)

        canvasView.setRenderMode(IWebCanvas.RENDER_MODE_AUTO)
        canvasView.queueEvent {
            v8 = V8.createV8Runtime()
            canvasView.initialize(v8!!)
            v8?.add("canvas", canvasView.getMyBinding(v8!!))
            v8?.add("Console", console.getMyBinding(v8!!))
            v8?.add("ctx", canvasView.getContextV8("2d"))
            v8?.add("window", window.getMyBinding(v8!!))

            v8?.executeScript("Console.log('Hello JS');")
        }

        jsApiRv.adapter = AnyAdapter().apply {
            addAll(getJsApis()) { s, _ ->
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
                    Log.d(TAG, "onClick name=${item.source().name}")
                    canvasView.queueEvent {
                        v8?.executeScript(code)
                    }
                }
            })
        }
    }

    private fun getJsApis(): List<JsApi2D> {
        val path = "js"
        val jsNames = assets.list(path) ?: return emptyList()
        return List(jsNames.size) {
            val name = jsNames[it]
            JsApi2D(name, "$path${File.separator}$name")
        }
    }

}