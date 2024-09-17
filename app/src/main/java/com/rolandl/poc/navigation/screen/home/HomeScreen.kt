package com.rolandl.poc.navigation.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.imageLoader
import coil.util.DebugLogger
import com.rolandl.poc.navigation.R
import com.rolandl.poc.navigation.domain.model.Post
import com.rolandl.poc.navigation.domain.model.User
import com.rolandl.poc.navigation.ui.theme.POCNavigationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  viewModel: HomeViewModel = hiltViewModel(),
  onFABClick: () -> Unit = {},
) {
  println("NAVIGATION - HomeScreen")

  Scaffold(
    modifier = modifier,
    topBar = {
      TopAppBar(
        title = {
          Text(stringResource(id = R.string.homefeed_fragment_label))
        }
      )
    },
    floatingActionButtonPosition = FabPosition.End,
    floatingActionButton = {
      FloatingActionButton(
        onClick = {
          onFABClick()
        }
      ) {
        Icon(
          imageVector = Icons.Filled.Add,
          contentDescription = stringResource(id = R.string.description_button_add)
        )
      }
    }
  ) { contentPadding ->
    val posts by viewModel.posts.collectAsStateWithLifecycle()
    
    HomefeedList(
      modifier = modifier.padding(contentPadding),
      posts = posts
    )
  }
}

@Composable
private fun HomefeedList(
  modifier: Modifier = Modifier,
  posts: List<Post>
) {
  LazyColumn(
    modifier = modifier.padding(8.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    items(posts) { post ->
      HomefeedCell(
        post = post
      )
    }
  }
}

@Composable
private fun HomefeedCell(
  post: Post
) {
  ElevatedCard(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.padding(8.dp),
    ) {
      Text(
        text = stringResource(
          id = R.string.by,
          post.author?.firstname ?: "",
          post.author?.lastname ?: ""
        ),
        style = MaterialTheme.typography.titleSmall
      )
      Text(
        text = post.title,
        style = MaterialTheme.typography.titleLarge
      )
      if (post.photoUrl.isNullOrEmpty() == false) {
        AsyncImage(
          modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth()
            .heightIn(max = 200.dp)
            .aspectRatio(ratio = 16 / 9f),
          model = post.photoUrl,
          imageLoader = LocalContext.current.imageLoader.newBuilder()
            .logger(DebugLogger())
            .build(),
          placeholder = ColorPainter(Color.DarkGray),
          contentDescription = "image",
          contentScale = ContentScale.Crop,
        )
      }
      if (post.description.isNullOrEmpty() == false) {
        Text(
          text = post.description,
          style = MaterialTheme.typography.bodyMedium
        )
      }
    }
  }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun HomefeedCellPreview() {
  POCNavigationTheme {
    HomefeedCell(
      post = Post(
        id = "1",
        title = "title",
        description = "description",
        photoUrl = null,
        timestamp = 1,
        author = User(
          id = "1",
          firstname = "firstname",
          lastname = "lastname"
        )
      )
    )
  }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun HomefeedCellImagePreview() {
  POCNavigationTheme {
    HomefeedCell(
      post = Post(
        id = "1",
        title = "title",
        description = null,
        photoUrl = "https://picsum.photos/id/85/1080/",
        timestamp = 1,
        author = User(
          id = "1",
          firstname = "firstname",
          lastname = "lastname"
        )
      )
    )
  }
}