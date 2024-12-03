package com.example.projekt31

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SendDataScreen(navController: NavHostController) {
    var data by remember { mutableStateOf("") }
    var statusMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = data,
            onValueChange = { data = it },
            label = { Text("Wprowadź dane do wysłania") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val userId = FirebaseAuth.getInstance().currentUser?.uid

            if (userId == null) {
                Log.e("SendDataScreen", "Zaloguj się, żeby wysyłać dane")
                statusMessage = "Zaloguj się, żeby wysyłać dane"
                return@Button
            }

            Log.d("SendDataScreen", "User ID: $userId")
            if (data.isNotEmpty()) {

                // Inicjalizacja bazy danych z URL
                val database = FirebaseDatabase
                    .getInstance("https://aplikacja-3---android-projekt-default-rtdb.europe-west1.firebasedatabase.app")
                    .reference
                val dataNode = database.child("users").child(userId).child("data")

                Log.d("SendDataScreen", "Próba wysłania danych: $data")
                dataNode.push().setValue(data)
                    .addOnSuccessListener {
                        statusMessage = "Wysłano dane"
                        Log.d("SendDataScreen", "Poszło: $data")
                    }
                    .addOnFailureListener { exception ->
                        statusMessage = "Nie poszło ${exception.localizedMessage}"
                        Log.e("SendDataScreen", "Error: ${exception.localizedMessage}")
                    }
            } else {
                statusMessage = "Wprowadź dane"
                Log.w("SendDataScreen", "Brak danych")
            }
        }) {
            Text("Wyślij dane")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("receive") }) {
            Text("Przejdź do odbioru danych")
        }
        if (statusMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(statusMessage)
        }
    }
}
