package com.giniapps.dslmeetup.data.remote.retrofit

import com.giniapps.dslmeetup.data.remote.retrofit.response_models.PlaylistResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("yEhMmnBY")
    fun getPlaylist(): Call<PlaylistResponse>
}