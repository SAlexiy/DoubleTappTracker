package com.salexey.doubletapptracker.ui.elements.list.colorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ColorUnit(hue: Float, onClick: ()-> Unit){

    Box(modifier = Modifier
        .background(brush = Brush.horizontalGradient(
            colors = listOf(
                Color.hsv(hue - 10f,1f,1f),
                Color.hsv(hue + 10f,1f,1f)
            )
        ))
        .padding(25.dp)

        .background(color = Color.Black)
        .padding(2.dp)

        .size(70.dp)
        .background(color = Color.hsv(hue,1f,1f))
        .clickable {
            onClick()
        }
    )

}