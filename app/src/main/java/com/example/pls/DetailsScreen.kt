@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pls

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun DetailsScreen(navController: NavController, text: String) {
    // Scaffold - główna struktura ekranu, która pozwala definiować top bar i zawartość
    Scaffold(
        topBar = {
            // TopAppBar - pasek u góry ekranu z tytułem i ikoną nawigacji
            TopAppBar(
                title = { Text(text = "Przesyłanie danych") }, // Tytuł ekranu
                navigationIcon = {
                    // Przycisk z ikoną powrotu
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, // Ikona powrotu
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { padding ->
            // Box - kontener zajmujący całą dostępną przestrzeń
            Box(
                modifier = Modifier
                    .fillMaxSize() // Wypełnia cały ekran
                    .padding(padding), // Uwzględnia padding od Scaffold
                contentAlignment = Alignment.Center // Centruje zawartość
            ) {
                // Kolumna do ułożenia elementów jeden pod drugim
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Wyświetlenie przekazanego tekstu
                    Text(text = "Tekst pod obrazkiem: $text")

                    Spacer(modifier = Modifier.height(16.dp)) // Odstęp między elementami

                    // Przycisk powrotu do ekranu Home
                    Button(onClick = { navController.navigate("home") }) {
                        Text(text = "Go to Home")
                    }
                }
            }
        }
    )
}
