package de.eddy105.shift.enhanced

import android.os.BatteryManager

data class PowerTelemetry(
    val capacityPercent: Int,
    val currentMicros: Int
) {
    val currentMilliamps: Int
        get() = currentMicros / 1000
}

fun readPowerTelemetry(manager: BatteryManager): PowerTelemetry = PowerTelemetry(
    capacityPercent = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY),
    currentMicros = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW)
)
