package com.example.projekt1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {

    // Delay na 5 sekund
    LaunchedEffect(Unit) {
        delay(5000)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true } // Usuwa splash screen ze stosu
        }
    }


   //UI
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Jakub Żądeł \n " +
                        "Nr indeksu: 275795 \n\n " +
                        "Projekt 1 \n\n " +
                        "19.11.2024",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}
