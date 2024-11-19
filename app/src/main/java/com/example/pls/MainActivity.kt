package com.example.pls

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pls.ui.theme.PlsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlsTheme {
                AppNavigation() // Uruchomienie nawigacji
            }
        }
    }
}
