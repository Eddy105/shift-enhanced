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
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShiftEnhancedApp()
        }
    }
}

@Composable
private fun ShiftEnhancedApp() {
    val batteryManager = remember { androidx.compose.ui.platform.LocalContext.current.getSystemService(BatteryManager::class.java) }
    val capacity = batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY) ?: 0
    val current = batteryManager?.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW) ?: 0

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("SHIFT Enhanced", style = MaterialTheme.typography.headlineMedium)
                Text("Power Foundation", style = MaterialTheme.typography.titleMedium)
                Text("Battery: $capacity%")
                Text("Current: ${current / 1000} mA")
                Text("Battery telemetry is read locally on the device.")
            }
        }
    }
}
