package com.example.newsapp.presentation.bookmark


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.newsapp.presentation.bookmark.component.BookmarkItem
import com.example.newsapp.presentation.home.component.NewsItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    paddingValues: PaddingValues,
    navController: NavHostController
){

    val viewModel = hiltViewModel<BookmarkViewModel>()

    val state = viewModel.newsBookmarkState.collectAsState().value



    

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.shadow(elevation = 5.dp),
                title = {
                  Text(
                      text = "Bookmarks📚",
                      fontWeight = FontWeight.SemiBold
                  )
                }
            )
        }
    ) {
        paddingValue->
        Column(
            modifier = Modifier
                .padding(paddingValues)


        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = 80.dp)
            ) {
                items(count = state.newsBookmark.size){
                    index: Int ->
                    BookmarkItem(bookmark = state.newsBookmark[index], navController = navController)
                }
            }
            Text(text = "", modifier = Modifier.padding(paddingValue))
        }

    }



}