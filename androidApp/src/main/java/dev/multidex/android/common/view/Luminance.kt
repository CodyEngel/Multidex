package dev.multidex.android.common.view

enum class Luminance {
    LIGHT, DARK;
}

class NotAHexColorException(color: String) : IllegalArgumentException("$color is not a valid hex color.")

val String.luminance: Luminance
    get() {
        if (!matches(Regex("^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})\$"))) {
            throw NotAHexColorException(this)
        }

        val chunks = removePrefix("#").chunked(2)
        val red = chunks[0].toInt(radix = 16)
        val green = chunks[1].toInt(radix = 16)
        val blue = chunks[2].toInt(radix = 16)
        val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255

        return if (luminance > .5) Luminance.LIGHT else Luminance.DARK
    }