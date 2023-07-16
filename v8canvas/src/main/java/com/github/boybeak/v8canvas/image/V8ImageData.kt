package com.github.boybeak.v8canvas.image

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8ArrayBuffer
import com.eclipsesource.v8.V8Object
import com.eclipsesource.v8.V8TypedArray
import com.eclipsesource.v8.V8Value
import com.github.boybeak.canvas.image.ImageData
import com.github.boybeak.v8x.binding.Key
import com.github.boybeak.v8x.binding.V8Binding
import com.github.boybeak.v8x.binding.annotation.V8Field

class V8ImageData(private val imageData: ImageData) : V8Binding {

    companion object {
        private const val KEY_V8_IMAGE_DATA_ID = "_v8ImageDataId"
        fun getId(v8obj: V8Object): String {
            return v8obj.getString(KEY_V8_IMAGE_DATA_ID)
        }
    }

    @V8Field
    private val _v8ImageDataId = imageData.id

    override fun getBindingId(): String {
        return imageData.id
    }

    override fun getMyBinding(v8: V8): V8Object {
        return super.getMyBinding(v8).also {
            it.add("width", imageData.width)
            it.add("height", imageData.height)
            it.add("data", V8TypedArray(v8, V8ArrayBuffer(v8, imageData.data), V8Value.INT_8_ARRAY, 0, imageData.bitmap.byteCount))
        }
    }

    override fun onBindingChanged(
        target: V8Object,
        fieldInfo: Key,
        newValue: Any?,
        oldValue: Any?
    ) {}
}