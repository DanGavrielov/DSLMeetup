package com.giniapps.dslmeetup.media_player

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.giniapps.dslmeetup.data.models.Song
import kotlin.math.min

typealias InternalMediaPlayer = MediaPlayer

class MediaPlayer(
    private val context: Context
) {
    private val mediaPlayer = InternalMediaPlayer()
    private var playerPrepared = false
    var state by mutableStateOf(MediaPlayerState())

    fun prepareSong(song: Song) {
        if (playerPrepared && mediaPlayer.isPlaying) {
            mediaPlayer.reset()
        }
        mediaPlayer.setDataSource(context, Uri.parse(song.mediaUrl))
        mediaPlayer.prepare()
        playerPrepared = true
        state = state.copy(itemDuration = mediaPlayer.duration)
    }

    fun pause() {
        mediaPlayer.pause()
    }

    fun play() {
        mediaPlayer.start()
    }

    fun playNext(song: Song) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, Uri.parse(song.mediaUrl))
        mediaPlayer.prepare()
        playerPrepared = true
        state = state.copy(itemDuration = mediaPlayer.duration)
        play()
    }

    fun playPrevious(song: Song) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(context, Uri.parse(song.mediaUrl))
        mediaPlayer.prepare()
        playerPrepared = true
        state = state.copy(itemDuration = mediaPlayer.duration)
        play()
    }

    fun playerClosed() {
        mediaPlayer.reset()
        playerPrepared = false
    }

    fun updatePlayerPosition() {
        state = state.copy(
            currentPlayerPosition = min(mediaPlayer.currentPosition, state.itemDuration)
        )
    }

    fun setOnCompleteAction(action: () -> Unit) {
        mediaPlayer.setOnCompletionListener {
            action()
        }
    }
}