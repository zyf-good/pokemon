package com.zyf.pokemon.nav

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.gson.Gson
import com.zyf.electronicwoodfish.nav.RouterUrls
import com.zyf.pokemon.model.PokemonResult
import com.zyf.pokemon.view.AttributeDetailView
import com.zyf.pokemon.view.FullScreenView


/**
 * @author zengyifeng
 * @date createDate:2023-03-21
 * @brief description
 */
object NavController {
    @SuppressLint("StaticFieldLeak")
    lateinit var instance: NavHostController

}


@OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun InitNavController(
    navController: NavHostController,
    startDestination: String = RouterUrls.SCREENPAGE,
    context: Context
) {
    NavController.instance = navController
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            RouterUrls.SCREENPAGE
        ) {
            FullScreenView(context)
        }


        composable("${RouterUrls.AttributeDetailView}/{item}") {
            val item = it.arguments?.getString("item")!!
            val pokemonResult = Gson().fromJson(item, PokemonResult::class.java)
            AttributeDetailView(pokemonResult)
        }



    }


}