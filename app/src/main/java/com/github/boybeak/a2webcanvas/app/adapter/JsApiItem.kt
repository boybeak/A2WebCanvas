package com.github.boybeak.a2webcanvas.app.adapter

import android.content.Context
import android.text.TextUtils
import com.github.boybeak.a2webcanvas.app.JsApi2D
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.adapter.AbsItem
import com.github.boybeak.adapter.ItemImpl
import java.io.InputStreamReader

class JsApiItem(api: JsApi2D) : AbsItem<JsApi2D>(api) {

    private var code: String? = null

    override fun layoutId(): Int {
        return R.layout.layout_js_api_item
    }

    override fun holderClass(): Class<JsApiHolder> {
        return JsApiHolder::class.java
    }

    override fun areContentsSame(other: ItemImpl<*>): Boolean {
        return other is JsApiItem && source().assetPath == other.source().assetPath
    }

    fun getJsCode(context: Context): String {
        if (TextUtils.isEmpty(code)) {
            val inputStream = context.assets.open(source().assetPath)
            val reader = InputStreamReader(inputStream)
            code = reader.readText()
            reader.close()
            inputStream.close()
        }
        return code!!
    }

}