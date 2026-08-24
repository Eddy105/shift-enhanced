package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PowerSessionTest {
    @Test
    fun emptySessionHasNoAverageAndNoDuration() {
        val session = PowerSession()

        assertNull(session.averageCurrentMilliamps)
        assertEquals(0, session.durationMillis)
    }

    @Test
    fun calculatesDurationAndAverageCurrentFromSamples() {
        val session = PowerSession()
            .addSample(PowerSample(timestampMillis = 1_000, currentMilliamps = -500))
            .addSample(PowerSample(timestampMillis = 4_000, currentMilliamps = -700))
            .addSample(PowerSample(timestampMillis = 7_000, currentMilliamps = -600))

        assertEquals(6_000, session.durationMillis)
        assertEquals(-600, session.averageCurrentMilliamps)
    }

    @Test
    fun addingASampleKeepsPreviousSamplesAndReturnsANewSession() {
        val first = PowerSession()
            .addSample(PowerSample(timestampMillis = 1_000, currentMilliamps = 300))
        val second = first.addSample(PowerSample(timestampMillis = 2_000, currentMilliamps = 500))

        assertEquals(1, first.samples.size)
        assertEquals(2, second.samples.size)
    }
}
