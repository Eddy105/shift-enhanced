package de.eddy105.shift.enhanced

import android.os.BatteryManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PowerTelemetryTest {
    @Test
    fun convertsMicrosToMilliamps() {
        val telemetry = PowerTelemetry(82, -412_000, null, null, null, null)
        assertEquals(-412, telemetry.currentMilliamps)
    }

    @Test
    fun preservesBatteryCapacity() {
        val telemetry = PowerTelemetry(82, 0, null, null, null, null)
        assertEquals(82, telemetry.capacityPercent)
    }

    @Test
    fun convertsTemperatureToCelsius() {
        val telemetry = PowerTelemetry(82, 0, 294, 4_200, BatteryManager.BATTERY_STATUS_DISCHARGING, null)
        assertEquals(29.4, telemetry.temperatureCelsius!!, 0.001)
    }

    @Test
    fun chargingStatusIsDerivedFromBatteryStatus() {
        val charging = PowerTelemetry(82, 0, null, null, BatteryManager.BATTERY_STATUS_CHARGING, null)
        val full = PowerTelemetry(100, 0, null, null, BatteryManager.BATTERY_STATUS_FULL, null)
        val discharging = PowerTelemetry(82, 0, null, null, BatteryManager.BATTERY_STATUS_DISCHARGING, null)

        assertTrue(charging.isCharging)
        assertTrue(full.isCharging)
        assertFalse(discharging.isCharging)
    }
}
