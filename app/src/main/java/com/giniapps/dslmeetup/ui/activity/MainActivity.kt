package com.giniapps.dslmeetup.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.giniapps.dslmeetup.ui.activity.composables.App
import com.giniapps.dslmeetup.ui.theme.DSLMeetupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DSLMeetupTheme {
                App()
            }
        }
    }
}