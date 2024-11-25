package com.example.newsapp.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.compose.NewsAppTheme
import com.example.newsapp.destination.Bookmark
import com.example.newsapp.destination.Bottom
import com.example.newsapp.destination.Details
import com.example.newsapp.destination.Home
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.bottom.BottomScreen
import com.example.newsapp.presentation.details.DetailScreen
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

                    Text(text = "", modifier = Modifier.padding(paddingValues))

                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Bottom) {
                        composable<Bottom> {
                           BottomScreen(paddingValues,navController)
                        }
                        composable<Details> {
                            val route = it.toRoute<Details>()
                            DetailScreen(id = route.id,navController, navigation = route.navigation, bookmark = route.bookmark)
                        }

                    }
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
