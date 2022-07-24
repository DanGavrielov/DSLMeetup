package com.giniapps.dslmeetup.data.remote

import com.giniapps.dslmeetup.data.models.Song
import com.giniapps.dslmeetup.data.remote.retrofit.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("BlockingMethodInNonBlockingContext")
class RemoteDataSource: DataSource {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val client = retrofit.create(Api::class.java)

    override suspend fun getPlaylist(): List<Song> {
        val response = withContext(Dispatchers.IO) {
            client.getPlaylist().execute()
        }
        val body = response.body() ?: throw NetworkCallFailedException()
        if (body.songs.isEmpty()) throw NetworkCallFailedException()

        return body.songs.map { it.asSongModel() }
    }

    companion object {
        const val BaseUrl = "https://pastebin.com/raw/"
    }
}

class NetworkCallFailedException: Exception("Network call failed")