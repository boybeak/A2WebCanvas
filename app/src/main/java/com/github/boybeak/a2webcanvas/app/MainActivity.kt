package com.github.boybeak.a2webcanvas.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.SpinnerAdapter
import android.widget.TextView
import com.github.boybeak.webcanvas.IWebCanvas
import com.github.boybeak.webcanvas.IWebCanvasContext
import com.github.boybeak.webcanvas.WebCanvasView
import com.github.boybeak.webcanvas.ext.post
import com.github.boybeak.webcanvas.twod.CanvasRenderingContext2D
import com.github.boybeak.webcanvas.twod.IWebCanvas2D

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private val modes = mapOf(
        IWebCanvas.RENDER_MODE_WHEN_DIRTY to "WHEN_DIRTY",
        IWebCanvas.RENDER_MODE_CONTINUOUSLY to "CONTINUOUSLY",
        IWebCanvas.RENDER_MODE_AUTO to "AUTO"
    )
    private val currentMode = modes[0]

    private val canvasView by lazy { findViewById<WebCanvasView>(R.id.webCanvas) }
    private val invalidateButton by lazy { findViewById<Button>(R.id.invalidateButton) }
    private val houseButton by lazy { findViewById<Button>(R.id.houseButton) }
    private val switchModeSpinner by lazy { findViewById<Spinner>(R.id.modeSpinner) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        invalidateButton.setOnClickListener {
            canvasView.requestRender()
        }
        houseButton.setOnClickListener {
            canvasView.getContext<CanvasRenderingContext2D>("2d").post {
                strokeRect(75F, 140F, 150F, 110F)
                fillRect(130F, 190F, 40F, 60F)

                beginPath()
                moveTo(50F, 140F)
                lineTo(150F, 60F)
                lineTo(250F, 140F)
                closePath()
                stroke()
            }
        }
        switchModeSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                canvasView.setRenderMode(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        switchModeSpinner.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return modes.size
            }

            override fun getItem(position: Int): Any {
                return modes[position]!!
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                return TextView(this@MainActivity).apply {
                    text = getItem(position).toString()
                }
            }
        }
    }
}