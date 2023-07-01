package com.github.boybeak.a2webcanvas.app.adapter

import android.view.View
import android.widget.TextView
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.adapter.AbsHolder
import com.github.boybeak.adapter.AnyAdapter

class JsApiHolder(v : View) : AbsHolder<JsApiItem>(v) {
    private val titleTv = v.findViewById<TextView>(R.id.apiTitle)
    override fun onBind(item: JsApiItem, position: Int, absAdapter: AnyAdapter) {
        titleTv.text = item.source().name
    }

}