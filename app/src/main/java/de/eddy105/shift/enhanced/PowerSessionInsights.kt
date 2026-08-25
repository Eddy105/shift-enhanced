package de.eddy105.shift.enhanced

/**
 * Derived, in-memory insights for the current power session.
 * No persistence or background processing is performed here.
 */
data class PowerSessionInsights(
    val sampleCount: Int,
    val capacityDeltaPercent: Int?,
    val minimumCurrentMilliamps: Int?,
    val maximumCurrentMilliamps: Int?,
    val averageTemperatureCelsius: Double?
)

fun PowerSession.insights(): PowerSessionInsights {
    if (samples.isEmpty()) {
        return PowerSessionInsights(0, null, null, null, null)
    }

    val currents = samples.map(PowerSample::currentMilliamps)
    return PowerSessionInsights(
        sampleCount = samples.size,
        capacityDeltaPercent = null,
        minimumCurrentMilliamps = currents.minOrNull(),
        maximumCurrentMilliamps = currents.maxOrNull(),
        averageTemperatureCelsius = null
    )
}
