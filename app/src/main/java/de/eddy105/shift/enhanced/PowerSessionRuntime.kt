package de.eddy105.shift.enhanced

/**
 * Estimates remaining battery runtime from the current session.
 * Estimates are intentionally conservative: charging sessions, invalid
 * telemetry, and the protected reserve return null rather than presenting
 * a misleading value.
 */
fun PowerSessionMetrics.estimatedRuntimeMinutes(currentCapacityPercent: Int?): Long? {
    val drainRate = batteryDrainPercentPerHour ?: return null
    if (drainRate <= 0.0 || currentCapacityPercent == null || currentCapacityPercent !in 1..100) {
        return null
    }

    val usableCapacityPercent = currentCapacityPercent - MINIMUM_RESERVE_PERCENT
    if (usableCapacityPercent <= 0) return null

    return ((usableCapacityPercent / drainRate) * 60.0).toLong()
}

private const val MINIMUM_RESERVE_PERCENT = 10
