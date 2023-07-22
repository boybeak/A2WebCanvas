package com.github.boybeak.a2webcanvas.app

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.utils.MemoryManager
import com.github.boybeak.a2webcanvas.app.adapter.JsApiItem
import com.github.boybeak.a2webcanvas.app.v8.Console
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.canvas.context.WebCanvasContextOffscreen2D
import com.github.boybeak.canvas.image.ISrcDecoder
import com.github.boybeak.canvas.image.WebImageManager
import com.github.boybeak.canvas.offscreen.WebCanvasOffscreen
import com.github.boybeak.v8canvas.context.V8WebCanvasContextOffscreen2D
import com.github.boybeak.v8canvas.image.V8HTMLImageElement
import com.github.boybeak.v8canvas.offscreen.V8WebCanvasOffscreen
import com.github.boybeak.v8x.binding.V8BindingAdapter
import com.github.boybeak.v8x.binding.annotation.V8Method
import com.github.boybeak.v8x.ext.clear
import java.io.File

class OffscreenActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OffscreenActivity"
    }

    private val imageView: ImageView by lazy { findViewById(R.id.offscreenCanvas) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }

    private val canvas = V8WebCanvasOffscreen()

    private var currentV8: V8? = null
    private var memoryManager: MemoryManager? = null

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
            return try {
                V8HTMLImageElement(WebImageManager.createHTMLImageElement(imageDecoder)).getMyBinding(currentV8!!)
            } catch (e: Throwable) {
                e.printStackTrace()
                V8Object(currentV8!!)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offscreen)

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
                    newV8EnvRun {
                        canvas.initialize(this)
                        val ctxV8 = canvas.getContextV8("2d") as V8WebCanvasContextOffscreen2D
                        add("ctx", ctxV8.getMyBinding(this))
                        val jsCode = item.getJsCode(this@OffscreenActivity)

                        executeScript(jsCode)
                        runOnUiThread {
                            imageView.setImageBitmap(ctxV8.bitmap)
                        }
                    }
                }
            })
        }
    }

    private fun getJsApis(): List<JsApi2D> {
        val path = "js/2d"
        val jsNames = assets.list(path) ?: return emptyList()
        return List(jsNames.size) {
            val name = jsNames[it]
            JsApi2D(name, "$path${File.separator}$name")
        }
    }

    private fun newV8EnvRun(callback: V8.() -> Unit)  {
        try {
            currentV8?.close()
            memoryManager?.release()
        } catch (e: Throwable) {
            e.printStackTrace()
        }

        val v8 = V8.createV8Runtime()
        initV8(v8)
        currentV8 = v8

        memoryManager = MemoryManager(v8)

        callback.invoke(v8)
    }

    private fun initV8(v8: V8) {
        Log.d(TAG, "initV8 ")
        canvas.initialize(v8)
        v8.add("canvas", canvas.getMyBinding(v8))
        v8.add("Console", Console().getMyBinding(v8))
        v8.add("ImageCreator", imageCreator.getMyBinding(v8))
    }
}