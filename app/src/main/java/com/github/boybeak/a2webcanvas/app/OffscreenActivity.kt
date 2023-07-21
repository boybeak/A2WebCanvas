package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.github.boybeak.a2webcanvas.app.adapter.JsApiItem
import com.github.boybeak.adapter.AnyAdapter
import com.github.boybeak.adapter.event.OnItemClick
import com.github.boybeak.canvas.context.WebCanvasContextOffscreen2D
import com.github.boybeak.canvas.offscreen.WebCanvasOffscreen
import java.io.File

class OffscreenActivity : AppCompatActivity() {

    private val imageView: ImageView by lazy { findViewById(R.id.offscreenCanvas) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }

    private val offscreenCanvas2D = WebCanvasOffscreen()

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
                    (offscreenCanvas2D.getContext("2d") as WebCanvasContextOffscreen2D).run {
                        fillRect(0F, 0F, 120F, 120F)
                        imageView.setImageBitmap(bitmap)
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
}