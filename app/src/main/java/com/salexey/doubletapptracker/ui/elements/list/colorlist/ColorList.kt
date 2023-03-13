package com.salexey.doubletapptracker.ui.elements.list.colorlist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.ui.screens.mainActivity.habitcreator.HabitCreatorViewModel

@Composable
fun colorPicker(viewModel: HabitCreatorViewModel) {

    val hueList = mutableListOf<Float>()
    for (i in 1..33 step(2)){
        hueList.add(i * 10f)
    }

    LazyRow(modifier = Modifier
        .padding(horizontal = 30.dp)
        .height(120.dp)
        .fillMaxWidth()
    ) {

        items(hueList.size) { currentHue ->

            colorUnit(hue = hueList[currentHue], onClick = {
                viewModel.setColor(

                    Color.hsv(hueList[currentHue],1f,1f).toArgb()

                )
            })

        }

    }
}