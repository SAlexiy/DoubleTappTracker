package com.salexey.doubletapptracker.ui.elements.list.habitlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.salexey.datamodels.habit.Habit
import com.salexey.doubletapptracker.consts.values.TypeValue
import com.salexey.doubletapptracker.features.habitdonestreak.HabitDoneStreak

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun habitUnit(habit: Habit, onClick: ()-> Unit, buttonClick: ()-> Unit) {
    val context = LocalContext.current

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {



        Row(modifier = Modifier
            .clickable {
            onClick()
        },
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
                .width(230.dp)
            ) {
                Text(habit.title, fontSize = 16.sp, fontWeight = FontWeight(600),
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                Text(habit.description)

                Text("Type: ${TypeValue.getTypeByValue(habit.type)}")
                Text("Priority: ${habit.priority}")
                Text("Periodicity: ${habit.frequency}")
            }
        }


        Box(modifier = Modifier
            .width(100.dp)
            .fillMaxHeight()
            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
            .background(color = Color.Green)
            .clickable {
                buttonClick()
                HabitDoneStreak.showToast(habit, context)
            }
        )

    }
}
