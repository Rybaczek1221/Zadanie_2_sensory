package com.example.pls

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pls.ui.theme.PlsTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Wyświetlanie SplashScreen
        setContent {
            PlsTheme {
                SplashScreen {
                    // Po 3 sekundach przejście do MainActivity
                    startActivity(Intent(this, MainActivity::class.java))
                    finish() // Zamykanie SplashActivity
                }
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    // Korutyna symulująca opóźnienie SplashScreen
    LaunchedEffect(Unit) {
        delay(3000) // Opóźnienie 3 sekundy
        onTimeout() // Wywołanie funkcji przejścia
    }

    // UI SplashScreen
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Wyświetlanie logo aplikacji
            Icon(
                painter = painterResource(id = R.drawable.kotwica_foreground), // Sprawdź, czy plik ying istnieje
                contentDescription = "6 HDŻ",
                modifier = Modifier.size(128.dp),
                tint = Color.Unspecified // Usunięcie domyślnego koloru Material
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tytuł aplikacji
            Text(
                text = "Aplikacja projektowa!",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary, // Upewnij się, że kolory są zdefiniowane
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Pasek ładowania
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary // Kolor zdefiniowany w stylu
            )
        }
    }
}
