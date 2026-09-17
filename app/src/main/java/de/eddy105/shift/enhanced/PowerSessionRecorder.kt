package de.eddy105.shift.enhanced

import android.os.SystemClock

/**
 * Builds an in-memory session from the latest live telemetry sample.
 * The recorder deliberately keeps no storage and performs no background work.
 * Session timestamps use elapsed realtime so wall-clock changes cannot distort
 * session duration or battery drain-rate calculations.
 */
class PowerSessionRecorder(
    private val clockMillis: () -> Long = { SystemClock.elapsedRealtime() }
) {
    var session: PowerSession = PowerSession()
        private set

    fun record(telemetry: PowerTelemetry): PowerSession {
        session = session.addSample(
            PowerSample(
                timestampMillis = clockMillis(),
                currentMilliamps = telemetry.currentMilliamps,
                capacityPercent = telemetry.capacityPercent,
                temperatureCelsius = telemetry.temperatureCelsius
            )
        )
        return session
    }

    fun reset() {
        session = PowerSession()
    }
}
