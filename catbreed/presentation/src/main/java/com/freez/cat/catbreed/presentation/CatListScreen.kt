package com.freez.cat.catbreed.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.freez.cat.catbreed.domain.models.CatBreed
import com.freez.cat.core.util.Screen
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import java.util.Locale

@Composable
fun CatListScreen(
    navController: NavController,
    viewModel: CatListViewModel = hiltViewModel(),
) {
    Column {
        SearchField(viewModel = viewModel)
        Spacer(modifier = Modifier.height(5.dp))
        GridGallery(viewModel = viewModel, navController = navController)
    }
}


@Composable
fun GridGallery(
    modifier: Modifier = Modifier,
    viewModel: CatListViewModel,
    navController: NavController
) {

    val catList = viewModel.catList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .map { layoutInfo ->
                val totalItems = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
                if (lastVisibleItemIndex >= totalItems - 2) {
                    viewModel.loadMoreData()
                }
            }
            .collect()
    }
    LazyVerticalGrid(
        state = listState,
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(10.dp),
    ) {
        items(catList.value) { item ->
            GridViewItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp, bottom = 6.dp, top = 6.dp),
                catBreed = item,
                onItemClick = { cat ->
                    navController.navigate(
                        Screen.CatDetailScreen.createRoute(catId = cat.id)
                    )
                },
                onFavoriteClick = { cat ->
                    viewModel.changeFavorite(cat.id, !cat.isFavorite)
                },
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GridViewItem(
    modifier: Modifier = Modifier,
    catBreed: CatBreed,
    onItemClick: (CatBreed) -> Unit,
    onFavoriteClick: (CatBreed) -> Unit,
) {
    Card(
        modifier = modifier.fillMaxSize(),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = { onItemClick(catBreed) }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                DisplayImage(
                    id = catBreed.id,
                    imageUrl = catBreed.image.url,
                    contentDescription = catBreed.name,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(
                            topStart = 5.dp,
                            topEnd = 5.dp,
                        ),
                    ),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                CatInfo(
                    countryCode = catBreed.countryCode,
                    name = catBreed.name
                )

                IconButton(
                    onClick = { onFavoriteClick(catBreed) },
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = getFavoriteIcon(catBreed.isFavorite),
                        contentDescription = "Favorite Button",
                    )
                }

            }
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                catBreed.temperament.forEach { behaviour ->
                    Surface(
                        modifier = Modifier.padding(4.dp),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.LightGray
                    ) {
                        Text(
                            text = behaviour,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchField(modifier: Modifier = Modifier, viewModel: CatListViewModel) {
    val searchQuery = rememberSaveable { mutableStateOf("") }
//    val isInitialized = rememberSaveable { mutableStateOf(false) }

//    LaunchedEffect(Unit) { // Trigger first created
//        if (!isInitialized.value) {
//            viewModel.onSearchQueryChanged(searchQuery.value)
//            isInitialized.value = true
//        }
//    }
    OutlinedTextField(
        value = searchQuery.value,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        onValueChange = { newValue: String ->
            searchQuery.value = newValue
            viewModel.onSearchQueryChanged(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
        label = { Text("Search...") },
    )
}

@Composable
fun DisplayImage(
    id: String,
    imageUrl: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl)
            .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
            .memoryCacheKey("image-$id")
            .crossfade(true)
            .build(),
    )
    Image(
        modifier = Modifier
            .defaultMinSize(minHeight = 50.dp)
            .fillMaxWidth()
            .height(140.dp)
            .clip(RoundedCornerShape(size = 8.dp)),
        painter = painter,
        contentScale = ContentScale.Fit,
        contentDescription = contentDescription,
    )
}

@Composable
fun CatInfo(
    modifier: Modifier = Modifier,
    countryCode: String?,
    name: String?,
    avatarSize: Dp = 24.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        FlagImage(modifier = Modifier.align(Alignment.Top), countryCode, avatarSize)

        Text(
            text = name?.capitalizeFirstChar() ?: "",
            style = textStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

private fun String.capitalizeFirstChar(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase(
                Locale.US,
            )
        } else it.toString()
    }
}

@Composable
fun FlagImage(modifier: Modifier = Modifier, countryCode: String?, flagSize: Dp) {
    val url = "https://flagcdn.com/w320/${countryCode.toString().toLowerCase()}.png"
    Log.d("FlagImage", "FlagImage: $url")
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build(),
    )
    Image(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(flagSize)
            .border(0.7.dp, MaterialTheme.colorScheme.outline, CircleShape)
            .clip(CircleShape),
        painter = painter,
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}

fun getFavoriteIcon(isFavorite: Boolean): ImageVector {
    return if (isFavorite)
        Icons.Default.Favorite
    else
        Icons.Default.FavoriteBorder
}