package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.salexey.doubletapptracker.ui.theme.Purple500

@Composable
fun MainDrawerHeader() {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Purple500),
        contentAlignment = Alignment.Center){
        Text("Навигация", fontWeight = FontWeight(600), fontSize = 25.sp, color = Color.White)
    }
}
