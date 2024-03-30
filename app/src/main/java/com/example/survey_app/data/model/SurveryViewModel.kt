package com.example.survey_app.data.model

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SurveyViewModel : ViewModel() {
    // Properties are correctly named
    var name by mutableStateOf("")
    var birthDate by mutableStateOf("")
    var educationLevel by mutableStateOf("")
    var city by mutableStateOf("")
    var gender by mutableStateOf("")
    var selectedAIModels by mutableStateOf<List<String>>(emptyList()) // Renamed for clarity
    var cons by mutableStateOf("")
    var selectedTechnologiesCons by mutableStateOf(mapOf<String, String>())
        private set

    fun updateConsForTechnology(technology: String, cons: String) {
        val updatedMap = selectedTechnologiesCons.toMutableMap()
        if (cons.isBlank()) {
            updatedMap.remove(technology) // Remove the entry if cons is blank
        } else {
            updatedMap[technology] = cons
        }
        selectedTechnologiesCons = updatedMap.toMap()
    }


    // Function names match those used in the Composables
    fun onNameChange(newName: String) { name = newName }
    fun onBirthDateChange(newDate: String) { birthDate = newDate }
    fun onEducationLevelChange(newLevel: String) { educationLevel = newLevel }
    fun onCityChange(newCity: String) { city = newCity }
    fun onGenderSelected(newGender: String) { gender = newGender } // Renamed for clarity
    fun onAIModelSelectionChange(newModels: List<String>) { selectedAIModels = newModels } // Renamed for clarity
    fun onConsChange(newCons: String) { cons = newCons }

    fun submitSurvey() {
        // Implement validation and submission logic here
    }
}
