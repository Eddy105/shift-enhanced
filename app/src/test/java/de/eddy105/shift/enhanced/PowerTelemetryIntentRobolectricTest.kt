package de.eddy105.shift.enhanced

import android.content.Intent
import android.os.BatteryManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PowerTelemetryIntentRobolectricTest {
    @Test
    fun readsBatteryIntentExtrasAndPreservesUnavailableValues() {
        val intent = Intent(Intent.ACTION_BATTERY_CHANGED)
            .putExtra(BatteryManager.EXTRA_TEMPERATURE, 301)
            .putExtra(BatteryManager.EXTRA_VOLTAGE, 4210)
            .putExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_CHARGING)
            .putExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_GOOD)
            .putExtra(BatteryManager.EXTRA_PLUGGED, BatteryManager.BATTERY_PLUGGED_USB)

        val telemetry = readPowerTelemetry(87, -650_000, intent)

        assertEquals(87, telemetry.capacityPercent)
        assertEquals(-650, telemetry.currentMilliamps)
        assertEquals(30.1, telemetry.temperatureCelsius!!, 0.001)
        assertEquals(4.21, telemetry.voltageVolts!!, 0.001)
        assertEquals(BatteryManager.BATTERY_STATUS_CHARGING, telemetry.status)
        assertEquals(BatteryManager.BATTERY_HEALTH_GOOD, telemetry.health)
        assertEquals(BatteryManager.BATTERY_PLUGGED_USB, telemetry.plugged)
    }

    @Test
    fun missingOptionalExtrasRemainUnavailable() {
        val telemetry = readPowerTelemetry(
            55,
            0,
            Intent(Intent.ACTION_BATTERY_CHANGED)
        )

        assertNull(telemetry.temperatureCelsius)
        assertNull(telemetry.voltageVolts)
        assertEquals(BatteryManager.BATTERY_STATUS_UNKNOWN, telemetry.status)
        assertEquals(BatteryManager.BATTERY_HEALTH_UNKNOWN, telemetry.health)
    }
}
