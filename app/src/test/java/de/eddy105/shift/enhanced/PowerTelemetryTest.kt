package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Test

class PowerTelemetryTest {
    @Test
    fun convertsMicrosToMilliamps() {
        val telemetry = PowerTelemetry(capacityPercent = 82, currentMicros = -412_000)
        assertEquals(-412, telemetry.currentMilliamps)
    }

    @Test
    fun preservesBatteryCapacity() {
        val telemetry = PowerTelemetry(capacityPercent = 82, currentMicros = 0)
        assertEquals(82, telemetry.capacityPercent)
    }
}
