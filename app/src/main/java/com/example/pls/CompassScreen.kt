package com.example.pls

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image


@Composable
fun CompassScreen(navController: NavHostController) {
    val context = LocalContext.current // Pobranie kontekstu z Compose
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val accelerometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    val magnetometer = remember { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }

    // States to store sensor data
    var azimuth by remember { mutableStateOf(0f) }
    val accelerometerData = FloatArray(3)
    val magnetometerData = FloatArray(3)
    val rotationMatrix = FloatArray(9)
    val orientationAngles = FloatArray(3)

    val sensorEventListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                when (event?.sensor?.type) {
                    Sensor.TYPE_ACCELEROMETER -> {
                        System.arraycopy(event.values, 0, accelerometerData, 0, event.values.size)
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> {
                        System.arraycopy(event.values, 0, magnetometerData, 0, event.values.size)
                    }
                }
                SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetometerData)
                SensorManager.getOrientation(rotationMatrix, orientationAngles)
                azimuth = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    // Register listeners
    DisposableEffect(Unit) {
        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(sensorEventListener, magnetometer, SensorManager.SENSOR_DELAY_UI)

        onDispose {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    // Wywołanie UI kompasu
    CompassUI(azimuth)
}


// Funkcja odpowiedzialna za UI kompasu
@Composable
fun CompassUI(azimuth: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Dodatkowe wcięcie od krawędzi ekranu
        contentAlignment = Alignment.Center
    ) {
        // Róża wiatrów - kierunki świata
        Box(modifier = Modifier.fillMaxSize()) {
            // Północ (N)
            Text(
                text = "N",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Południe (S)
            Text(
                text = "S",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Wschód (E)
            Text(
                text = "E",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            // Zachód (W)
            Text(
                text = "W",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        // Obracająca się igła kompasu
        Image(
            painter = painterResource(id = R.drawable.compass_needle),
            contentDescription = "Compass Needle",
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(rotationZ = -azimuth) // Obrót na podstawie azymutu
        )



    }
}
