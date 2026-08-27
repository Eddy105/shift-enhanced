package de.eddy105.shift.enhanced

/**
 * A lightweight in-memory power session built from telemetry samples.
 * Samples are intentionally not persisted by this layer.
 */
data class PowerSample(
    val timestampMillis: Long,
    val currentMilliamps: Int,
    val capacityPercent: Int? = null,
    val temperatureCelsius: Double? = null
)

data class PowerSession(
    val samples: List<PowerSample> = emptyList()
) {
    fun addSample(sample: PowerSample): PowerSession =
        copy(samples = samples + sample)

    val durationMillis: Long
        get() = if (samples.size < 2) 0 else samples.last().timestampMillis - samples.first().timestampMillis

    val averageCurrentMilliamps: Int?
        get() = samples.takeIf { it.isNotEmpty() }
            ?.map(PowerSample::currentMilliamps)
            ?.average()
            ?.toInt()
}
