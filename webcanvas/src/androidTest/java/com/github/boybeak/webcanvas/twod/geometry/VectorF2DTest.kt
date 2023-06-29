package com.github.boybeak.webcanvas.twod.geometry

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class VectorF2DTest {

    companion object {
        private const val TAG = "VectorF2DTest"
    }

    private val random = Random(System.currentTimeMillis())

    @Test
    fun testNormalized() {
        val vector = VectorF2D(1F, 0F)
        assert(vector.module() == 1F)
    }

    @Test
    fun testNormalizedRandom() {
        val vector = newRandomVectorF2D()
        assert(vector.normalized.module() == 1F)
    }

    @Test
    fun testDegreesWith() {
        val xPositive = VectorF2D(1F, 0F)
        val yPositive = VectorF2D(0F, 2F)
        assert(xPositive.degreesWith(yPositive) == 90F)
    }

    @Test
    fun testAngleMidWith() {
        val va = VectorF2D(1F, 0F)
        val vb = VectorF2D(0F, 1F)
        val vm = va.angleMidWith(vb)
        assert(va.degreesWith(vb) / va.degreesWith(vm) == 2F)
    }

    @Test
    fun testAngleMidWithRandomly() {
        val va = newRandomVectorF2D()
        val vb = newRandomVectorF2D()
        val vm = va.angleMidWith(vb)
        Log.d(TAG, "testAngleMidWithRandomly va=$va vb=$vb vm=$vm")
        val degreesAB = va.degreesWith(vb)
        val degreesAM = va.degreesWith(vm)
        Log.d(TAG, "testAngleMidWithRandomly degreesAB=$degreesAB degreesAM=$degreesAM ratio=${degreesAB / degreesAM}")
        assert(degreesAB / degreesAM == 2F)
    }

    private fun newRandomVectorF2D(): VectorF2D {
        return VectorF2D(random.nextDouble(100.0).toFloat() - 50, random.nextDouble(100.0).toFloat() - 50)
    }
}