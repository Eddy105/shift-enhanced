package de.eddy105.shift.enhanced

import android.content.Intent
import android.os.BatteryManager

data class PowerTelemetry(
    val capacityPercent: Int,
    val currentMicros: Int,
    val temperatureTenthsC: Int?,
    val voltageMillivolts: Int?,
    val status: Int,
    val health: Int,
    val plugged: Int
) {
    val currentMilliamps: Int
        get() = currentMicros / 1000

    val temperatureCelsius: Double?
        get() = temperatureTenthsC?.div(10.0)

    val voltageVolts: Double?
        get() = voltageMillivolts?.div(1000.0)

    val isCharging: Boolean
        get() = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL
}

fun readPowerTelemetry(manager: BatteryManager, batteryIntent: Intent): PowerTelemetry =
    readPowerTelemetry(
        capacityPercent = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY),
        currentMicros = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW),
        batteryIntent = batteryIntent
    )

fun readPowerTelemetry(
    capacityPercent: Int,
    currentMicros: Int,
    batteryIntent: Intent
): PowerTelemetry = PowerTelemetry(
    capacityPercent = capacityPercent,
    currentMicros = currentMicros,
    temperatureTenthsC = batteryIntent.optionalInt(BatteryManager.EXTRA_TEMPERATURE),
    voltageMillivolts = batteryIntent.optionalInt(BatteryManager.EXTRA_VOLTAGE),
    status = batteryIntent.getIntExtra(
        BatteryManager.EXTRA_STATUS,
        BatteryManager.BATTERY_STATUS_UNKNOWN
    ),
    health = batteryIntent.getIntExtra(
        BatteryManager.EXTRA_HEALTH,
        BatteryManager.BATTERY_HEALTH_UNKNOWN
    ),
    plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)
)

private fun Intent.optionalInt(key: String): Int? =
    if (hasExtra(key)) getIntExtra(key, 0) else null
