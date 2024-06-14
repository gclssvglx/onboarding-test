package com.gclewis.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingScreenState())
    val uiState: StateFlow<OnboardingScreenState> = _uiState.asStateFlow()

    fun nextStep(stepSize: Int = 1) {
        if (uiState.value.currentStep < stepSize) {
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

    fun setStep(step: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                currentStep = step
            )
        }
    }
}
