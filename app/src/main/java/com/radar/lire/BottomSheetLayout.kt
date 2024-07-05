package com.radar.lire


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.radar.lire.modules.TrendingMangaReaderManga


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendingBottomSheet(
    onDismissRequest: () -> Unit,
    manga: TrendingMangaReaderManga
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismissRequest() },
        sheetState = sheetState
    ) {
        TrendingMangaBottomSheetLayout(manga = manga)
    }
    
}

@Composable
fun TrendingMangaBottomSheetLayout(manga: TrendingMangaReaderManga) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = manga.cover,
            contentDescription = manga.title,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = manga.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rating: ${manga.rating}",
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Chapters: ${manga.chapters.toInt()}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Volumes: ${manga.volumes}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Languages: ${manga.langs.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

