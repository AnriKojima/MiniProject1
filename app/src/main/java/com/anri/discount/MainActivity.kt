package com.anri.discount

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anri.discount.navigation.AboutScreen
import com.anri.discount.screen.DiscountCalculator
import com.anri.discount.ui.theme.DiscountTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiscountTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            DiscountCalculator(navController)
                        }
                        composable("about") {
                            AboutScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
