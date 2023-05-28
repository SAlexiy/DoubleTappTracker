package com.salexey.doubletapptracker.ui.elements

import android.content.Context
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salexey.doubletapptracker.consts.values.TypeValue


@Composable
fun RadioButtons(
    selectedType: Int, onTypeChange: (Int) -> Unit,
    text: String, type: List<Int>
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = Modifier
        .padding(10.dp)) {
        Text(text = text, fontSize = 13.sp, color = Color.Black.copy(alpha = 0.54f),
            modifier = Modifier
                .padding(start = 40.dp, bottom = 5.dp)
        )
        type.forEach { typeValue ->
            Row(
                Modifier
                    .padding(horizontal = 50.dp, vertical = 7.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .selectable(
                        interactionSource = interactionSource,
                        indication = null,
                        selected = (typeValue == selectedType),
                        onClick = { onTypeChange(typeValue) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    colors = RadioButtonDefaults.colors(selectedColor = Color.Blue),
                    selected = (typeValue == selectedType),
                    onClick = null
                )

                Text( text = stringResource(TypeValue.getTypeByValue(typeValue).strId), fontSize = 18.sp,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }
        }

    }
}
