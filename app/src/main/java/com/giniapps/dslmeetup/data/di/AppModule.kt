package com.giniapps.dslmeetup.data.di

import com.giniapps.dslmeetup.data.remote.DataSource
import com.giniapps.dslmeetup.data.remote.RemoteDataSource
import com.giniapps.dslmeetup.data.repositories.PlaylistRepository
import com.giniapps.dslmeetup.data.repositories.Repository
import com.giniapps.dslmeetup.data.view_models.PlaylistViewModel
import com.giniapps.dslmeetup.media_player.MediaPlayer
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory<DataSource> { RemoteDataSource() }
    factory<Repository> { PlaylistRepository(dataSource = get()) }
    factory { MediaPlayer(androidContext()) }
    viewModel {
        PlaylistViewModel(
            repository = get(),
            mediaPlayer = get()
        )
    }
}