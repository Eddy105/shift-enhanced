package de.eddy105.shift.enhanced

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PowerSessionRuntimeTest {
    @Test
    fun estimatesRemainingRuntimeFromPositiveDrainRate() {
        val metrics = PowerSessionMetrics(
            sampleCount = 3,
            capacityDeltaPercent = -2,
            minimumCurrentMilliamps = -900,
            maximumCurrentMilliamps = -400,
            averageTemperatureCelsius = 30.0,
            batteryDrainPercentPerHour = 2.0
        )

        assertEquals(1_500L, metrics.estimatedRuntimeMinutes(50))
    }

    @Test
    fun doesNotEstimateRuntimeWhenChargingOrCapacityIsMissing() {
        val charging = PowerSessionMetrics(1, 1, -500, -500, null, -1.0)
        val unknownCapacity = PowerSessionMetrics(1, -1, -500, -500, null, 1.0)

        assertNull(charging.estimatedRuntimeMinutes(80))
        assertNull(unknownCapacity.estimatedRuntimeMinutes(null))
    }

    @Test
    fun doesNotEstimateRuntimeForEmptyBattery() {
        val metrics = PowerSessionMetrics(2, -1, -500, -500, null, 1.0)

        assertNull(metrics.estimatedRuntimeMinutes(0))
    }
}
