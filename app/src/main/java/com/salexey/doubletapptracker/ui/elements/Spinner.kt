package com.salexey.doubletapptracker.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun exposedDropdownMenuBoxTypeHabit(viewModel: HabitCreatorViewModel) {
    val priorityValueList = arrayOf(0, 1, 2, 3, 4)

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    val expanded = viewModel.spinnerExpanded.collectAsState()
    val selectedText = viewModel.priority.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 5.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                viewModel.setSpinnerExpanded(!expanded.value)
            }
        ) {
            TextField(
                value = "${selectedText.value}",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Priority") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = focusedColor,
                    focusedLabelColor = focusedColor.copy(alpha = 0.54f),
                    unfocusedLabelColor = unfocusedColor.copy(alpha = 0.54f),
                    textColor = unfocusedColor,
                    backgroundColor = backgroundColor,
                    focusedIndicatorColor = focusedColor,
                    unfocusedIndicatorColor = unfocusedColor,
                ),
                modifier = Modifier
                    .width(328.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { viewModel.setSpinnerExpanded(false) }
            ) {
                priorityValueList.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item.toString()) },
                        onClick = {
                            viewModel.setPriority(item)
                            viewModel.setSpinnerExpanded(false)
                        }
                    )
                }
            }
        }
    }
}