@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.survey_app.ui.screen

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.survey_app.data.model.SurveyViewModel
import com.example.survey_app.ui.theme.SurveyAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.Calendar
@Composable
fun SurveyScreen(viewModel: SurveyViewModel = viewModel()) {
    SurveyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilledCardExample()
                NameInput(name = viewModel.name, onNameChange = viewModel::onNameChange)
                BirthdateInput(birthdate = viewModel.birthDate, onBirthdateChange = viewModel::onBirthDateChange)
                CityInput(city = viewModel.city, onCityChange = viewModel::onCityChange)
                EducationLevelInput(educationLevel = viewModel.educationLevel, onEducationLevelChange = viewModel::onEducationLevelChange)
                GenderInput(selectedGender = viewModel.gender, onGenderSelected = viewModel::onGenderSelected)
                ConsInput(cons = viewModel.cons, onConsChange = viewModel::onConsChange)
                DividerWithSpacing()
                AITechnologySelection(
                    selectedTechnologiesCons = viewModel.selectedTechnologiesCons,
                    onUpdateCons = viewModel::updateConsForTechnology
                )
                SubmitButton(onSubmit = viewModel::submitSurvey)
            }
        }
    }
}

@Composable
fun DividerWithSpacing() {
    Spacer(modifier = Modifier.height(8.dp))
    Divider()
    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun FilledCardExample() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        modifier = Modifier
            .size(width = 350.dp, height = 160.dp)
    ) {
        Text(
            text = "CS 458 \n SOFTWARE VERIFICATION AND VALIDATION \n INTRODUCTION TO MOBILE TEST AUTOMATION",
            modifier = Modifier
                .padding(20.dp),
            textAlign = TextAlign.Center,
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun NameInput(name: String, onNameChange: (String) -> Unit) {
    OutlinedTextField(
        value = name,
        onValueChange = onNameChange,
        label = { Text("Name and Surname") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
            )
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun BirthdateInput(birthdate: String, onBirthdateChange: (String) -> Unit) {
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        DisposableEffect(Unit) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = android.app.DatePickerDialog(
                context,
                { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                    onBirthdateChange("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
                    showDialog.value = false
                }, year, month, day
            )

            datePickerDialog.setOnDismissListener {
                showDialog.value = false
            }

            datePickerDialog.show()

            onDispose {
                datePickerDialog.dismiss()
            }
        }
    }

    OutlinedTextField(
        value = birthdate,
        onValueChange = {},
        label = { Text("Birth Date (DD/MM/YYYY)") },
        modifier = Modifier
            .fillMaxWidth()
            .clickable { showDialog.value = true },
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select Date",
                modifier = Modifier.clickable { showDialog.value = true }
            )
        }
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

    Text(
        text = "Select The AI Technologies That You Used",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            options.forEach { option ->
                val isSelected = selectedOptions[option] ?: false

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
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
                        label = { Text("Cons/Defects for $option") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Composable
fun ConsInput(cons: String, onConsChange: (String) -> Unit) {
    OutlinedTextField(
        value = cons,
        onValueChange = onConsChange,
        label = { Text("Benefits of AI Models") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun EducationLevelInput(educationLevel: String, onEducationLevelChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val educationLevels = listOf("High School", "Bachelor's", "Master's", "PhD")
    val context = LocalContext.current

    Box {
        OutlinedTextField(
            value = educationLevel,
            onValueChange = {},
            label = { Text("Education Level") },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Menu,
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
            educationLevels.forEach { level ->
                DropdownMenuItem(
                    text = { Text(level) },
                    onClick = {
                        onEducationLevelChange(level)
                        expanded = false
                    }
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}


@Composable
fun CityInput(city: String, onCityChange: (String) -> Unit) {
    OutlinedTextField(
        value = city,
        onValueChange = onCityChange,
        label = { Text("City") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "",
            )
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun GenderInput(selectedGender: String, onGenderSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf("Male", "Female", "Other")
    val label = "Gender"

    Box {
        OutlinedTextField(
            value = selectedGender,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            readOnly = true,
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
    Spacer(modifier = Modifier.height(16.dp))
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
