package com.example.newsapp.presentation.bookmark.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.newsapp.destination.Details

import com.example.newsapp.domain.model.NewsBookmarkList

@Composable
fun BookmarkItem(
    bookmark:NewsBookmarkList,
    navController: NavHostController,
){
    val image = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(bookmark.image)
            .size(Size.ORIGINAL)
            .build()

    ).state


    Row (
        modifier = Modifier
            .padding(2.dp)
            .height(131.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )

            .clickable {
                navController.navigate(Details(bookmark.id,false,true))
            }

    ) {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                .width(211.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {

            Text(
                text = bookmark.text,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3

            )
            Spacer(modifier = Modifier.height(15.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ){
                Text(
                    text = "\uD83D\uDCF0${bookmark.author}",
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = bookmark.publish_date,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

        }


        Box(modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer),
            contentAlignment = Alignment.Center
        ){
            if (image is AsyncImagePainter.State.Success){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = image.painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

            if (image is AsyncImagePainter.State.Loading){
                CircularProgressIndicator()
            }
            if (image is AsyncImagePainter.State.Error){
                Image(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }

        }
    }

}