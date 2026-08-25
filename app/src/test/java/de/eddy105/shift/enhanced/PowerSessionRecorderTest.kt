package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PowerSessionRecorderTest {
    @Test
    fun recordAddsTimestampedCurrentSample() {
        var now = 1_000L
        val recorder = PowerSessionRecorder { now }
        val telemetry = PowerTelemetry(
            capacityPercent = 80,
            currentMicros = 1_500_000,
            temperatureTenthsC = null,
            voltageMillivolts = null,
            status = 0,
            health = 0,
            plugged = 0
        )

        recorder.record(telemetry)
        now = 3_500L
        recorder.record(telemetry.copy(currentMicros = 2_500_000))

        assertEquals(2, recorder.session.samples.size)
        assertEquals(2_500, recorder.session.durationMillis)
        assertEquals(2_000, recorder.session.averageCurrentMilliamps)
    }

    @Test
    fun resetClearsSession() {
        val recorder = PowerSessionRecorder { 1_000L }
        recorder.record(
            PowerTelemetry(
                capacityPercent = 50,
                currentMicros = 500_000,
                temperatureTenthsC = null,
                voltageMillivolts = null,
                status = 0,
                health = 0,
                plugged = 0
            )
        )

        recorder.reset()

        assertTrue(recorder.session.samples.isEmpty())
        assertEquals(0, recorder.session.durationMillis)
    }
}
