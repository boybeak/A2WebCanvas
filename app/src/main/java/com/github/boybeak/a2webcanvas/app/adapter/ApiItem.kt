package com.github.boybeak.a2webcanvas.app.adapter

import com.github.boybeak.a2webcanvas.app.Api2D
import com.github.boybeak.a2webcanvas.app.R
import com.github.boybeak.adapter.AbsItem
import com.github.boybeak.adapter.ItemImpl

class ApiItem(api2D: Api2D) : AbsItem<Api2D>(api2D) {
    override fun layoutId(): Int {
        return R.layout.layout_api_item
    }

    override fun holderClass(): Class<ApiHolder> {
        return ApiHolder::class.java
    }

    override fun areContentsSame(other: ItemImpl<*>): Boolean {
        if (other !is ApiItem) {
            return false
        }
        return source().name == other.source().name
    }
}