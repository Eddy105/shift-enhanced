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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShiftEnhancedApp() }
    }
}

@androidx.compose.runtime.Composable
private fun ShiftEnhancedApp() {
    val telemetry = rememberPowerTelemetry()

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
                Text("Battery telemetry updates automatically when the system reports a battery change.")
            }
        }
    }
}
