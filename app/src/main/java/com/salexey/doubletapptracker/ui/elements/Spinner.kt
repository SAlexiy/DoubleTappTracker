package com.salexey.doubletapptracker.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExposedDropdownMenuBoxTypeHabit(
    isExpanded: Boolean, changeSpinnerExpanded: (Boolean) -> Unit,
    selectedPriority: Int, onPriorityChange: (Int) -> Unit
) {
    val priorityValueList = arrayOf(0, 1, 2, 3, 4)

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 5.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = {
                changeSpinnerExpanded(!isExpanded)
            }
        ) {
            TextField(
                value = "$selectedPriority",
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Priority") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
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
                expanded = isExpanded,
                onDismissRequest = { changeSpinnerExpanded(false) }
            ) {
                priorityValueList.forEach { item ->
                    DropdownMenuItem(
                        content = { Text(text = item.toString()) },
                        onClick = {
                            onPriorityChange(item)
                            changeSpinnerExpanded(false)
                        }
                    )
                }
            }
        }
    }
}