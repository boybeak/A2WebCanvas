package com.github.boybeak.webcanvas.twod.geometry

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArcToTest {

    companion object {
        private const val TAG = "ArcToTest"
    }

    private val arcTo = ArcTo()

    @Test
    fun testArcTo() {
        val x0 = 200F
        val y0 = 20F
        val x1 = 200F
        val y1 = 130F
        val x2 = 50F
        val y2 = 20F
        val r = 40F

        arcTo.set(x0, y0, x1, y1, x2, y2, r)
        Log.d(TAG, "testArcTo startDegrees=${arcTo.startDegrees}")
        assert(arcTo.startDegrees == 0F)
    }
}