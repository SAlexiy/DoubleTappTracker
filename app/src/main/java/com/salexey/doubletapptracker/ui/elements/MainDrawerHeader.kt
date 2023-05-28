package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.salexey.doubletapptracker.ui.theme.Purple500

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MainDrawerHeader() {
    Column (modifier = Modifier
        .fillMaxSize()
        .background(color = Purple500),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(modifier = Modifier.padding(20.dp), text = "Навигация", fontWeight = FontWeight(600), fontSize = 25.sp, color = Color.White)

        GlideImage(
            model = "https://mobimg.b-cdn.net/v3/fetch/22/226764f441ab68fed1cc52d8e12be6c6.jpeg",
            contentDescription = "good rabbit",
            modifier = Modifier.padding(10.dp).size(400.dp,300.dp),
        )
    }
}
