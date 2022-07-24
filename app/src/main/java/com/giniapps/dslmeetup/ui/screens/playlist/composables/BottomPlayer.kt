package com.giniapps.dslmeetup.ui.screens.playlist.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.giniapps.dslmeetup.data.models.Song

@Composable
fun BottomPlayer(
    modifier: Modifier = Modifier,
    song: Song,
    songProgress: Float,
    isPlaying: Boolean,
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onPauseButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit,
    closePlayer: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.1f)
            )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Playing ${song.title} by ${song.artist}",
                fontWeight = FontWeight.Bold
            )
            PlayerController(
                isPlaying = isPlaying,
                onBackButtonClicked = onBackButtonClicked,
                onNextButtonClicked = onNextButtonClicked,
                onPauseButtonClicked = onPauseButtonClicked,
                onPlayButtonClicked = onPlayButtonClicked
            )
        }
        if (songProgress > 0) {
            MediaItemProgressBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                progress = songProgress
            )
        }
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = closePlayer
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }
}

@Composable
fun MediaItemProgressBar(
    modifier: Modifier = Modifier,
    progress: Float
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .background(MaterialTheme.colors.primary)
                .weight(progress)
        )
        Spacer(modifier = Modifier.weight(1 - progress))
    }
}

@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    onBackButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onPauseButtonClicked: () -> Unit,
    onPlayButtonClicked: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = onBackButtonClicked) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.FastRewind,
                contentDescription = null
            )
        }
        if (isPlaying) {
            IconButton(onClick = onPauseButtonClicked) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.PauseCircleFilled,
                    contentDescription = null
                )
            }
        } else {
            IconButton(onClick = onPlayButtonClicked) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Default.PlayCircleFilled,
                    contentDescription = null
                )
            }
        }
        IconButton(onClick = onNextButtonClicked) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.FastForward,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}