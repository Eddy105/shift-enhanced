package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Test

class PowerSessionFormattingTest {
    @Test
    fun formatsMinutesAndSeconds() {
        assertEquals("02m 05s", formatDuration(125_000))
    }

    @Test
    fun formatsHoursMinutesAndSeconds() {
        assertEquals("1h 01m 01s", formatDuration(3_661_000))
    }
}
