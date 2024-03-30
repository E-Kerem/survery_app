package com.example.survey_app.data.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

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

    fun onNameChange(newName: String) { name = newName }
    fun onBirthDateChange(newDate: String) { birthDate = newDate }
    fun onEducationLevelChange(newLevel: String) { educationLevel = newLevel }
    fun onCityChange(newCity: String) { city = newCity }
    fun onGenderSelected(newGender: String) { gender = newGender } // Renamed for clarity
    fun onAIModelSelectionChange(newModels: List<String>) { selectedAIModels = newModels } // Renamed for clarity
    fun onConsChange(newCons: String) { cons = newCons }

    fun submitSurvey() {
        Log.d("SurveyViewModel", "here") // This will print in Logcat
        val databaseReference = FirebaseDatabase.getInstance().getReference("Surveys")
        val surveyId = databaseReference.push().key // Generate a unique ID for the survey

        val survey = hashMapOf(
            "name" to name,
            "birthDate" to birthDate,
            "educationLevel" to educationLevel,
            "city" to city,
            "gender" to gender,
            "selectedAIModels" to selectedAIModels,
            "cons" to cons,
            "selectedTechnologiesCons" to selectedTechnologiesCons
        )
        Log.d("SurveyViewModel", survey.toString()) // T

        if (surveyId != null) {
            databaseReference.child(surveyId).setValue(survey)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        print("clicked");
                        // Handle success, maybe clear the form or show a success message
                    } else {
                        // Handle failure, show an error message
                    }
                }
        }
    }
}
