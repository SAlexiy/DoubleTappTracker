package com.salexey.doubletapptracker.ui.elements.list.colorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.features.ColorFeatures

@Composable
fun SelectedColor(color: Int){

    Column(modifier = Modifier
        .padding(horizontal = 30.dp, vertical = 10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    ) {

        Box(modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(color))
        )


        Text("RGB: ${ Color(color).red}, " +
                "${ Color(color).green}, " +
                "${ Color(color).blue}")


        Text("HSV: ${ ColorFeatures().rgbToHsv(Color(color))[0]}, " +
                "${ ColorFeatures().rgbToHsv(Color(color))[1]}, " +
                "${ ColorFeatures().rgbToHsv(Color(color))[2]}")
    }
}