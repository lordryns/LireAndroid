package com.radar.lire

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.radar.lire.modules.FeaturedMangaReaderResponse
import com.radar.lire.modules.TrendingMangaReaderManga
import kotlin.system.exitProcess

@Composable
fun LibraryScreen(){
    var canExitApp by remember {
        mutableStateOf(false)
    }


    var featuredMangaList by remember {
        mutableStateOf<FeaturedMangaReaderResponse?>(null)
    }

    var apiExceptionOutput by remember {
        mutableStateOf<Exception?>(null)
    }

    LaunchedEffect(Unit){
         try {
            featuredMangaList = getFeaturedManga()
             apiExceptionOutput = null
        }
        catch (e: Exception){
            featuredMangaList = null
            apiExceptionOutput = e
        }

    }

    if (canExitApp){
        BaseDialog(
            title = "Exit?",
            message = "Do you want to exit the app?",
            icon = Icons.Default.Close,
            onDismissRequest = {
                canExitApp = false
            },
            onConfirmation = {
                exitProcess(0)
            }
        )
    }

   Column(
       modifier = Modifier
           .fillMaxSize(),
       verticalArrangement = Arrangement.Center,
       horizontalAlignment = Alignment.CenterHorizontally
   ) {

       if (featuredMangaList?.data != null){
           Row(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.Start
           ) {
               Text(text = "Featured Manga", style=MaterialTheme.typography.titleLarge)
           }
           LazyColumn {
               itemsIndexed(featuredMangaList?.data!!){
                       _, manga ->

                   FeaturedMangaCard(manga = manga, onClick = {})
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
    
        
        

    

    BackHandler {
        canExitApp = true
    }

}
