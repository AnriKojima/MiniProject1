package com.anri.discount.screen

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import java.text.NumberFormat
import java.util.*

fun openWebPage(context: android.content.Context, url: String) {
    val webpage: Uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, webpage)
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    }
}

@Composable
fun DiscountCalculator(navController: NavHostController) {
    var priceInput by remember { mutableStateOf("") }
    var discountInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Diskon Kalkulator", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = priceInput,
            onValueChange = {
                priceInput = it
                errorMessage = null
            },
            label = { Text("Harga (Rp)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = discountInput,
            onValueChange = {
                discountInput = it
                errorMessage = null
            },
            label = { Text("Diskon (%)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val price = priceInput.toDoubleOrNull()
                val discount = discountInput.toDoubleOrNull()

                when {
                    priceInput.isBlank() || discountInput.isBlank() -> {
                        result = null
                        errorMessage = "Data belum terisi."
                    }
                    price == null || discount == null || discount !in 0.0..100.0 -> {
                        result = null
                        errorMessage = "Input tidak valid. Masukkan angka yang benar."
                    }
                    else -> {
                        val discountedPrice = price - (price * discount / 100)
                        result = discountedPrice
                        errorMessage = null
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Hitung")
        }

        result?.let {
            val formatted = NumberFormat.getNumberInstance(Locale("in", "ID")).format(it)
            Text("Harga setelah diskon: Rp $formatted")
        }

        errorMessage?.let {
            Text(
                it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = { navController.navigate("about") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Info")
        }

        Button(
            onClick = {
                val shareText = result?.let {
                    val formatted = NumberFormat.getNumberInstance(Locale("in", "ID")).format(it)
                    "Harga setelah diskon adalah Rp $formatted"
                } ?: "Belum ada hasil yang bisa dibagikan."

                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Bagikan hasil melalui")
                context.startActivity(shareIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Bagikan")
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DiscountCalculatorPreview() {
    DiscountCalculator(navController = rememberNavController())
}
