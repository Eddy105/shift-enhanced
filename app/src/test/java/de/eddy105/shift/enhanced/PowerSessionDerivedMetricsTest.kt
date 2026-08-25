package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PowerSessionDerivedMetricsTest {
    @Test
    fun derivesCurrentRangeCapacityDeltaAndTemperatureAverage() {
        val session = PowerSession()
            .addSample(PowerSample(1_000, -400, capacityPercent = 80, temperatureCelsius = 30.0))
            .addSample(PowerSample(2_000, -900, capacityPercent = 79, temperatureCelsius = 32.0))
            .addSample(PowerSample(3_000, -600, capacityPercent = 78, temperatureCelsius = 31.0))

        val metrics = session.metrics()

        assertEquals(3, metrics.sampleCount)
        assertEquals(-900, metrics.minimumCurrentMilliamps)
        assertEquals(-400, metrics.maximumCurrentMilliamps)
        assertEquals(-2, metrics.capacityDeltaPercent)
        assertEquals(31.0, metrics.averageTemperatureCelsius, 0.001)
    }

    @Test
    fun omitsDerivedValuesWhenOptionalTelemetryIsUnavailable() {
        val session = PowerSession()
            .addSample(PowerSample(1_000, 200))
            .addSample(PowerSample(2_000, 300))

        val metrics = session.metrics()

        assertEquals(2, metrics.sampleCount)
        assertEquals(200, metrics.minimumCurrentMilliamps)
        assertEquals(300, metrics.maximumCurrentMilliamps)
        assertNull(metrics.capacityDeltaPercent)
        assertNull(metrics.averageTemperatureCelsius)
    }
}
