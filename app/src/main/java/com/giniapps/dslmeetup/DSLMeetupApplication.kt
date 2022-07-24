package com.giniapps.dslmeetup

import android.app.Application
import com.giniapps.dslmeetup.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class DSLMeetupApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DSLMeetupApplication)
            modules(appModule)
        }
    }
}