package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Test

class PowerTelemetryConversionTest {
    @Test
    fun convertsNegativeCurrentFromMicroampsToMilliamps() {
        val telemetry = PowerTelemetry(
            capacityPercent = 80,
            currentMicros = -1_250_000,
            temperatureCelsius = null,
            voltageVolts = null,
            status = 1,
            health = 2,
            plugged = 0
        )

        assertEquals(-1250, telemetry.currentMilliamps)
    }

    @Test
    fun convertsPositiveCurrentFromMicroampsToMilliamps() {
        val telemetry = PowerTelemetry(
            capacityPercent = 80,
            currentMicros = 875_000,
            temperatureCelsius = null,
            voltageVolts = null,
            status = 1,
            health = 2,
            plugged = 0
        )

        assertEquals(875, telemetry.currentMilliamps)
    }
}
