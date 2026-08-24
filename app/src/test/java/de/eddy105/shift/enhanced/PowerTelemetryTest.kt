package de.eddy105.shift.enhanced

import android.content.Intent
import android.os.BatteryManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class PowerTelemetryTest {
    @Test
    fun convertsMicrosToMilliamps() {
        val telemetry = PowerTelemetry(82, -412_000, null, null, BatteryManager.BATTERY_STATUS_UNKNOWN, BatteryManager.BATTERY_HEALTH_UNKNOWN, 0)
        assertEquals(-412, telemetry.currentMilliamps)
    }

    @Test
    fun preservesBatteryCapacity() {
        val telemetry = PowerTelemetry(82, 0, null, null, BatteryManager.BATTERY_STATUS_UNKNOWN, BatteryManager.BATTERY_HEALTH_UNKNOWN, 0)
        assertEquals(82, telemetry.capacityPercent)
    }

    @Test
    fun convertsTemperatureAndVoltageToDisplayUnits() {
        val telemetry = PowerTelemetry(80, 0, 294, 5000, BatteryManager.BATTERY_STATUS_DISCHARGING, BatteryManager.BATTERY_HEALTH_GOOD, 0)
        assertEquals(29.4, telemetry.temperatureCelsius!!, 0.001)
        assertEquals(5.0, telemetry.voltageVolts!!, 0.001)
    }

    @Test
    fun identifiesChargingAndFullAsChargingStates() {
        val charging = PowerTelemetry(80, 0, null, null, BatteryManager.BATTERY_STATUS_CHARGING, BatteryManager.BATTERY_HEALTH_GOOD, BatteryManager.BATTERY_PLUGGED_USB)
        val full = charging.copy(status = BatteryManager.BATTERY_STATUS_FULL)
        val discharging = charging.copy(status = BatteryManager.BATTERY_STATUS_DISCHARGING)

        assertTrue(charging.isCharging)
        assertTrue(full.isCharging)
        assertFalse(discharging.isCharging)
    }

    @Test
    fun readsOptionalBatteryFieldsFromIntent() {
        val intent = Intent().apply {
            putExtra(BatteryManager.EXTRA_TEMPERATURE, 301)
            putExtra(BatteryManager.EXTRA_VOLTAGE, 4980)
            putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING)
            putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_GOOD)
            putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_USB)
        }

        val telemetry = readPowerTelemetry(FakeBatteryManager(), intent)

        assertEquals(301, telemetry.temperatureTenthsC)
        assertEquals(4980, telemetry.voltageMillivolts)
        assertEquals(BatteryManager.BATTERY_STATUS_CHARGING, telemetry.status)
        assertEquals(BatteryManager.BATTERY_HEALTH_GOOD, telemetry.health)
        assertEquals(BatteryManager.BATTERY_PLUGGED_USB, telemetry.plugged)
    }

    @Test
    fun missingOptionalBatteryFieldsRemainNull() {
        val telemetry = readPowerTelemetry(FakeBatteryManager(), Intent())
        assertNull(telemetry.temperatureTenthsC)
        assertNull(telemetry.voltageMillivolts)
    }

    private class FakeBatteryManager : BatteryManager()
}
