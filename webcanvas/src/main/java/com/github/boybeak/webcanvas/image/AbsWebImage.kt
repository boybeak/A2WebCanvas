package com.github.boybeak.webcanvas.image

import java.util.UUID

abstract class AbsWebImage : IWebImage {
    override val id: String = UUID.randomUUID().toString()
}