package com.example.pls

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.lerp

@Composable
fun LightSensorScreen(context: Context) {
    val sensorManager = remember { context.getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    val lightSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) }

    // Stan do przechowywania poziomu światła
    var lightLevel by remember { mutableStateOf(0f) }

    val lightSensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    lightLevel = it.values[0]
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    // Rejestracja i wyrejestrowanie listenera
    DisposableEffect(Unit) {
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(lightSensorListener)
        }
    }

    // Dynamiczne tło zależne od wartości luksów (zakres normalizacji do max 250 lx)
    val backgroundColor = remember(lightLevel) {
        // Im więcej luksów, tym jaśniejszy kolor tła (max 250 lx).
        val normalizedLux = (lightLevel / 250f).coerceIn(0f, 1f) // Normalizacja dla zakresu 0 - 250
        lerp(Color.Black, Color.Yellow, normalizedLux) // Przejście od czerni do żółci
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Dynamiczne tło
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Stały Box na wyświetlanie wartości luksów
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.8f)) // Białe tło z przezroczystością
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Jasność: ${lightLevel.toInt()} lx", fontSize = 24.sp, color = Color.Black)
        }
    }
}
