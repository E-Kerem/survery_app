// ui/theme/Theme.kt
package com.example.survey_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun SurveyAppTheme(content: @Composable () -> Unit) {
    val colorScheme = lightColorScheme(
    // Define other colors for your theme
    )

    MaterialTheme(
        colorScheme = colorScheme,
        //typography = Typography,
        //shapes = Shapes,
        content = content
    )
}
