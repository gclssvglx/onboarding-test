package com.gclewis.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OnboardingScreen(
    steps: Map<Int, Map<String, String>>,
    screenType: OnboardingScreenType,
    viewModel: OnboardingScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        androidx.compose.ui.platform.LocalLifecycleOwner.current
    )
    val context = androidx.compose.ui.platform.LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painterResource(
                id = context.resources.getIdentifier(
                    steps.getValue(uiState.currentStep).getValue("image"),
                    "drawable",
                    context.packageName
                )
            ),
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

        Row() {
            if (screenType == OnboardingScreenType.PreviousNext) {
                PreviousNextUI(steps = steps)
            }
            if (screenType == OnboardingScreenType.DotStep) {
                DotStepUI(steps = steps)
            }
        }

        TextButton(
            onClick = { /* TODO */ }
        ) {
            Text("Skip")
        }
    }
}

@Composable
private fun DotStepUI(
    steps: Map<Int, Map<String, String>>,
    viewModel: OnboardingScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    steps.keys.forEach { step ->
        var onClickFunction = { viewModel.setStep(step) }
        var containerColor = MaterialTheme.colorScheme.primary

        if (step == uiState.currentStep) {
            onClickFunction = { }
            containerColor = MaterialTheme.colorScheme.secondary
        }

        Button(
            onClick = onClickFunction,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor
            ),
            modifier = Modifier.padding(16.dp)
                .size(32.dp)
        ) {}
    }
}

@Composable
private fun PreviousNextUI(
    steps: Map<Int, Map<String, String>>,
    viewModel: OnboardingScreenViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(
        androidx.compose.ui.platform.LocalLifecycleOwner.current
    )

    if (uiState.currentStep > 1) {
        Button(
            onClick = { viewModel.previousStep() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Previous")
        }
    }

    if (uiState.currentStep < steps.size) {
        Button(
            onClick = { viewModel.nextStep(steps.size) },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Next")
        }
    }
}

