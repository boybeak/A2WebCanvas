package com.github.boybeak.canvas._2d.paint

data class LineDash(val intervals: FloatArray, val phase: Float) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LineDash

        if (!intervals.contentEquals(other.intervals)) return false
        if (phase != other.phase) return false

        return true
    }

    override fun hashCode(): Int {
        var result = intervals.contentHashCode()
        result = 31 * result + phase.hashCode()
        return result
    }
}