package com.gclewis

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gclewis.ui.theme.OnboardingTestTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnboardingTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    OnboardingScreen()
                }
            }
        }
    }

    @Composable
    fun OnboardingScreen(
        viewModel: OnboardingScreenViewModel = viewModel()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            viewModel.ShowCurrentStep()

            Row() {
                Button(onClick = {
                    viewModel.previousStep()
                }) {
                    Text(text = "Previous")
                }
                Button(onClick = {
                    viewModel.nextStep()
                }) {
                    Text(text = "Next")
                }
            }
        }
    }
}

data class OnboardingScreenUIState(
    val currentStep: Int = 1,
)

class OnboardingScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingScreenUIState())
    val uiState: StateFlow<OnboardingScreenUIState> = _uiState.asStateFlow()

    private var steps = mapOf(
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

    fun nextStep() {
        if (uiState.value.currentStep < steps.size) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentStep = currentState.currentStep + 1
                )
            }
        }
    }

    fun previousStep() {
        if (uiState.value.currentStep > 1) {
            _uiState.update { currentState ->
                currentState.copy(
                    currentStep = currentState.currentStep - 1
                )
            }
        }
    }

    @Composable
    fun ShowCurrentStep(
        viewModel: OnboardingScreenViewModel = viewModel()
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle(
            androidx.compose.ui.platform.LocalLifecycleOwner.current
        )
        val context = androidx.compose.ui.platform.LocalContext.current

        Image(
            painterResource(id = context.resources.getIdentifier(steps.getValue(uiState.currentStep).getValue("image"), "drawable", context.packageName)) ,
            contentDescription = steps.getValue(uiState.currentStep).getValue("title"),
            modifier = Modifier.padding(16.dp).height(400.dp)
        )

        Text(
            text = steps.getValue(uiState.currentStep).getValue("title"),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = steps.getValue(uiState.currentStep).getValue("description"),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
