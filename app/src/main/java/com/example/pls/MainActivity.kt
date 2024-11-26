package com.example.pls

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.core.content.ContextCompat
import com.example.pls.ui.theme.PlsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Funkcja wywoływana po przyznaniu uprawnień
        val onPermissionsGranted = {
            setContent {
                PlsTheme {
                    AppNavigation(context = this) // Przekazanie context do AppNavigation
                }
            }
        }

        // Sprawdzanie i żądanie uprawnień
        if (hasLocationPermissions()) {
            onPermissionsGranted()
        } else {
            requestLocationPermissions(onPermissionsGranted)
        }
    }

    // Funkcja sprawdzająca, czy lokalizacja jest dozwolona
    private fun hasLocationPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    // Funkcja żądająca uprawnień od użytkownika
    private fun requestLocationPermissions(onGranted: () -> Unit) {
        val requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                onGranted() // Uprawnienia zostały przyznane
            } else {
                // Obsłuż sytuację, gdy użytkownik odmówił uprawnień
                finish() // Opcjonalnie zamknij aplikację
            }
        }

        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}
