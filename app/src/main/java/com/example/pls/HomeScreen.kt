package com.example.pls

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    // Stany dla obrazka i tekstu
    var currentImage by remember { mutableStateOf(R.drawable.jones) }
    var currentText by remember { mutableStateOf("Jones") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Wyświetlenie obrazka
            Image(
                painter = painterResource(id = currentImage),
                contentDescription = "Displayed Image",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Wyświetlenie tekstu
            Text(text = currentText)

            Spacer(modifier = Modifier.height(16.dp))

            // Przycisk zmieniający obrazek i tekst
            Button(onClick = {
                if (currentImage == R.drawable.jones) {
                    currentImage = R.drawable.yoda
                    currentText = "Yoda"
                } else {
                    currentImage = R.drawable.jones
                    currentText = "Jones"
                }
            }) {
                Text(text = "Zmiana tekstu i obrazka")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nawigacja do innych ekranów
            Button(onClick = {
                navController.navigate("details/${currentText}")
            }) {
                Text(text = "Go to text")
            }


            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = { navController.navigate("settings") }) {
                Text(text = "Numer indeksu")
            }
        }
    }
}
