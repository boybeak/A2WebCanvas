package com.github.boybeak.webcanvas.utils

import android.graphics.Color
import android.util.Log
import java.util.Locale

object HtmlColor {
    private const val TAG = "HtmlColor"
    private fun color(name: String): Int {
        return htmlColorMap[name.toUpperCase(Locale.ENGLISH)] ?: throw IllegalArgumentException("Unknown color name($name)")
    }
    fun parseColor(colorStr: String): Int {
        return if (colorStr.indexOf("rgba", ignoreCase = true) >= 0) {
            val array = colorStr.split("[(,)]".toRegex())
            val r = array[1].trim().toInt()
            val g = array[2].trim().toInt()
            val b = array[3].trim().toInt()
            val a = (array[4].trim().toFloat() * 255).toInt()

            Color.argb(a, r, g, b)
        } else if (colorStr.indexOf("rgb", ignoreCase = true) >= 0) {
            val array = colorStr.split("[(,)]".toRegex())
            val r = array[1].trim().toInt()
            val g = array[2].trim().toInt()
            val b = array[3].trim().toInt()

            Color.rgb(r, g, b)
        } else if (colorStr.indexOf('#') >= 0) {
            val builder = StringBuilder()
            var started = false
            pushChar@ for (i in colorStr.indexOf('#') until colorStr.length) {
                when(val c = colorStr[i]) {
                    '#',
                    in '0'..'9',
                    in 'a' .. 'f',
                    in 'A' .. 'F'-> {
                        started = true
                        builder.append(c)
                    }
                    else -> {
                        if (started) {
                            break@pushChar
                        }
                    }
                }
            }
            val colorStrFormatted = builder.toString()
            when(colorStrFormatted.length) {
                4 -> {
                    // #FFF
                    val r = colorStrFormatted.substring(1, 2).toInt(16) * 0xFF / 0xF
                    val g = colorStrFormatted.substring(2, 3).toInt(16) * 0xFF / 0xF
                    val b = colorStrFormatted.substring(3, 4).toInt(16) * 0xFF / 0xF
                    Color.rgb(r, g, b)
                }
                5 -> {
                    // #FFFF
                    val a = colorStrFormatted.substring(1, 2).toInt(16) * 0xFF / 0xF
                    val r = colorStrFormatted.substring(2, 3).toInt(16) * 0xFF / 0xF
                    val g = colorStrFormatted.substring(3, 4).toInt(16) * 0xFF / 0xF
                    val b = colorStrFormatted.substring(4, 5).toInt(16) * 0xFF / 0xF
                    Color.argb(a, r, g, b)
                }
                7 -> {
                    // #FFFFFF
                    val r = colorStrFormatted.substring(1, 3).toInt(16)
                    val g = colorStrFormatted.substring(3, 5).toInt(16)
                    val b = colorStrFormatted.substring(5, 7).toInt(16)
                    Color.rgb(r, g, b)
                }
                9 -> {
                    // #FFFFFFFF
                    val a = colorStrFormatted.substring(1, 3).toInt(16)
                    val r = colorStrFormatted.substring(3, 5).toInt(16)
                    val g = colorStrFormatted.substring(5, 7).toInt(16)
                    val b = colorStrFormatted.substring(7, 9).toInt(16)
                    Color.argb(a, r, g, b)
                }
                else -> throw IllegalArgumentException("Unknown color format $colorStr")
            }
        } else {
            HtmlColor.color(colorStr)
        }.also {
            val a = Color.alpha(it).toString(16)
            val r = Color.red(it).toString(16)
            val g = Color.green(it).toString(16)
            val b = Color.blue(it).toString(16)

            val colorRet = StringBuilder("#").apply {
                arrayOf(a, r, g, b).forEach { bit ->
                    if (bit.length == 1) {
                        append('0')
                    }
                    append(bit)
                }
            }.toString()
        }
    }
    private val htmlColorMap = hashMapOf<String, Int>(
        "INDIANRED" to 0xFFCD5C5C.toInt(),
        "LIGHTCORAL" to 0xFFF08080.toInt(),
        "SALMON" to 0xFFFA8072.toInt(),
        "DARKSALMON" to 0xFFE9967A.toInt(),
        "LIGHTSALMON" to 0xFFFFA07A.toInt(),
        "CRIMSON" to 0xFFDC143C.toInt(),
        "RED" to 0xFFFF0000.toInt(),
        "FIREBRICK" to 0xFFB22222.toInt(),
        "DARKRED" to 0xFF8B0000.toInt(),
        "PINK" to 0xFFFFC0CB.toInt(),
        "LIGHTPINK" to 0xFFFFB6C1.toInt(),
        "HOTPINK" to 0xFFFF69B4.toInt(),
        "DEEPPINK" to 0xFFFF1493.toInt(),
        "MEDIUMVIOLETRED" to 0xFFC71585.toInt(),
        "PALEVIOLETRED" to 0xFFDB7093.toInt(),
        "LIGHTSALMON" to 0xFFFFA07A.toInt(),
        "CORAL" to 0xFFFF7F50.toInt(),
        "TOMATO" to 0xFFFF6347.toInt(),
        "ORANGERED" to 0xFFFF4500.toInt(),
        "DARKORANGE" to 0xFFFF8C00.toInt(),
        "ORANGE" to 0xFFFFA500.toInt(),
        "GOLD" to 0xFFFFD700.toInt(),
        "YELLOW" to 0xFFFFFF00.toInt(),
        "LIGHTYELLOW" to 0xFFFFFFE0.toInt(),
        "LEMONCHIFFON" to 0xFFFFFACD.toInt(),
        "LIGHTGOLDENRODYELLOW" to 0xFFFAFAD2.toInt(),
        "PAPAYAWHIP" to 0xFFFFEFD5.toInt(),
        "MOCCASIN" to 0xFFFFE4B5.toInt(),
        "PEACHPUFF" to 0xFFFFDAB9.toInt(),
        "PALEGOLDENROD" to 0xFFEEE8AA.toInt(),
        "KHAKI" to 0xFFF0E68C.toInt(),
        "DARKKHAKI" to 0xFFBDB76B.toInt(),
        "LAVENDER" to 0xFFE6E6FA.toInt(),
        "THISTLE" to 0xFFD8BFD8.toInt(),
        "PLUM" to 0xFFDDA0DD.toInt(),
        "VIOLET" to 0xFFEE82EE.toInt(),
        "ORCHID" to 0xFFDA70D6.toInt(),
        "FUCHSIA" to 0xFFFF00FF.toInt(),
        "MAGENTA" to 0xFFFF00FF.toInt(),
        "MEDIUMORCHID" to 0xFFBA55D3.toInt(),
        "MEDIUMPURPLE" to 0xFF9370DB.toInt(),
        "REBECCAPURPLE" to 0xFF663399.toInt(),
        "BLUEVIOLET" to 0xFF8A2BE2.toInt(),
        "DARKVIOLET" to 0xFF9400D3.toInt(),
        "DARKORCHID" to 0xFF9932CC.toInt(),
        "DARKMAGENTA" to 0xFF8B008B.toInt(),
        "PURPLE" to 0xFF800080.toInt(),
        "INDIGO" to 0xFF4B0082.toInt(),
        "SLATEBLUE" to 0xFF6A5ACD.toInt(),
        "DARKSLATEBLUE" to 0xFF483D8B.toInt(),
        "MEDIUMSLATEBLUE" to 0xFF7B68EE.toInt(),
        "GREENYELLOW" to 0xFFADFF2F.toInt(),
        "CHARTREUSE" to 0xFF7FFF00.toInt(),
        "LAWNGREEN" to 0xFF7CFC00.toInt(),
        "LIME" to 0xFF00FF00.toInt(),
        "LIMEGREEN" to 0xFF32CD32.toInt(),
        "PALEGREEN" to 0xFF98FB98.toInt(),
        "LIGHTGREEN" to 0xFF90EE90.toInt(),
        "MEDIUMSPRINGGREEN" to 0xFF00FA9A.toInt(),
        "SPRINGGREEN" to 0xFF00FF7F.toInt(),
        "MEDIUMSEAGREEN" to 0xFF3CB371.toInt(),
        "SEAGREEN" to 0xFF2E8B57.toInt(),
        "FORESTGREEN" to 0xFF228B22.toInt(),
        "GREEN" to 0xFF008000.toInt(),
        "DARKGREEN" to 0xFF006400.toInt(),
        "YELLOWGREEN" to 0xFF9ACD32.toInt(),
        "OLIVEDRAB" to 0xFF6B8E23.toInt(),
        "OLIVE" to 0xFF808000.toInt(),
        "DARKOLIVEGREEN" to 0xFF556B2F.toInt(),
        "MEDIUMAQUAMARINE" to 0xFF66CDAA.toInt(),
        "DARKSEAGREEN" to 0xFF8FBC8B.toInt(),
        "LIGHTSEAGREEN" to 0xFF20B2AA.toInt(),
        "DARKCYAN" to 0xFF008B8B.toInt(),
        "TEAL" to 0xFF008080.toInt(),
        "AQUA" to 0xFF00FFFF.toInt(),
        "CYAN" to 0xFF00FFFF.toInt(),
        "LIGHTCYAN" to 0xFFE0FFFF.toInt(),
        "PALETURQUOISE" to 0xFFAFEEEE.toInt(),
        "AQUAMARINE" to 0xFF7FFFD4.toInt(),
        "TURQUOISE" to 0xFF40E0D0.toInt(),
        "MEDIUMTURQUOISE" to 0xFF48D1CC.toInt(),
        "DARKTURQUOISE" to 0xFF00CED1.toInt(),
        "CADETBLUE" to 0xFF5F9EA0.toInt(),
        "STEELBLUE" to 0xFF4682B4.toInt(),
        "LIGHTSTEELBLUE" to 0xFFB0C4DE.toInt(),
        "POWDERBLUE" to 0xFFB0E0E6.toInt(),
        "LIGHTBLUE" to 0xFFADD8E6.toInt(),
        "SKYBLUE" to 0xFF87CEEB.toInt(),
        "LIGHTSKYBLUE" to 0xFF87CEFA.toInt(),
        "DEEPSKYBLUE" to 0xFF00BFFF.toInt(),
        "DODGERBLUE" to 0xFF1E90FF.toInt(),
        "CORNFLOWERBLUE" to 0xFF6495ED.toInt(),
        "MEDIUMSLATEBLUE" to 0xFF7B68EE.toInt(),
        "ROYALBLUE" to 0xFF4169E1.toInt(),
        "BLUE" to 0xFF0000FF.toInt(),
        "MEDIUMBLUE" to 0xFF0000CD.toInt(),
        "DARKBLUE" to 0xFF00008B.toInt(),
        "NAVY" to 0xFF000080.toInt(),
        "MIDNIGHTBLUE" to 0xFF191970.toInt(),
        "CORNSILK" to 0xFFFFF8DC.toInt(),
        "BLANCHEDALMOND" to 0xFFFFEBCD.toInt(),
        "BISQUE" to 0xFFFFE4C4.toInt(),
        "NAVAJOWHITE" to 0xFFFFDEAD.toInt(),
        "WHEAT" to 0xFFF5DEB3.toInt(),
        "BURLYWOOD" to 0xFFDEB887.toInt(),
        "TAN" to 0xFFD2B48C.toInt(),
        "ROSYBROWN" to 0xFFBC8F8F.toInt(),
        "SANDYBROWN" to 0xFFF4A460.toInt(),
        "GOLDENROD" to 0xFFDAA520.toInt(),
        "DARKGOLDENROD" to 0xFFB8860B.toInt(),
        "PERU" to 0xFFCD853F.toInt(),
        "CHOCOLATE" to 0xFFD2691E.toInt(),
        "SADDLEBROWN" to 0xFF8B4513.toInt(),
        "SIENNA" to 0xFFA0522D.toInt(),
        "BROWN" to 0xFFA52A2A.toInt(),
        "MAROON" to 0xFF800000.toInt(),
        "WHITE" to 0xFFFFFFFF.toInt(),
        "SNOW" to 0xFFFFFAFA.toInt(),
        "HONEYDEW" to 0xFFF0FFF0.toInt(),
        "MINTCREAM" to 0xFFF5FFFA.toInt(),
        "AZURE" to 0xFFF0FFFF.toInt(),
        "ALICEBLUE" to 0xFFF0F8FF.toInt(),
        "GHOSTWHITE" to 0xFFF8F8FF.toInt(),
        "WHITESMOKE" to 0xFFF5F5F5.toInt(),
        "SEASHELL" to 0xFFFFF5EE.toInt(),
        "BEIGE" to 0xFFF5F5DC.toInt(),
        "OLDLACE" to 0xFFFDF5E6.toInt(),
        "FLORALWHITE" to 0xFFFFFAF0.toInt(),
        "IVORY" to 0xFFFFFFF0.toInt(),
        "ANTIQUEWHITE" to 0xFFFAEBD7.toInt(),
        "LINEN" to 0xFFFAF0E6.toInt(),
        "LAVENDERBLUSH" to 0xFFFFF0F5.toInt(),
        "MISTYROSE" to 0xFFFFE4E1.toInt(),
        "GAINSBORO" to 0xFFDCDCDC.toInt(),
        "LIGHTGRAY" to 0xFFD3D3D3.toInt(),
        "SILVER" to 0xFFC0C0C0.toInt(),
        "DARKGRAY" to 0xFFA9A9A9.toInt(),
        "GRAY" to 0xFF808080.toInt(),
        "DIMGRAY" to 0xFF696969.toInt(),
        "LIGHTSLATEGRAY" to 0xFF778899.toInt(),
        "SLATEGRAY" to 0xFF708090.toInt(),
        "DARKSLATEGRAY" to 0xFF2F4F4F.toInt(),
        "BLACK" to 0xFF000000.toInt()
    )
}