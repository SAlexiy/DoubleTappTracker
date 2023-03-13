package com.salexey.doubletapptracker.ui.elements.list.colorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.features.ColorFeatures
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel

@Composable
fun selectedColor(viewModel: HabitCreatorViewModel){

    val color = viewModel.color.collectAsState()

    Column(modifier = Modifier
        .padding(horizontal = 30.dp, vertical = 10.dp)
        .fillMaxWidth()
        .wrapContentHeight()
    ) {

        Box(modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .height(80.dp)
            .background(color = Color(color.value))
        )


        Text("RGB: ${ Color(color.value).red}, " +
                "${ Color(color.value).green}, " +
                "${ Color(color.value).blue}")


        Text("HSV: ${ ColorFeatures().rgbToHsv(Color(color.value))[0]}, " +
                "${ ColorFeatures().rgbToHsv(Color(color.value))[1]}, " +
                "${ ColorFeatures().rgbToHsv(Color(color.value))[2]}")
    }
}