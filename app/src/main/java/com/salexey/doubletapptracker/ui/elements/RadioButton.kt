package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel


@Composable
fun radioButtons(viewModel: HabitCreatorViewModel) {

    val type = listOf("Полезная", "Вредная")
    val selectedType = viewModel.type.collectAsState()
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier
        .padding(10.dp)) {
        Text(text = "Type", fontSize = 13.sp, color = Color.Black.copy(alpha = 0.54f),
            modifier = Modifier
                .padding(start = 40.dp, bottom = 5.dp)
        )
        type.forEach { text ->
            Row(
                Modifier
                    .padding(horizontal = 50.dp, vertical = 7.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .selectable(
                        interactionSource = interactionSource,
                        indication = null,
                        selected = (text == selectedType.value),
                        onClick = { viewModel.setType(text) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Blue),
                    selected = (text == selectedType.value),
                    onClick = null
                )

                Text( text = text, fontSize = 18.sp,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }
        }

    }
}
