package com.giniapps.dslmeetup.data.repositories

import com.giniapps.dslmeetup.data.remote.DataSource

class PlaylistRepository(override val dataSource: DataSource) : Repository {
    override suspend fun getPlaylist() = dataSource.getPlaylist()
}