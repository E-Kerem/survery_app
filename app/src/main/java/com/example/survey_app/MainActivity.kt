package com.example.survey_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.survey_app.ui.screen.SplashScreen
import com.example.survey_app.ui.screen.SurveyScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isSplashShown by remember { mutableStateOf(true) }

            if (isSplashShown) {
                SplashScreen(onNavigationToSurvey = { isSplashShown = false })
            } else {
                SurveyScreen()
            }
        }
    }
}
