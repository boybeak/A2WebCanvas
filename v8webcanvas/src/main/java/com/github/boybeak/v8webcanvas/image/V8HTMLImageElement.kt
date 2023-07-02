package com.github.boybeak.v8webcanvas.image

import com.eclipsesource.v8.V8Object
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Field
import com.github.boybeak.webcanvas.image.HTMLImageElement

class V8HTMLImageElement(private val htmlImageElement: HTMLImageElement) : V8Binding {

    companion object {
        private const val KEY_HTML_IMAGE_ELEMENT_ID = "_v8HTMLImageElementId"
        fun getId(v8obj: V8Object): String {
            return v8obj.getString(KEY_HTML_IMAGE_ELEMENT_ID)
        }
    }

    @V8Field
    private val _v8HTMLImageElementId: String = htmlImageElement.id

    override fun getBindingId(): String {
        return _v8HTMLImageElementId
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {}

    override fun getAdditionalPropertyNames(): Array<String>? {
        return arrayOf("src")
    }

    override fun onAdditionalPropertyChanged(
        target: V8Object,
        propertyName: String,
        newValue: Any?,
        oldValue: Any?
    ) {
        when(propertyName) {
            "src" -> htmlImageElement.src = newValue.toString()
        }
    }

}