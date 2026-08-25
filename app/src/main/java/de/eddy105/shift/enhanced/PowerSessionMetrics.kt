package de.eddy105.shift.enhanced

/**
 * Derived, in-memory metrics for the current power session.
 * No persistence or background processing is performed here.
 */
data class PowerSessionMetrics(
    val sampleCount: Int,
    val capacityDeltaPercent: Int?,
    val minimumCurrentMilliamps: Int?,
    val maximumCurrentMilliamps: Int?,
    val averageTemperatureCelsius: Double?
)

fun PowerSession.metrics(): PowerSessionMetrics {
    if (samples.isEmpty()) {
        return PowerSessionMetrics(0, null, null, null, null)
    }

    val capacities = samples.mapNotNull(PowerSample::capacityPercent)
    val temperatures = samples.mapNotNull(PowerSample::temperatureCelsius)
    val currents = samples.map(PowerSample::currentMilliamps)

    return PowerSessionMetrics(
        sampleCount = samples.size,
        capacityDeltaPercent = if (capacities.size >= 2) capacities.last() - capacities.first() else null,
        minimumCurrentMilliamps = currents.minOrNull(),
        maximumCurrentMilliamps = currents.maxOrNull(),
        averageTemperatureCelsius = temperatures.takeIf { it.isNotEmpty() }?.average()
    )
}
