package com.giniapps.dslmeetup.ui.screens.playlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giniapps.dslmeetup.data.view_models.DataFetchingState
import com.giniapps.dslmeetup.data.view_models.PlaylistViewModel
import com.giniapps.dslmeetup.ui.screens.playlist.composables.BottomPlayer
import com.giniapps.dslmeetup.ui.screens.playlist.composables.NetworkFailureMessage
import com.giniapps.dslmeetup.ui.screens.playlist.composables.ProgressBar
import com.giniapps.dslmeetup.ui.screens.playlist.composables.SongList
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun PlaylistScreen(modifier: Modifier = Modifier) {
    val viewModel: PlaylistViewModel = getViewModel()
    if (viewModel.playerState.isPlaying()) {
        LaunchedEffect(Unit) {
            while (true) {
                viewModel.updateCurrentPlayerPosition()
                delay(1_000 / 30)
            }
        }
    }
    Column {
        println("${viewModel.playerState.currentPlayerPosition} / ${viewModel.playerState.itemDuration}")
        when (viewModel.screenState.dataFetchingState) {
            DataFetchingState.Fetching -> ProgressBar(
                modifier = modifier.weight(1f)
            )
            DataFetchingState.Success -> SongList(
                modifier = modifier.weight(1f),
                songs = viewModel.playerState.mediaItems,
                onSongClicked = { viewModel.prepareSongAndPlay(it) },
                currentlyPlayingMediaId = viewModel.playerState.currentlyPlayingSong.id
            )
            DataFetchingState.Failure -> NetworkFailureMessage(
                modifier = modifier.weight(1f)
            ) {
                viewModel.getPlaylist()
            }
        }
        if (viewModel.playerState.isPrepared()) {
            BottomPlayer(
                modifier = Modifier.height(100.dp),
                song = viewModel.playerState.currentlyPlayingSong,
                songProgress = viewModel.playerState.currentItemProgress,
                isPlaying = viewModel.playerState.isPlaying(),
                onBackButtonClicked = { viewModel.playPrevious() },
                onNextButtonClicked = { viewModel.playNext() },
                onPauseButtonClicked = { viewModel.pause() },
                onPlayButtonClicked = { viewModel.play() },
                closePlayer = {
                    viewModel.closePlayer()
                }
            )
        }
    }
}