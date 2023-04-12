package com.zyf.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zyf.pokemon.nav.InitNavController
import com.zyf.pokemon.ui.theme.PokemonTheme
import com.zyf.pokemon.utils.FixSystemBarsColor
import com.zyf.pokemon.utils.setAndroidNativeLightStatusBar
import com.zyf.pokemon.utils.transformDp
import com.zyf.pokemon.utils.transparentStatusBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setAndroidNativeLightStatusBar()
        setContent {
            PokemonTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = LocalWindowInsets.current.navigationBars.bottom.transformDp)
                        .background(MaterialTheme.colors.secondary)
                ) {
                    val navController = rememberAnimatedNavController()
                    InitNavController(navController = navController, context = this@MainActivity)
                    FixSystemBarsColor()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokemonTheme {
        Greeting("Android")
    }
}