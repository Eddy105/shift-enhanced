package de.eddy105.shift.enhanced

/**
 * Estimates remaining battery runtime from the current session.
 * Estimates are intentionally conservative: charging sessions, insufficient
 * samples, and invalid telemetry return null rather than presenting a
 * misleading value.
 */
fun PowerSessionMetrics.estimatedRuntimeMinutes(currentCapacityPercent: Int?): Long? {
    val drainRate = batteryDrainPercentPerHour ?: return null
    if (sampleCount < 2 || !drainRate.isFinite() || drainRate <= 0.0 || currentCapacityPercent == null || currentCapacityPercent !in 1..100) {
        return null
    }

    return ((currentCapacityPercent / drainRate) * 60.0).toLong()
}
