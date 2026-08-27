package de.eddy105.shift.enhanced

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShiftEnhancedApp() }
    }
}

@Composable
private fun ShiftEnhancedApp() {
    val telemetry = rememberPowerTelemetry()
    val recorder = remember { PowerSessionRecorder() }
    var session by remember { mutableStateOf(PowerSession()) }

    LaunchedEffect(telemetry) {
        telemetry?.let { session = recorder.record(it) }
    }

    val metrics = session.metrics()

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("SHIFT Enhanced", style = MaterialTheme.typography.headlineMedium)
                Text("Power Center", style = MaterialTheme.typography.titleMedium)
                Text("Battery: ${telemetry?.capacityPercent ?: 0}%")
                Text("Current: ${telemetry?.currentMilliamps ?: 0} mA")
                Text("Temperature: ${telemetry?.temperatureCelsius?.let { "%.1f °C".format(it) } ?: "Unavailable"}")
                Text("Voltage: ${telemetry?.voltageVolts?.let { "%.2f V".format(it) } ?: "Unavailable"}")
                Text("Charging: ${if (telemetry?.isCharging == true) "Yes" else "No"}")
                Text("Session Analytics", style = MaterialTheme.typography.titleMedium)
                Text("Samples: ${metrics.sampleCount}")
                Text("Session duration: ${formatDuration(session.durationMillis)}")
                Text("Average current: ${session.averageCurrentMilliamps?.let { "$it mA" } ?: "Unavailable"}")
                Text("Current range: ${metrics.minimumCurrentMilliamps?.let { "${metrics.minimumCurrentMilliamps} to ${metrics.maximumCurrentMilliamps} mA" } ?: "Unavailable"}")
                Text("Battery change: ${metrics.capacityDeltaPercent?.let { "$it%" } ?: "Unavailable"}")
                Text("Battery drain rate: ${metrics.batteryDrainPercentPerHour?.let { "%.2f%%/h".format(it) } ?: "Unavailable"}")
                Text("Average temperature: ${metrics.averageTemperatureCelsius?.let { "%.1f °C".format(it) } ?: "Unavailable"}")
                Text("Session data is kept in memory and is not persisted.")
            }
        }
    }
}

internal fun formatDuration(durationMillis: Long): String {
    val totalSeconds = durationMillis / 1_000
    val hours = totalSeconds / 3_600
    val minutes = (totalSeconds % 3_600) / 60
    val seconds = totalSeconds % 60
    return if (hours > 0) "%dh %02dm %02ds".format(hours, minutes, seconds)
    else "%02dm %02ds".format(minutes, seconds)
}
