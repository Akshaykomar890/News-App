package com.example.newsapp.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.newsapp.data.mapper.toNewsBookmarkList
import com.example.newsapp.data.mapper.toNewsEntity
import com.example.newsapp.destination.Bookmark
import com.example.newsapp.destination.Bottom
import com.example.newsapp.destination.Home
import com.example.newsapp.presentation.bookmark.BookmarkState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id:Int,
    navController: NavHostController,
    navigation:Boolean,
    bookmark:Boolean
){
    val viewModel = hiltViewModel<DetailsViewModel>()

    val newsIdState = viewModel.newsIdState.collectAsState().value



    if (bookmark==true) {
        viewModel.getBookmarkById(id = id)
    }

    if (bookmark==false){
        viewModel.getNewsId(id = id)
    }

    var bookmarkClicked by remember {
        mutableStateOf(false)
    }

    val news = newsIdState.newsId

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val snackBarHost = remember {
        SnackbarHostState()
    }


    var navigation by remember {
        mutableStateOf(navigation)
    }

    Scaffold(
        topBar = {
            if (navigation == true) {
                TopAppBar(
                    modifier = Modifier.shadow(elevation = 5.dp),
                    title = {  },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.navigate(Bottom){
                                navController.popBackStack()
                            } }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBackIosNew,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.addToBookmark(news!!.toNewsBookmarkList())

                            scope.launch {
                                snackBarHost.showSnackbar(
                                    message = "Bookmark saved successfully✅",
                                    duration = SnackbarDuration.Short
                                )
                            }

                        }) {
                            Icon(imageVector = Icons.Filled.Bookmark, contentDescription = null)
                        }
                        IconButton(onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsIdState.newsId?.url.toString()))
                            context.startActivity(intent)
                        }) {
                            Icon(imageVector = Icons.Filled.Public, contentDescription = null)
                        }
                    },


                    )
            }



        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHost)
        }

    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState(), enabled = true)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(15.dp),

        ) {

            val image = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(news?.image)
                    .size(Size.ORIGINAL)
                    .build()
            ).state


            Spacer(modifier = Modifier.height(10.dp))
            
            Text(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = newsIdState.newsId?.title.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "\uD83D\uDCF0${newsIdState.newsId?.author}",
                color = Color.Black,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.alpha(0.5f),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center,
            ){
                if(image is AsyncImagePainter.State.Success){
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = image.painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                if(image is AsyncImagePainter.State.Loading){
                    CircularProgressIndicator()
                }
                if(image is AsyncImagePainter.State.Error) {
                    Image(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = null,
                        contentScale = ContentScale.Fit
                    )
                }


            }

            Spacer(modifier = Modifier.height(10.dp))


            Text(
                modifier = Modifier.alpha(0.7f),
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                text = newsIdState.newsId?.author.toString(),
                fontSize = 15.sp,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.alpha(0.9f),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                text = newsIdState.newsId?.text.toString(),
                fontSize = 15.sp,
                textAlign = TextAlign.Justify
            )

        }
    }



}




