package com.gclewis

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.gclewis.onboarding.OnboardingScreen
import com.gclewis.onboarding.OnboardingScreenType
import com.gclewis.ui.theme.OnboardingTestTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnboardingTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val steps = mapOf(
                        1 to mapOf(
                            "title" to "Step 1",
                            "description" to "Step 1 description",
                            "image" to "durham_7539264_1280",
                        ),
                        2 to mapOf(
                            "title" to "Step 2",
                            "description" to "Step 2 description",
                            "image" to "green_sea_turtle_8199770_1280",
                        ),
                        3 to mapOf(
                            "title" to "Step 3",
                            "description" to "Step 3 description",
                            "image" to "beach_84598_1280",
                        ),
                        4 to mapOf(
                            "title" to "Step 4",
                            "description" to "Step 4 description",
                            "image" to "underwater_4286600_1280",
                        ),
                    )

                    OnboardingScreen(
                        steps = steps,
                        screenType = OnboardingScreenType.DotStep
                    )
                }
            }
        }
    }
}
