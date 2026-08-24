package de.eddy105.shift.enhanced

import android.os.BatteryManager
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ShiftEnhancedApp() }
    }
}

@Composable
private fun ShiftEnhancedApp() {
    val context = LocalContext.current
    val batteryManager = remember(context) {
        context.getSystemService(BatteryManager::class.java)
    }
    val telemetry = remember(context, batteryManager) {
        batteryManager?.let { readPowerTelemetry(context, it) }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("SHIFT Enhanced", style = MaterialTheme.typography.headlineMedium)
                Text("Power Foundation", style = MaterialTheme.typography.titleMedium)
                Text("Battery: ${telemetry?.capacityPercent ?: 0}%")
                Text("Current: ${telemetry?.currentMilliamps ?: 0} mA")
                telemetry?.temperatureCelsius?.let { Text("Temperature: %.1f °C".format(it)) }
                telemetry?.voltageMillivolts?.let { Text("Voltage: $it mV") }
                Text(if (telemetry?.isCharging == true) "Status: Charging" else "Status: Not charging")
                Text("Battery telemetry is read locally on the device.")
            }
        }
    }
}
