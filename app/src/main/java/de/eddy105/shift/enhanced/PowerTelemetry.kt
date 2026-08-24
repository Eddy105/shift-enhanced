package de.eddy105.shift.enhanced

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

/** A device-local snapshot of the battery state used by the Power Center. */
data class PowerTelemetry(
    val capacityPercent: Int,
    val currentMicros: Int,
    val temperatureTenthsCelsius: Int?,
    val voltageMillivolts: Int?,
    val status: Int?,
    val health: Int?
) {
    val currentMilliamps: Int
        get() = currentMicros / 1000

    val temperatureCelsius: Double?
        get() = temperatureTenthsCelsius?.div(10.0)

    val isCharging: Boolean
        get() = status == BatteryManager.BATTERY_STATUS_CHARGING ||
            status == BatteryManager.BATTERY_STATUS_FULL
}

fun readPowerTelemetry(context: Context, manager: BatteryManager): PowerTelemetry {
    val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    return PowerTelemetry(
        capacityPercent = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY),
        currentMicros = manager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW),
        temperatureTenthsCelsius = intent?.readIntExtra(BatteryManager.EXTRA_TEMPERATURE),
        voltageMillivolts = intent?.readIntExtra(BatteryManager.EXTRA_VOLTAGE),
        status = intent?.readIntExtra(BatteryManager.EXTRA_STATUS),
        health = intent?.readIntExtra(BatteryManager.EXTRA_HEALTH)
    )
}

private fun Intent.readIntExtra(name: String): Int? =
    if (hasExtra(name)) getIntExtra(name, Int.MIN_VALUE).takeUnless { it == Int.MIN_VALUE } else null
