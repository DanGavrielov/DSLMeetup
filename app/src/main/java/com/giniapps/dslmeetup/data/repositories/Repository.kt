package com.giniapps.dslmeetup.data.repositories

import com.giniapps.dslmeetup.data.models.Song
import com.giniapps.dslmeetup.data.remote.DataSource

interface Repository {
    val dataSource: DataSource
    suspend fun getPlaylist(): List<Song>
}