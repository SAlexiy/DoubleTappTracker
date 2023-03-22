package com.salexey.doubletapptracker.ui.elements.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.salexey.doubletapptracker.R


@Composable
fun Fab(onClick: ()-> Unit) {
    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        Box(modifier = Modifier
            .padding(end = 20.dp, bottom = 20.dp)
            .size(55.dp)
            .clip(CircleShape)
            .background(color = Color.Black)
            .padding(5.dp)
            .clip(CircleShape)
            .background(color = Color.White)
            .clickable { onClick() },
            contentAlignment = Alignment.Center
        ){
            val vector = ImageVector.vectorResource(id = R.drawable.ic_add_black_24)

            Image(
                vector,
                "top",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(vector.viewportWidth/vector.viewportHeight)
            )

        }
    }
}

