package com.giniapps.dslmeetup.data.models

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val coverImageUrl: String,
    val duration: String,
    val mediaUrl: String
) {
    fun isEmpty() = this == emptyObject()
    fun isNotEmpty() = this != emptyObject()

    companion object {
        fun emptyObject() = Song(
            id = "",
            title = "",
            artist = "",
            album = "",
            coverImageUrl = "",
            duration = "",
            mediaUrl = ""
        )
    }
}