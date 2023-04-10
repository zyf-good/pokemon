package com.zyf.pokemon.view.commond

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.zyf.pokemon.utils.MAX_BASE_STATE
import com.zyf.pokemon.utils.cdp
import kotlinx.coroutines.delay

/**
 * @author zengyifeng
 * @date createDate:2023-04-08
 * @brief description
 */
@Composable
fun CommonCircularProgress(){
    CircularProgressIndicator(
        color = MaterialTheme.colors.secondary,
    )
}

@Composable
fun CommonAttributeCircularProgress(text:String,content:String,mProgress: Float,modifier: Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var progress = remember {
            mutableStateOf(0.0f)
        }
        LaunchedEffect(true){
            var state = 0.0f
            while (state <= mProgress) {
                progress.value = state
                state += mProgress / 10f
                delay(50)
            }
        }

        Box(){
            Text(text = text,modifier = modifier.align(Alignment.Center))
            CircularProgressIndicator(progress = 1f,
                color = Color(0xFFffcba4),
                modifier = modifier
                    .align(Alignment.Center)
                    .size(150.cdp, 150.cdp)
            )
            CircularProgressIndicator(progress = progress.value,
                color = MaterialTheme.colors.secondary,
                modifier = modifier
                    .align(Alignment.Center)
                    .size(150.cdp, 150.cdp)
            )
        }
        Text(text = content, modifier = modifier.padding(top = 10.cdp))
    }
}



@Preview(showSystemUi = true)
@Composable
fun CommonCircularProgressPre() {
    CommonAttributeCircularProgress("100","hp",0.73f,Modifier)
}