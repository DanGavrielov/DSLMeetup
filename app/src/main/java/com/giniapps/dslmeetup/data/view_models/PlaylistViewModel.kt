package com.giniapps.dslmeetup.data.view_models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giniapps.dslmeetup.data.models.Song
import com.giniapps.dslmeetup.data.remote.NetworkCallFailedException
import com.giniapps.dslmeetup.data.repositories.Repository
import com.giniapps.dslmeetup.media_player.MediaPlayer
import com.giniapps.dslmeetup.media_player.MediaPlayerState
import com.giniapps.dslmeetup.media_player.PlayerState
import kotlinx.coroutines.launch

class PlaylistViewModel(
    private val repository: Repository,
    private val mediaPlayer: MediaPlayer
) : ViewModel() {
    var screenState by mutableStateOf(PlaylistScreenStateHolder())
    var playerState: MediaPlayerState
        get() = mediaPlayer.state
        set(value) { mediaPlayer.state = value }

    init {
        getPlaylist()
        mediaPlayer.setOnCompleteAction {
            if (playerState.hasNext()) playNext()
        }
    }

    fun getPlaylist() {
        screenState = screenState.copy(dataFetchingState = DataFetchingState.Fetching)
        viewModelScope.launch {
            screenState = try {
                val playlist = repository.getPlaylist()
                playerState = playerState.copy(
                    mediaItems = playlist
                )
                screenState.copy(
                    dataFetchingState = DataFetchingState.Success
                )
            } catch (e: NetworkCallFailedException) {
                screenState.copy(dataFetchingState = DataFetchingState.Failure)
            }
        }
    }

    fun prepareSongAndPlay(mediaItemId: String) {
        playerState = playerState.copy(
            state = PlayerState.Prepared,
            currentlyPlayingMediaItemIndex =
            playerState.mediaItems.indexOfFirst { it.id == mediaItemId }
        )
        mediaPlayer.prepareSong(playerState.currentlyPlayingSong)
        play()
    }

    fun pause() {
        if (playerState.isPlaying()) {
            mediaPlayer.pause()
            playerState = playerState.copy(
                state = PlayerState.Paused
            )
        }
    }

    fun play() {
        mediaPlayer.play()
        playerState = playerState.copy(
            state = PlayerState.Playing
        )
    }

    fun playNext() {
        if (playerState.hasNext()) {
            val nextSong = playerState.nextItem()
            playerState = playerState.copy(
                currentlyPlayingMediaItemIndex =
                    playerState.mediaItems.indexOfFirst { it.id == nextSong.id }
            )
            mediaPlayer.playNext(nextSong)
        }
    }

    fun playPrevious() {
        if (playerState.hasPrevious()) {
            val nextSong = playerState.previousItem()
            playerState = playerState.copy(
                currentlyPlayingMediaItemIndex =
                playerState.mediaItems.indexOfFirst { it.id == nextSong.id }
            )
            mediaPlayer.playPrevious(nextSong)
        }
    }

    fun closePlayer() {
        playerState = playerState.copy(
            state = PlayerState.NotPrepared,
            currentlyPlayingMediaItemIndex = -1
        )
        mediaPlayer.playerClosed()
    }

    fun updateCurrentPlayerPosition() {
        mediaPlayer.updatePlayerPosition()
    }
}

data class PlaylistScreenStateHolder(
    val dataFetchingState: DataFetchingState = DataFetchingState.Fetching
)

enum class DataFetchingState {
    Fetching, Success, Failure
}