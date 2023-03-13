package com.salexey.doubletapptracker.ui.elements.list.habitlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salexey.doubletapptracker.datamodel.Habit

@Composable
fun habitUnit(habit: Habit,onClick: ()-> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 10.dp)
        .clickable {
                   onClick()
        },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {


        Row(modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){

            //цвет
            Box(modifier = Modifier
                .width(40.dp)
                .fillMaxHeight()
                .padding(10.dp)
                .background(color = Color(habit.color))
            )

            //текстовая информация
            Column(modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .width(250.dp)
            ) {
                Text(habit.name, fontSize = 16.sp, fontWeight = FontWeight(600),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                Text(habit.description)

                Text("Type: ${habit.type}")
                Text("Periodicity: ${habit.periodicity}")
            }
        }

        //приоритет
        Text("${habit.priority}", color = Color.Black,
            fontSize = 18.sp, fontWeight = FontWeight(600),
            modifier = Modifier
                .padding(10.dp)
                .size(30.dp)
        )
    }
}
