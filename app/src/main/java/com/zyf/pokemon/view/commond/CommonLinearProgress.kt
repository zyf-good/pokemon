package com.zyf.pokemon.view.commond

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.zyf.pokemon.utils.cdp

/**
 * @author zengyifeng
 * @date createDate:2023-04-08
 * @brief description
 */
@Composable
fun CommonLinearProgress( progress: Float,modifier: Modifier){
    LinearProgressIndicator(progress = progress,
        color = MaterialTheme.colors.secondary,
        backgroundColor = Color(0xFFffcba4),
        modifier = modifier
    )
}

@Composable
fun CommonAttributeLinearProgress( text:String,progress: Float,modifier: Modifier){
    Row (horizontalArrangement = Arrangement.Center,//设置水平居中对齐
        verticalAlignment =  Alignment.CenterVertically,//设置垂直居中对齐
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = text, modifier = modifier.width(110.cdp))
        CommonLinearProgress(progress,Modifier)
    }
}


@Preview(showSystemUi = true)
@Composable
fun AttributeDetailViewPre() {
    CommonAttributeLinearProgress("Hp : 47 ",0.47f,Modifier)
}