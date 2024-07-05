package com.radar.lire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.radar.lire.modules.TrendingMangaReaderManga
import com.radar.lire.modules.TrendingMangaReaderResponse

@Composable
fun TrendingScreen(){

    var trendingManga by remember {
        mutableStateOf<TrendingMangaReaderManga?>(null)
    }

    var trendingMangaList by remember {
        mutableStateOf<TrendingMangaReaderResponse?>(null)
    }

    var showBottomSheet by remember {
        mutableStateOf(false)
    }


    var apiExceptionOutput by remember {
        mutableStateOf<Exception?>(null)
    }

    if (showBottomSheet && trendingManga != null){
        TrendingBottomSheet(manga = trendingManga!!, onDismissRequest = {showBottomSheet = false})
    }

    LaunchedEffect(Unit){
        try {
            trendingMangaList = getTrendingManga()
            apiExceptionOutput = null
        }
        catch (e: Exception){
            trendingMangaList = null
            apiExceptionOutput = e
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        if (trendingMangaList?.data != null){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "Trending Manga", style=MaterialTheme.typography.titleLarge)
            }
            LazyColumn {
                itemsIndexed(trendingMangaList?.data!!){
                        _, manga ->

                    TrendingMangaCard(manga = manga, onClick = {
                        trendingManga = manga
                        showBottomSheet = true
                    })
                }
            }


        }

        else {
            if (apiExceptionOutput != null){
                if (apiExceptionOutput is java.nio.channels.UnresolvedAddressException){
                    Text(text = "An internet connection is required!")
                }
                
                else {
                    Text(text = "Some random error occurred.")
                }
            }
            else {
                Text(text = "Loading...")
            }
        }

    }

}

