package com.salexey.doubletapptracker.ui.elements.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StandardButton(text: String = "text", textColor: Color = Color.Black,
                   width: Int = 146, height: Int = 36, buttonColor: Color = Color.White,
                   onClick: ()-> Unit){
    Box(
        modifier = Modifier
            .width(width.dp)
            .height(height.dp)
            .shadow(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp),
                clip = true)
            .clip(RoundedCornerShape(8.dp))
            .background(buttonColor)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color =  textColor)
    }
}