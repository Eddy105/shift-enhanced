package de.eddy105.shift.enhanced

import android.os.BatteryManager
import org.junit.Assert.assertEquals
import org.junit.Test

class BatteryStatusFormattingTest {
    private val baseTelemetry = PowerTelemetry(
        capacityPercent = 80,
        currentMicros = 0,
        temperatureTenthsC = null,
        voltageMillivolts = null,
        status = BatteryManager.BATTERY_STATUS_UNKNOWN,
        health = BatteryManager.BATTERY_HEALTH_GOOD,
        plugged = 0
    )

    @Test
    fun formatsChargingState() {
        assertEquals(
            "Charging",
            formatBatteryStatus(baseTelemetry.copy(status = BatteryManager.BATTERY_STATUS_CHARGING))
        )
    }

    @Test
    fun formatsFullStateSeparatelyFromCharging() {
        assertEquals(
            "Fully charged",
            formatBatteryStatus(baseTelemetry.copy(status = BatteryManager.BATTERY_STATUS_FULL))
        )
    }

    @Test
    fun formatsNonChargingState() {
        assertEquals(
            "Not charging",
            formatBatteryStatus(baseTelemetry.copy(status = BatteryManager.BATTERY_STATUS_DISCHARGING))
        )
    }
}
