package com.ohmz.hackerrankjsonuserapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ohmz.hackerrankjsonuserapp.ui.theme.HackerRankJsonUserAppTheme


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