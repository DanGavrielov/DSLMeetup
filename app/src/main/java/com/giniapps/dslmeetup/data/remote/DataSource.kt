package com.giniapps.dslmeetup.data.remote

import com.giniapps.dslmeetup.data.models.Song

interface DataSource {
    suspend fun getPlaylist(): List<Song>
}