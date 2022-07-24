package com.giniapps.dslmeetup.media_player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.giniapps.dslmeetup.data.models.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.abs
import kotlin.reflect.KProperty

data class MediaPlayerState(
    var mediaItems: List<Song> = emptyList(),
    var state: PlayerState = PlayerState.NotPrepared,
    var repeatType: RepeatType = RepeatType.NoRepeat,
    var currentlyPlayingMediaItemIndex: Int = -1,
    var itemDuration: Int = -1,
    var currentPlayerPosition: Int = -1
) {
    val currentlyPlayingSong: Song
        get() = if (currentlyPlayingMediaItemIndex == -1) Song.emptyObject()
        else mediaItems[currentlyPlayingMediaItemIndex]

    val currentItemProgress: Float get() {
        return if (currentPlayerPosition < 0) 0f
        else currentPlayerPosition.toFloat() / itemDuration.toFloat()
    }

    fun isPlaying() = state == PlayerState.Playing
    fun isPrepared() = state != PlayerState.NotPrepared

    fun hasNext() = currentlyPlayingMediaItemIndex < mediaItems.lastIndex
    @Throws(NoNextItemException::class) // annotation for Java
    fun nextItem(): Song {
        return when (repeatType) {
            RepeatType.NoRepeat -> {
                if (hasNext())
                    mediaItems[++currentlyPlayingMediaItemIndex]
                else throw NoNextItemException()
            }
            RepeatType.LoopAll -> {
                mediaItems[++currentlyPlayingMediaItemIndex % mediaItems.lastIndex]
            }
            RepeatType.LoopOne -> currentlyPlayingSong
            RepeatType.Random -> {
                val newIndex = mediaItems.indices.random()
                currentlyPlayingMediaItemIndex = newIndex
                currentlyPlayingSong
            }
        }
    }

    fun hasPrevious() = currentlyPlayingMediaItemIndex > 0
    fun previousItem(): Song {
        return when (repeatType) {
            RepeatType.NoRepeat -> {
                if (hasPrevious())
                    mediaItems[--currentlyPlayingMediaItemIndex]
                else currentlyPlayingSong
            }
            RepeatType.LoopAll -> {
                mediaItems[--currentlyPlayingMediaItemIndex % mediaItems.lastIndex]
            }
            RepeatType.LoopOne -> currentlyPlayingSong
            RepeatType.Random -> {
                val newIndex = mediaItems.indices.random()
                currentlyPlayingMediaItemIndex = newIndex
                currentlyPlayingSong
            }
        }
    }
}

@Composable
fun rememberMediaPlayerState(): MediaPlayerState {
    return rememberSaveable(
        saver = object : Saver<MediaPlayerState, List<Any>> {
            override fun restore(value: List<Any>): MediaPlayerState {
                val typeToken = object : TypeToken<List<Song>>() {}.type
                val mediaItems = Gson().fromJson<List<Song>>(value[0] as String, typeToken)
                return MediaPlayerState(
                    mediaItems = mediaItems,
                    state = value[1] as PlayerState,
                    repeatType = value[2] as RepeatType,
                    currentlyPlayingMediaItemIndex = value[3] as Int
                )
            }

            override fun SaverScope.save(value: MediaPlayerState) =
                listOf(
                    Gson().toJson(value.mediaItems),
                    value.state,
                    value.repeatType,
                    value.currentlyPlayingMediaItemIndex
                )
        }
    ) {
        MediaPlayerState()
    }
}

enum class PlayerState {
    NotPrepared, Prepared, Playing, Paused
}

enum class RepeatType {
    NoRepeat, LoopAll, LoopOne, Random
}

class NoNextItemException : Throwable("No next item!")