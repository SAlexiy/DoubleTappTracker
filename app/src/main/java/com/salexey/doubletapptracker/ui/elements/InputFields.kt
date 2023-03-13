package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel

@Composable
fun nameField(padding: Int = 10, viewModel: HabitCreatorViewModel){

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    val text = viewModel.name.collectAsState()

    Box(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .padding(horizontal = padding.dp, vertical = 5.dp)) {
        TextField(
            value = text.value,
            onValueChange = {viewModel.setName(it)} ,
            modifier = Modifier
                .width(328.dp),
            label = { Text(text = "Name") },
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = focusedColor,
                focusedLabelColor = focusedColor.copy(alpha = 0.54f),
                unfocusedLabelColor = unfocusedColor.copy(alpha = 0.54f),
                textColor = unfocusedColor,
                backgroundColor = backgroundColor,
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = unfocusedColor,

            )
        )
    }
}

@Composable
fun descriptionField(padding: Int = 10,viewModel: HabitCreatorViewModel){

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    val text = viewModel.description.collectAsState()

    Box(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .padding(horizontal = padding.dp, vertical = 5.dp)) {
        TextField(
            value = text.value,
            onValueChange = {viewModel.setDescription(it)} ,
            modifier = Modifier
                .width(328.dp),
            label = { Text(text = "Description") },
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = focusedColor,
                focusedLabelColor = focusedColor.copy(alpha = 0.54f),
                unfocusedLabelColor = unfocusedColor.copy(alpha = 0.54f),
                textColor = unfocusedColor,
                backgroundColor = backgroundColor,
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = unfocusedColor,

                )
        )
    }
}

@Composable
fun periodicityField(padding: Int = 10,viewModel: HabitCreatorViewModel){

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    val text = viewModel.periodicity.collectAsState()

    Box(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .padding(horizontal = padding.dp, vertical = 5.dp)) {
        TextField(
            value = text.value,
            onValueChange = {viewModel.setPeriodicity(it)} ,
            modifier = Modifier
                .width(328.dp),
            label = { Text(text = "Periodicity") },
            singleLine = false,
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = focusedColor,
                focusedLabelColor = focusedColor.copy(alpha = 0.54f),
                unfocusedLabelColor = unfocusedColor.copy(alpha = 0.54f),
                textColor = unfocusedColor,
                backgroundColor = backgroundColor,
                focusedIndicatorColor = focusedColor,
                unfocusedIndicatorColor = unfocusedColor,
                )
        )
    }
}