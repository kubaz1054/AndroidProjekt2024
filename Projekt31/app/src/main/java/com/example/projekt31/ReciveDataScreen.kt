package com.example.projekt31

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

@Composable
fun ReceiveDataScreen(navController: NavHostController) {
    var receivedData by remember { mutableStateOf<List<String>>(emptyList()) }
    var statusMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Log.e("ReceiveDataScreen", "Zaloguj się, żeby odczytać dane")
            statusMessage = "Zaloguj się, żeby odczytać dane"
            return@LaunchedEffect
        }

        Log.d("ReceiveDataScreen", "User ID: $userId")
        // Inicjalizacja bazy danych z URL
        val database = FirebaseDatabase
            .getInstance("https://aplikacja-3---android-projekt-default-rtdb.europe-west1.firebasedatabase.app")
            .reference
        val dataNode = database.child("users").child(userId).child("data")

        Log.d("ReceiveDataScreen", "próba odczytu")
        dataNode.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val dataList = mutableListOf<String>()
                for (dataSnapshot in snapshot.children) {
                    val data = dataSnapshot.getValue(String::class.java)
                    if (data != null) {
                        dataList.add(data)
                        Log.d("ReceiveDataScreen", "Dane: $data")
                    }
                }
                receivedData = dataList
                statusMessage = if (dataList.isEmpty()) {
                    "Brak dostępnych danych"
                } else {
                    "Dane otrzymane "
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusMessage = "Nie udało się odczytać danych: ${error.message}"
                Log.e("ReceiveDataScreen", "Błąd: ${error.message}")
            }
        })
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Otrzymane dane:")
        Spacer(modifier = Modifier.height(8.dp))
        receivedData.forEach { data ->
            Text("- $data")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("send") }) {
            Text("Przejdź do wysyłania")
        }
        if (statusMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(statusMessage)
        }
    }
}
