package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PowerSessionDerivedMetricsTest {
    @Test
    fun derivesCurrentRangeCapacityDeltaTemperatureAndDrainRate() {
        val session = PowerSession()
            .addSample(PowerSample(1_000, -400, capacityPercent = 80, temperatureCelsius = 30.0))
            .addSample(PowerSample(1_801_000, -900, capacityPercent = 79, temperatureCelsius = 32.0))
            .addSample(PowerSample(3_601_000, -600, capacityPercent = 78, temperatureCelsius = 31.0))

        val metrics = session.metrics()

        assertEquals(3, metrics.sampleCount)
        assertEquals(-900, metrics.minimumCurrentMilliamps)
        assertEquals(-400, metrics.maximumCurrentMilliamps)
        assertEquals(-2, metrics.capacityDeltaPercent ?: error("capacity delta should be available"))
        assertEquals(31.0, metrics.averageTemperatureCelsius ?: error("average temperature should be available"), 0.001)
        assertEquals(2.0, metrics.batteryDrainPercentPerHour ?: error("drain rate should be available"), 0.001)
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
        assertNull(metrics.batteryDrainPercentPerHour)
    }

    @Test
    fun doesNotReportDrainRateForZeroDuration() {
        val session = PowerSession()
            .addSample(PowerSample(1_000, -500, capacityPercent = 70))
            .addSample(PowerSample(1_000, -600, capacityPercent = 69))

        assertNull(session.metrics().batteryDrainPercentPerHour)
    }
}
