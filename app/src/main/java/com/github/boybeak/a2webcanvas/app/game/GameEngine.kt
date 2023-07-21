package com.github.boybeak.a2webcanvas.app.game

class GameEngine<T : GamePlayground>(private val creator: (name: String) -> T) {

    private var currentPlayground: T? = null

    val playground: T get() = currentPlayground!!

    fun createPlayground(name: String, onFinish: GamePlayground.() -> Unit): GamePlayground {
        if (currentPlayground != null) {
            currentPlayground?.stop()
            currentPlayground = null
        }
        currentPlayground = creator.invoke(name)
        currentPlayground?.start {
            onFinish.invoke(currentPlayground!!)
        }
        return currentPlayground!!
    }

    /*fun destroyPlayground() {
        currentPlayground?.stop()
        currentPlayground = null
    }*/

}