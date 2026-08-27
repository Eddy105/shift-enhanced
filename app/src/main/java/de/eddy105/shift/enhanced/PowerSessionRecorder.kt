package de.eddy105.shift.enhanced

/**
 * Builds an in-memory session from the latest live telemetry sample.
 * The recorder deliberately keeps no storage and performs no background work.
 */
class PowerSessionRecorder(
    private val clockMillis: () -> Long = { System.currentTimeMillis() }
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
