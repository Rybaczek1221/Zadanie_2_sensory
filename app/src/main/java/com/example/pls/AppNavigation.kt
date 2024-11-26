package com.example.pls

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(context: Context) {
    // Tworzymy kontroler nawigacji, który zarządza trasami między ekranami
    val navController = rememberNavController()

    // Deklaracja NavHost, który łączy kontroler z trasami
    NavHost(
        navController = navController, // Podłączenie kontrolera
        startDestination = "home" // Ustawienie ekranu początkowego
    ) {
        // Definicja ekranu Home (ścieżka "home")
        composable("home") {
            HomeScreen(navController) // Wywołanie funkcji HomeScreen z kontrolerem
        }

        // Definicja ekranu Details z parametrem tekstowym (ścieżka "details/{text}")
        composable("details/{text}") { backStackEntry ->
            // Pobranie parametru tekstowego z trasy (jeśli brak, ustawiamy wartość domyślną)
            val text = backStackEntry.arguments?.getString("text") ?: "Default Text"
            DetailsScreen(navController, text) // Wywołanie funkcji DetailsScreen z tekstem
        }

        // Definicja ekranu Settings (ścieżka "settings")
        composable("settings") {
            SettingsScreen(navController) // Wywołanie funkcji SettingsScreen z kontrolerem
        }

        // Definicja ekranu Kompas (ścieżka "kompas")
        composable("kompas") {
            CompassScreen(navController) // Wywołanie funkcji CompassScreen z kontrolerem
        }

        // Definicja ekranu Luksometr (ścieżka "light_sensor")
        composable("light_sensor") {
            LightSensorScreen(context) // Wywołanie funkcji LightSensorScreen
        }
    }
}
