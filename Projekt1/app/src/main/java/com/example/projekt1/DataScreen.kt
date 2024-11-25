package com.example.projekt1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun DataScreen(navController: NavHostController, inputText: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Otrzymane dane: $inputText",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge

        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Przejście do ekranu podsumowania
                navController.navigate("summary")

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Przejdź do podsumowania")
        }
    }
}
