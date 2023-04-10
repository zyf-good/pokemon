package com.zyf.pokemon.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController



@Composable
fun FixSystemBarsColor() {
    val sysUiController = rememberSystemUiController()
    sysUiController.setSystemBarsColor(Color.Transparent, false)
}