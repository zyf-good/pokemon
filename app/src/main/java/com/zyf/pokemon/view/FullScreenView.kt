package com.zyf.pokemon.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.google.gson.Gson
import com.zyf.electronicwoodfish.nav.RouterUrls
import com.zyf.pokemon.R
import com.zyf.pokemon.model.PokemonResult
import com.zyf.pokemon.nav.NavController
import com.zyf.pokemon.utils.TwoBackFinish
import com.zyf.pokemon.utils.cdp
import com.zyf.pokemon.utils.getPicUrl
import com.zyf.pokemon.viewmodels.PokemonListViewModel
import java.util.*


/**
 * @author zengyifeng
 * @date createDate:2023-04-07
 * @brief description
 */


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FullScreenView(context: Context) {
    val vm: PokemonListViewModel = hiltViewModel()
    val searchString = rememberSaveable {
        mutableStateOf("")
    }
    val searchOldString = rememberSaveable {
        mutableStateOf("")
    }

    var list: LazyPagingItems<PokemonResult>? = null
    list = if (vm.currentResult != null&& searchString.value==searchOldString.value) {
        vm.currentResult!!.collectAsLazyPagingItems()
    } else {
        searchOldString.value = searchString.value
        vm.getPokemon(searchOldString.value).collectAsLazyPagingItems()
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
            .height(113.cdp)
            .focusTarget()
            .background(
                Color.White, RoundedCornerShape(16.cdp)
            ), singleLine = true, colors = LoginTextFieldColors()
        )
        Spacer(
            modifier = Modifier.padding(top = 10.cdp)
        )
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemView(item: PokemonResult) {
    var backgroundColor by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val modelBuilder = ImageRequest.Builder(context).data(item.url.getPicUrl()).crossfade(false)
        .allowHardware(false).transformations().placeholder(R.drawable.ic_pokeball)
        .error(R.drawable.ic_pokeball)


    LaunchedEffect(modelBuilder.build()) {
        val bitmap = context.imageLoader.execute(modelBuilder.build()).drawable?.toBitmap(
            config = Bitmap.Config.RGBA_F16
        )
        bitmap?.let {
            val palette = Palette.from(bitmap).generate()
            backgroundColor = palette.getDominantColor(0)
        }
    }

    Column(
        Modifier
            .width(150.cdp)
            .height(300.cdp)
            .background(
                colorResource(R.color.white), shape = RoundedCornerShape(16.cdp)
            )
            .clickable {
                item.backgroundColor = backgroundColor
                val json = Uri.encode(Gson().toJson(item))
                NavController.instance.navigate("${RouterUrls.AttributeDetailView}/$json")
            }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.cdp)
                .align(alignment = Alignment.CenterHorizontally)
                .background(
                    color = Color(backgroundColor),
                    shape = RoundedCornerShape(topStart = 16.cdp, topEnd = 16.cdp)
                ), contentAlignment = Alignment.Center
        ) {
            Spacer(modifier = Modifier.height(10.cdp))
            AsyncImage(
                model = modelBuilder.build(),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(210.cdp)
                    .height(210.cdp),
            )
        }
        Text(
            text = item.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
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