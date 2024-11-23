package com.example.newsapp.presentation.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsapp.data.local.database.NewsDatabase
import com.example.newsapp.presentation.home.component.NewsItems
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    paddingValues:PaddingValues,
    navController:NavHostController
){
    val viewModel = hiltViewModel<HomeViewModel>()

    val newsListState = viewModel.newsListState.collectAsState().value

    val isLoading = viewModel.isLoading.collectAsState().value

    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    val pullToRefreshState = rememberPullToRefreshState()

    val coroutineScope = rememberCoroutineScope()

    var isRefreshing by rememberSaveable {
        mutableStateOf(false)
    }









    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(paddingValues)
            .fillMaxSize()
            .padding(top = 30.dp, start = 10.dp, end = 10.dp)
    ) {
        var onSearch by rememberSaveable {
            mutableStateOf("")
        }

        Text(
            text = "Today✨",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            modifier = Modifier.alpha(0.5f),
            text = "What you want to read?",
            fontSize = 12.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(24.dp))

        OnSearchBar(text = onSearch, onSearch = {
            onSearch = it
            viewModel.getNews(it)
        })

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Trending News🔥",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(20.dp))


        Box(
            modifier = Modifier
                .fillMaxSize()
        ){

            if (isLoading){
                LazyColumn(Modifier.fillMaxSize()) {
                    items(newsListState.newsList.size){
                        ShimmerItem(shimmerInstance = shimmerInstance)
                    }

                }
            }else{

                PullToRefreshBox(
                    state = pullToRefreshState ,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        coroutineScope.launch {
                            isRefreshing = true
                            delay(2000)
                            onSearch = ""
                            viewModel.onRefresh()
                            isRefreshing = false
                        }
                })
                {
                    LazyColumn(
                        state = rememberLazyListState()

                    ) {
                        items(count = newsListState.newsList.size, key = {
                            newsListState.newsList[it].id
                        }){
                                count->
                            NewsItems(
                                item = newsListState.newsList[count],
                                id=newsListState.newsList[count].id,
                                navController
                            )
                        }
                    }
                }







            }
        }

    }

}


@Composable
fun ShimmerItem(shimmerInstance: Shimmer) {

    Row(
        modifier = Modifier
            .padding(2.dp)
            .height(131.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                .width(211.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .shimmer(shimmerInstance)
                    .background(MaterialTheme.colorScheme.inversePrimary)

            )
            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    text = "",
                    modifier = Modifier
                        .width(100.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .shimmer(shimmerInstance)
                        .background(MaterialTheme.colorScheme.inversePrimary)

                )
            }

        }


        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(14.dp))
                .shimmer(shimmerInstance)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            contentAlignment = Alignment.Center
        ) {

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnSearchBar(text: String, onSearch: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(color = Color.White)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { it ->
                onSearch(it)
            },
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = "Search,news,etc...",
                    textAlign = TextAlign.Start
                )
            },
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(start = 12.dp),
                    imageVector = Icons.Rounded.Search, contentDescription = "Search"
                )
            },
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .padding(2.dp)
                            .clickable {
                                onSearch("")
                            },
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Search"
                    )
                }
            },
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

            )
        }
}
