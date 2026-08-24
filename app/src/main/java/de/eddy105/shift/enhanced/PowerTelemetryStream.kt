package de.eddy105.shift.enhanced

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext

@Composable
fun rememberPowerTelemetry(): PowerTelemetry? {
    val context = LocalContext.current
    val batteryManager = remember(context) {
        context.getSystemService(BatteryManager::class.java)
    }
    var batteryIntent by remember(context) {
        mutableStateOf<BatteryIntentState>(BatteryIntentState.Unknown)
    }

    DisposableEffect(context) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(receiverContext: Context, intent: Intent) {
                batteryIntent = BatteryIntentState.Available(Intent(intent))
            }
        }
        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val initialIntent = context.registerReceiver(null, filter)
        batteryIntent = initialIntent?.let { BatteryIntentState.Available(Intent(it)) }
            ?: BatteryIntentState.Unavailable
        context.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        onDispose { context.unregisterReceiver(receiver) }
    }

    return when (val state = batteryIntent) {
        is BatteryIntentState.Available -> batteryManager?.let {
            readPowerTelemetry(it, state.intent)
        }
        BatteryIntentState.Unknown,
        BatteryIntentState.Unavailable -> null
    }
}

private sealed interface BatteryIntentState {
    data object Unknown : BatteryIntentState
    data object Unavailable : BatteryIntentState
    data class Available(val intent: Intent) : BatteryIntentState
}
