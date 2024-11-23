package com.example.newsapp.presentation.bottom

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.newsapp.destination.Bookmark
import com.example.newsapp.destination.Bottom
import com.example.newsapp.destination.Details
import com.example.newsapp.destination.Home
import com.example.newsapp.presentation.bookmark.BookmarkScreen
import com.example.newsapp.presentation.details.DetailScreen
import com.example.newsapp.presentation.home.HomeScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomScreen(
paddingValues: PaddingValues,
) {
    val bottomNavController = rememberNavController()



    Scaffold(
    bottomBar = {

        BottomNavigationBar(bottomNavController = bottomNavController,paddingValues)
    }
    ) {
        paddingValue->
        NavHost(navController = bottomNavController , startDestination = Home) {
            composable<Home> {
                HomeScreen(paddingValues = paddingValue,bottomNavController)
            }
            composable<Bookmark> {
                BookmarkScreen()
            }
            
            composable<Details> { 
                val args = it.toRoute<Details>()
                DetailScreen(id = args.id,bottomNavController)
            }


        }

    }
}
@Composable
fun BottomNavigationBar(bottomNavController: NavHostController,paddingValues: PaddingValues) {
    Surface(
        tonalElevation = 5.dp,
        shadowElevation = 8.dp
    ) {
    NavigationBar(
        modifier = Modifier.wrapContentHeight()
    ) {
        val items = listOf(
            BottomListItems(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home
            ),
            BottomListItems(
                title = "Bookmarks",
                selectedIcon = Icons.Filled.Bookmarks,
                unSelectedIcon = Icons.Outlined.Bookmarks
            )

        )
        var selected = rememberSaveable {
            mutableIntStateOf(0)
        }

        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            items.forEachIndexed { index, bottomListItems ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index

                        when (selected.intValue) {
                            0 -> {
                                bottomNavController.navigate(Home) {
                                    bottomNavController.popBackStack()
                                }

                            }

                            1 -> {
                                bottomNavController.navigate(Bookmark) {
                                    bottomNavController.popBackStack()
                                }
                            }
                        }

                    },
                    icon = {
                        Icon(
                            imageVector = if (selected.intValue == index) {
                                bottomListItems.selectedIcon
                            } else {
                                bottomListItems.unSelectedIcon
                            },
                            contentDescription = null
                        )
                    },
                    label = {
                        when (index) {
                            0 -> Text(text = "Home")
                            1 -> Text(text = "Bookmark")
                        }
                    }
                )
            }


        }
    }
}

}






