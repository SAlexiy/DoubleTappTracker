package com.salexey.doubletapptracker.ui.elements

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    state: BottomSheetScaffoldState,
    screenContent: @Composable (PaddingValues) -> Unit,
    sheetContent: @Composable () -> Unit,
) {
    BottomSheetScaffold(
        scaffoldState = state,
        sheetContent = {
            sheetContent()
        },
        sheetPeekHeight = 0.dp
    ) {
        screenContent(it)
    }
}
