package com.github.boybeak.a2webcanvas.app

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Function
import com.eclipsesource.v8.V8Object
import com.github.boybeak.a2webcanvas.app.adapter.JsApiItem
import com.github.boybeak.a2webcanvas.app.v8.V8Engine
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.v8webcanvas.V8WebCanvasView
import com.github.boybeak.v8webcanvas.image.V8HTMLImageElement
import com.github.boybeak.v8x.binding.V8BindingAdapter
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.guessName
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.image.ISrcDecoder
import com.github.boybeak.webcanvas.image.WebImageManager
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import java.io.File

class OnscreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OnscreenActivity"
        const val KEY_SUB_DIR = "sub-dir"
    }

    private val density get() = resources.displayMetrics.density

    private val canvas by lazy { findViewById<V8WebCanvasView>(R.id.webCanvas) }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.apiList) }
    private val stopBtn by lazy { findViewById<View>(R.id.stopBtn) }

    private val v8Engine = V8Engine()

    private val console = object : V8BindingAdapter {

        private val tag = "V8Console"

        override fun getBindingId(): String {
            return this.hashCode().toString()
        }

        @V8Method
        fun log(vararg args: Any) {
            Log.d(tag, args.joinToString(separator = ""))
        }
    }
    private val window = object : V8BindingAdapter {

        @Volatile
        var stopWhenNextFrame = false

        override fun getBindingId(): String {
            return this.hashCode().toString()
        }
        @V8Method
        fun requestAnimationFrame(function: V8Function) {
            if (stopWhenNextFrame) {
                stopWhenNextFrame = false
                return
            }

            val guessName = function.guessName
            canvas.queueEvent(16L) {
                canvas?.getContext<CanvasRenderingContext2D>("2d")?.scale(density, density)
                v8Engine.v8Run {
                    executeJSFunction(guessName)
                }
            }
            if (stopBtn.visibility == View.GONE) {
                runOnUiThread {
                    stopBtn.visibility = View.VISIBLE
                }
            }
        }
    }
    private val imageDecoder = object : ISrcDecoder {
        override fun decode(src: String?, callback: ISrcDecoder.Callback) {
            src ?: return
            try {
                if (src.startsWith("assets")) {
                    val path = src.replace("assets/", "")
                    Thread {
                        val bitmap = assets.open(path).run {
                            val bmp = BitmapFactory.decodeStream(this)
                            this.close()
                            bmp
                        }
                        callback.onLoad(bitmap)
                    }.start()
                } else {
                    null
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                callback.onError(e)
            }
        }
    }
    private val imageCreator = object : V8BindingAdapter {
        override fun getBindingId(): String {
            return this.hashCode().toString()
        }
        @V8Method
        fun createImage(): V8Object {
            return V8HTMLImageElement(WebImageManager.createHTMLImageElement(imageDecoder)).getMyBinding(v8Engine.v8)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onscreen)

        v8Engine.onV8Prepared {
            canvas.initialize(this)
            initV8(this)
        }
        canvas.setOnPrepareCallback { v8Engine.v8Looper }
        canvas.setRenderMode(IWebCanvas.RENDER_MODE_AUTO)

        recyclerView.adapter = AnyAdapter().apply {
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
                    canvas.queueEvent {
                        v8Engine.v8Run {
                            executeScript(code)
                        }
                    }
                }
            })
        }

        canvas.setOnTouchListener { v, event ->
            when(event.action) {
                MotionEvent.ACTION_UP -> v8Engine.v8Run {
                    executeJSFunction("onTouchEnd", event.x / density, event.y / density)
                }
            }
            true
        }

        stopBtn.setOnClickListener {
            window.stopWhenNextFrame = true
            stopBtn.visibility = View.GONE
            v8Engine.reset {
                initV8(this)
            }
        }
    }

    private fun initV8(v8: V8) {
        v8.add("canvas", canvas.getMyBinding(v8))
        v8.add("Console", console.getMyBinding(v8))
        v8.add("ctx", canvas.getContextV8("2d"))
        v8.add("window", window.getMyBinding(v8))
        v8.add("ImageCreator", imageCreator.getMyBinding(v8))
    }

    private fun getJsApis(): List<JsApi2D> {
        val path = "js/${intent.getStringExtra(KEY_SUB_DIR)}"
        val jsNames = assets.list(path) ?: return emptyList()
        return List(jsNames.size) {
            val name = jsNames[it]
            JsApi2D(name, "$path${File.separator}$name")
        }
    }

    override fun onResume() {
        super.onResume()
        canvas.onResume()
    }

    override fun onPause() {
        super.onPause()
        canvas.onPause()
    }

}