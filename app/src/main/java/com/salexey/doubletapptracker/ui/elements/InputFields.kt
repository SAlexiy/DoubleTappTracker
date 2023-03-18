package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextField(padding: Int = 10, label: String,
              textState: String, onValueChange: (String) -> Unit
){

    val focusedColor = Color.Blue
    val unfocusedColor = Color.Black
    val backgroundColor = Color.Transparent

    Box(modifier = Modifier
        .clip(RoundedCornerShape(4.dp))
        .padding(horizontal = padding.dp, vertical = 5.dp)) {
        TextField(
            value = textState,
            onValueChange = { onValueChange(it) },
            modifier = Modifier
                .width(328.dp),
            label = { Text(text = label) },
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