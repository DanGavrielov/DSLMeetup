package com.giniapps.dslmeetup.ui.screens.playlist.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giniapps.dslmeetup.data.models.Song

@Composable
fun SongList(
    modifier: Modifier = Modifier,
    songs: List<Song>,
    onSongClicked: (String) -> Unit,
    currentlyPlayingMediaId: String
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 12.dp)
    ) {
        items(songs) {
            SongItem(
                modifier = Modifier
                    .padding(bottom = 12.dp)
                    .clickable { onSongClicked(it.id) },
                song = it,
                isPlaying = it.id == currentlyPlayingMediaId
            )
        }
    }
}

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    song: Song,
    isPlaying: Boolean = false
) {
    val rootModifier =
        if (isPlaying) modifier.border(
            width = 1.dp,
            color = MaterialTheme.colors.primary,
            shape = MaterialTheme.shapes.medium
        )
        else modifier
    Card(
        modifier = rootModifier
            .fillMaxWidth(),
        elevation = 20.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(song.coverImageUrl)
                    .build(),
                contentDescription = null
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = song.title,
                    style = LocalTextStyle.current.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(text = song.artist)
            }
        }
    }
}