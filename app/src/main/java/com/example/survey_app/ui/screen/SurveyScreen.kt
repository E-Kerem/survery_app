@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.survey_app.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.survey_app.data.model.SurveyViewModel
import com.example.survey_app.ui.theme.SurveyAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SurveyScreen(viewModel: SurveyViewModel = viewModel()) {
    SurveyAppTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NameInput(name = viewModel.name, onNameChange = viewModel::onNameChange)
                BirthdateInput(birthdate = viewModel.birthDate, onBirthdateChange = viewModel::onBirthDateChange)
                EducationLevelInput(educationLevel = viewModel.educationLevel, onEducationLevelChange = viewModel::onEducationLevelChange)
                CityInput(city = viewModel.city, onCityChange = viewModel::onCityChange)
                GenderInput(selectedGender = viewModel.gender, onGenderSelected = viewModel::onGenderSelected)
                AITechnologySelection(
                    selectedTechnologiesCons = viewModel.selectedTechnologiesCons,
                    onUpdateCons = viewModel::updateConsForTechnology
                )
                ConsInput(cons = viewModel.cons, onConsChange = viewModel::onConsChange)
                SubmitButton(onSubmit = viewModel::submitSurvey)
            }
        }
    }
}

@Composable
fun NameInput(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name and Surname") },
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun BirthdateInput(birthdate: String, onBirthdateChange: (String) -> Unit) {
    OutlinedTextField(
        value = birthdate,
        onValueChange = onBirthdateChange,
        label = { Text("Birth Date (DD/MM/YYYY)") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun AITechnologySelection(
    selectedTechnologiesCons: Map<String, String>,
    onUpdateCons: (String, String) -> Unit
) {
    val options = listOf("ChatGPT", "Gemini", "Claude", "Copilot")
    val selectedOptions = remember { mutableStateMapOf<String, Boolean>().apply { options.forEach { put(it, false) } } }

    Column {
        options.forEach { option ->
            val isSelected = selectedOptions[option] ?: false

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isSelected,
                    onCheckedChange = { isChecked ->
                        selectedOptions[option] = isChecked
                        if (!isChecked) {
                            onUpdateCons(option, "")
                        } else {
                            if (option !in selectedTechnologiesCons) {
                                onUpdateCons(option, "")
                            }
                        }
                    }
                )
                Text(
                    text = option,
                    modifier = Modifier.weight(1f)
                )
            }

            if (isSelected) {
                OutlinedTextField(
                    value = selectedTechnologiesCons[option] ?: "",
                    onValueChange = { cons -> onUpdateCons(option, cons) },
                    label = { Text("Cons for $option") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



@Composable
fun ConsInput(cons: String, onConsChange: (String) -> Unit) {
    OutlinedTextField(
        value = cons,
        onValueChange = onConsChange,
        label = { Text("Cons of AI Models") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun EducationLevelInput(educationLevel: String, onEducationLevelChange: (String) -> Unit) {
    OutlinedTextField(
        value = educationLevel,
        onValueChange = onEducationLevelChange,
        label = { Text("Education Level") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun CityInput(city: String, onCityChange: (String) -> Unit) {
    OutlinedTextField(
        value = city,
        onValueChange = onCityChange,
        label = { Text("City") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun GenderInput(selectedGender: String, onGenderSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other") // Add more options as needed
    val label = "Gender"

    Box {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = { /* Don't allow manual edit */ },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            readOnly = true, // Make the TextField read-only
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            genders.forEach { gender ->
                DropdownMenuItem(
                    text = { Text(gender) },
                    onClick = {
                        onGenderSelected(gender)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun SubmitButton(onSubmit: () -> Unit) {
    Button(
        onClick = onSubmit,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Submit")
    }
}

@Preview(showBackground = true)
@Composable
fun SurveyScreenPreview() {
    SurveyScreen()
}
