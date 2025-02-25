package com.ohmz.hackerrankjsonuserapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ohmz.hackerrankjsonuserapp.ui.theme.HackerRankJsonUserAppTheme
import com.ohmz.hackerrankjsonuserapp.ui.theme.MainScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HackerRankJsonUserAppTheme {
                MainScreen()
            }
        }
    }
}