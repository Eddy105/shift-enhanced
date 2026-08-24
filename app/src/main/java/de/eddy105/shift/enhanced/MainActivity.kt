package de.eddy105.shift.enhanced

import android.content.Intent
import android.content.IntentFilter
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
    val batteryIntent = remember(context) {
        context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }
    val telemetry = remember(batteryManager, batteryIntent) {
        if (batteryManager != null && batteryIntent != null) {
            readPowerTelemetry(batteryManager, batteryIntent)
        } else {
            null
        }
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
                Text("Temperature: ${telemetry?.temperatureCelsius?.let { "%.1f °C".format(it) } ?: "Unavailable"}")
                Text("Voltage: ${telemetry?.voltageVolts?.let { "%.2f V".format(it) } ?: "Unavailable"}")
                Text("Charging: ${if (telemetry?.isCharging == true) "Yes" else "No"}")
                Text("Battery telemetry is read locally on the device.")
            }
        }
    }
}
