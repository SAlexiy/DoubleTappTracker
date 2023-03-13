package com.salexey.doubletapptracker.features

import androidx.compose.ui.graphics.Color

class ColorFeatures {
    fun rgbToHsv(color: Color): List<Float> {


        val r = color.red.toDouble() / 255.0
        val g = color.green.toDouble() / 255.0
        val b = color.blue.toDouble() / 255.0

        val cMax = r.coerceAtLeast(g.coerceAtLeast(b))
        val cMin = r.coerceAtMost(g.coerceAtMost(b))
        val diff = cMax - cMin

        var h = -1.0
        var s = -1.0

        // if cmax and cmax are equal then h = 0
        when (cMax) {
            cMin -> h = 0.0

            r -> h = (60 * ((g - b) / diff) + 360) % 360

            g -> h = (60 * ((b - r) / diff) + 120) % 360

            b -> h = (60 * ((r - g) / diff) + 240) % 360
        }

        s = if (cMax == 0.0) {
            0.0
        } else {
            diff / cMax * 100
        }

        val v = cMax * 100

        return listOf(
            h.toFloat(),
            s.toFloat(),
            v.toFloat())
    }
}