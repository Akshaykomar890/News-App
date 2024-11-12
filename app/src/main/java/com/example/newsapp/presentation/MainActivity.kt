package com.example.newsapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.compose.NewsAppTheme
import com.example.newsapp.presentation.bottom.BottomScreen
import com.example.newsapp.presentation.home.HomeScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {paddingValues ->
                    SystemBarColor(color = MaterialTheme.colorScheme.inverseSurface)
                   BottomScreen(
                        paddingValues = paddingValues
                   )
                }
            }
        }
    }
}

@Composable
fun SystemBarColor(color:Color){
    val ui = rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        ui.setSystemBarsColor(color)
    }

}
