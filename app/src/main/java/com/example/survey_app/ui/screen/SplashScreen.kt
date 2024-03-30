package com.example.survey_app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.survey_app.R
import com.example.survey_app.ui.theme.SurveyAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigationToSurvey: () -> Unit) {
    LaunchedEffect(key1 = true) {
        delay(500) // 2 seconds delay
        onNavigationToSurvey()
    }

    SurveyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.splash_image), // Update this line
                    contentDescription = "Splash Screen Image"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(onNavigationToSurvey = {})
}
