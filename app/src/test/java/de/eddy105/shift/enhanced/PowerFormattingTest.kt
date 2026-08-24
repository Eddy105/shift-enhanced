package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Test

class PowerFormattingTest {
    @Test
    fun currentIsFormattedInMilliamps() {
        assertEquals("-412 mA", formatCurrentMicros(-412_000))
        assertEquals("0 mA", formatCurrentMicros(0))
    }
}

internal fun formatCurrentMicros(currentMicros: Int): String = "${currentMicros / 1000} mA"
