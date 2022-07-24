package com.giniapps.dslmeetup.data.remote.retrofit.response_models

import com.giniapps.dslmeetup.data.models.Song
import com.google.gson.annotations.SerializedName

data class PlaylistResponse(
    @SerializedName("songs")
    val songs: List<SongResponse>
)

data class SongResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("artist")
    val artist: String,

    @SerializedName("album")
    val album: String,

    @SerializedName("cover")
    val coverImageUrl: String,

    @SerializedName("duration")
    val duration: Long,

    @SerializedName("media_url")
    val mediaUrl: String
) {
    fun asSongModel() = Song(
        id, title, artist, album, coverImageUrl, duration.asDurationString(), mediaUrl
    )

    private fun Long.asDurationString(): String {
        val minutes = "${this / 60}"
        val seconds = this % 60
        val secondsString = if (seconds < 10) "0$seconds" else "$seconds"
        return "$minutes:$secondsString"
    }
}