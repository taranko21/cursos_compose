package com.example.bluetooth_practice

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bluetooth_practice.ui.theme.Bluetooth_practiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Bluetooth_practiceTheme {
                Surface {
                    BluetootScreen()
                }
            }
        }
    }
}

@Composable
fun BluetootScreen(){
    val context = LocalContext.current
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val permissions = arrayOf(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.BLUETOOTH_ADMIN,
        android.Manifest.permission.BLUETOOTH_SCAN,
        android.Manifest.permission.BLUETOOTH_ADVERTISE,
        android.Manifest.permission.BLUETOOTH_CONNECT
    )
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.all { it.value }
        if(allGranted){
            Toast.makeText(context, "All permissions granted", Toast.LENGTH_SHORT).show()
            scanDevices(bluetoothAdapter)
        }else{
            Toast.makeText(context, "Permissions not granted", Toast.LENGTH_SHORT).show()
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {}) {
            Text(text = "Enable Bluetooth")
        }
    }
}

@SuppressLint("MissingPermission")
fun scanDevices(adapter: BluetoothAdapter) {
    if(adapter== null || !adapter.isEnabled){
        println("Bluetooth is not enabled")
        return
    }
    val devices = adapter.bondedDevices
    for(device in devices){
        println("Device: ${device.name} - ${device.address}")
    }
}
