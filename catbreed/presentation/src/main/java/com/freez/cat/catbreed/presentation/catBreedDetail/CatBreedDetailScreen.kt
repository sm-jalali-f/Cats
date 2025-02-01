package com.freez.cat.catbreed.presentation.catBreedDetail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.freez.cat.catbreed.presentation.catBreedList.DisplayImage
import com.freez.cat.core.util.Constant
import com.freez.cat.core.util.getFlagUrl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CatDetailScreen(
    modifier: Modifier = Modifier,
    catBreedId: String,
    imageUrl: String,
    viewModel: CatBreedDetailViewModel = hiltViewModel()
) {

    viewModel.initialize(catBreedId, imageUrl)
    val catBreed by viewModel.catBreed.collectAsStateWithLifecycle()
    val imageUrl by viewModel.imageUrl.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        DisplayImage(
            id = catBreed?.id ?: "",
            imageUrl = imageUrl ?: "",
            contentDescription = catBreed?.name ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(150.dp,500.dp)
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 5.dp,
                        topEnd = 5.dp,
                    ),
                ),
        )

        Spacer(Modifier.height(5.dp))
        LabelTextColumnInfo(
            label = "Name:",
            value = catBreed?.name ?: ""
        )
        Spacer(Modifier.height(8.dp))
        OriginPart(catBreed?.countryCode, catBreed?.origin)

        Spacer(Modifier.height(8.dp))
        LabelTextColumnInfo(
            label = "Description:",
            value = catBreed?.description ?: ""
        )

        Spacer(Modifier.height(8.dp))
        Temperament(catBreed?.temperament)

    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Temperament(temperament: List<String>?) {
    LabelText(textValue = "Temperament:")

    Spacer(Modifier.height(4.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
    ) {
        temperament?.forEach { tag ->
            Surface(
                modifier = Modifier.padding(4.dp),
                shape = RoundedCornerShape(16.dp),
                color = Color.LightGray
            ) {
                Text(
                    text = tag,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun LabelText(modifier: Modifier = Modifier, textValue: String) {
    Text(
        modifier = modifier,
        text = textValue,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun OriginPart(countryCode: String?, origin: String?) {
    Column {
        LabelText(textValue = "Origin:")
        Spacer(Modifier.height(4.dp))

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(countryCode.getFlagUrl())
                .size(coil3.size.Size.ORIGINAL) // Set the target size to load the image at.
                .build(),
        )
        ImageTextRowInfo(modifier = Modifier.padding(start = 10.dp), painter = painter, textValue = origin ?: "")
    }
}



@Composable
fun LabelTextColumnInfo(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Column {
        Text(
            modifier = modifier,
            text = label,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 2.dp),
            style = MaterialTheme.typography.bodyMedium

        )
    }
}

@Composable
fun ImageTextRowInfo(modifier: Modifier = Modifier, painter: Painter, textValue: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .height(24.dp),
            painter = painter,
            contentScale = ContentScale.Fit,
            contentDescription = "$textValue image",
        )
        Text(
            textValue,
            modifier = Modifier.padding(start = 2.dp, end = 8.dp),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge

        )
    }
}
