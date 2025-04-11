package com.anri.discount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DiscountCalculator()
                }
            }
        }
    }
}

@Composable
fun DiscountCalculator() {
    var priceInput by remember { mutableStateOf("") }
    var discountInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Diskon Kalkulator", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = priceInput,
            onValueChange = { priceInput = it },
            label = { Text("Harga (Rp)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = discountInput,
            onValueChange = { discountInput = it },
            label = { Text("Diskon (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val price = priceInput.toDoubleOrNull()
                val discount = discountInput.toDoubleOrNull()
                if (price != null && discount != null && discount in 0.0..100.0) {
                    val discountedPrice = price - (price * discount / 100)
                    result = discountedPrice
                } else {
                    result = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hitung")
        }

        result?.let {
            val formatted = NumberFormat.getNumberInstance(Locale("in", "ID")).format(it)
            Text("Harga setelah diskon: Rp $formatted")
        } ?: run {
            if (priceInput.isNotBlank() && discountInput.isNotBlank()) {
                Text(
                    "Input tidak valid. Coba lagi.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}