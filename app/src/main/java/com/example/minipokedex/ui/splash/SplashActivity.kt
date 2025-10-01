package com.example.minipokedex.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.delay
import com.example.minipokedex.ui.list.MainActivity
import com.example.minipokedex.ui.theme.Theme

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Theme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "MiniPokedex",
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold
                    )

                    LaunchedEffect(Unit) {
                        delay(2000)
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}