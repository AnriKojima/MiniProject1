package com.anri.discount.navigation


sealed class Screen(val route: String) {
        data object Home: Screen("mainScreen")
        data object About: Screen("aboutScreen")
    }
