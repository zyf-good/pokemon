package com.zyf.pokemon.view

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.gson.Gson
import com.zyf.electronicwoodfish.nav.RouterUrls
import com.zyf.pokemon.R
import com.zyf.pokemon.model.PokemonResult
import com.zyf.pokemon.nav.NavController
import com.zyf.pokemon.utils.TwoBackFinish
import com.zyf.pokemon.utils.cdp
import com.zyf.pokemon.utils.getPicUrl
import com.zyf.pokemon.view.commond.CommonCircularProgress
import com.zyf.pokemon.view.commond.CommonNetworkImage
import com.zyf.pokemon.viewmodels.PokemonListViewModel
import java.util.*


/**
 * @author zengyifeng
 * @date createDate:2023-04-07
 * @brief description
 */


@Composable
fun FullScreenView(context: Context) {
    val vm: PokemonListViewModel = hiltViewModel()
    val searchString = rememberSaveable {
        mutableStateOf("")
    }

    var list: LazyPagingItems<PokemonResult>? = null
    list = if (vm.currentResult != null) {
        vm.currentResult!!.collectAsLazyPagingItems()
    } else {
        vm.getPokemon(searchString.value).collectAsLazyPagingItems()
    }

    val state: LazyGridState = rememberLazyGridState()

    BackHandler {
        TwoBackFinish().execute { (context as Activity).finish() }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(
            modifier = Modifier.padding(top = 80.cdp)
        )
        OutlinedTextField(value = searchString.value, onValueChange = {
            searchString.value = it
        }, placeholder = { Text(text = "Search Pokemon", color = Color.Gray) }, leadingIcon = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_search),
                contentDescription = "搜索"
            )
        }, modifier = Modifier
            .height(103.cdp)
            .focusTarget()
            .background(
                Color.White, RoundedCornerShape(16.cdp)
            ), singleLine = true, colors = LoginTextFieldColors()
        )
        Spacer(
            modifier = Modifier.padding(top = 10.cdp)
        )
        if (list == null) {
            CommonCircularProgress()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .padding(30.cdp),
            contentPadding = PaddingValues(14.cdp, 0.cdp, 14.cdp, 80.cdp),
            verticalArrangement = Arrangement.spacedBy(40.cdp),
            horizontalArrangement = Arrangement.spacedBy(30.cdp)
        ) {

            items(
                count = list.itemCount
            ) { count ->
                val item = list[count]
                ItemView(item!!)
            }
        }


    }


}

@Composable
fun ItemView(item: PokemonResult) {
    Column(
        Modifier
            .width(150.cdp)
            .height(300.cdp)
            .background(
                colorResource(R.color.white), shape = RoundedCornerShape(16.cdp)
            )
            .clickable {
                val json = Uri.encode(Gson().toJson(item))
                NavController.instance.navigate("${RouterUrls.AttributeDetailView}/$json")
            }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.cdp)
                .align(alignment = Alignment.CenterHorizontally)
                .background(
                    colorResource(R.color.yellow),
                    shape = RoundedCornerShape(topStart = 16.cdp, topEnd = 16.cdp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.height(10.cdp))
            CommonNetworkImage(
                url = item.url.getPicUrl(),
                modifier = Modifier
                    .width(210.cdp)
                    .height(210.cdp)
                    ,
                allowHardware = true,
            )
        }
        Text(
            text = item.name
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 16.cdp, bottom = 16.cdp)
        )
    }
}

@Composable
private fun LoginTextFieldColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        placeholderColor = Color.Black,
        focusedLabelColor = Color.Black,
        unfocusedLabelColor = Color.Black,
        unfocusedBorderColor = Color.Black,
        focusedBorderColor = Color.Black,
        textColor = Color.Black,
        cursorColor = Color.Black,
    )
}