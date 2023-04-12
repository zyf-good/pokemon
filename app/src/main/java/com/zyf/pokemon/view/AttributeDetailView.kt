package com.zyf.pokemon.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.zyf.pokemon.R
import com.zyf.pokemon.model.PokemonResult
import com.zyf.pokemon.model.SinglePokemonResponse
import com.zyf.pokemon.model.Sprites
import com.zyf.pokemon.model.Stats
import com.zyf.pokemon.nav.NavController
import com.zyf.pokemon.utils.*
import com.zyf.pokemon.view.commond.CommonAttributeCircularProgress
import com.zyf.pokemon.view.commond.CommonCircularProgress
import com.zyf.pokemon.view.commond.CommonNetworkImage
import com.zyf.pokemon.viewmodels.PokemonStatsViewModel
import java.util.*


@Composable
fun AttributeDetailView(item: PokemonResult) {
    val modifier = Modifier
    val state = rememberLazyGridState()
    val vm: PokemonStatsViewModel = hiltViewModel()
    val entity = remember {
        mutableStateOf(SinglePokemonResponse(Sprites(), emptyList(), 0, 0))
    }
    val showLoading = remember {
        mutableStateOf(true)
    }

    LaunchedEffect(key1 = item) {
        vm.getSinglePokemon(item.url).collect {
            when (it) {
                is NetworkResource.Success -> {
                    showLoading.value = false
                    entity.value = it.value
                }
                is NetworkResource.Failure -> {
                    showToast("There was an error loading the pokemon")
                }
                is NetworkResource.Loading -> {
                    showLoading.value = true
                }
            }
        }
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(item.backgroundColor))
    ) {
        Spacer(
            modifier = Modifier
                .padding(top = 100.cdp)
        )

        ConstraintLayout(
            modifier = modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            val (backBtn, name, pic, metres, kgs) = createRefs()

            Icon(
                painter = painterResource(R.drawable.breake),
                contentDescription = null,
                modifier = modifier
                    .size(70.cdp)
                    .padding(start = 10.cdp)
                    .clickable {
                        NavController.instance.popBackStack()
                    }
                    .constrainAs(backBtn) {},
                tint = Color.White
            )

            Text(text = item.name,
                modifier
                    .constrainAs(name) {
                        top.linkTo(parent.top)
                        start.linkTo(backBtn.end)
                    }
                    .padding(start = 60.cdp),
                fontSize = 50.csp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            CommonNetworkImage(url = item.url.getPicUrl(),
                modifier = modifier
                    .constrainAs(pic) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(200.cdp))
            Text(text = entity.value.height.div(10.0).toString() + " metres",
                modifier
                    .constrainAs(metres) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 30.cdp, bottom = 10.cdp),
                fontSize = 50.csp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.White)

            Text(text = entity.value.weight.div(10.0).toString() + " kgs",
                modifier
                    .constrainAs(kgs) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(end = 30.cdp, bottom = 10.cdp),
                fontSize = 50.csp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.White)

        }
        Column(
            modifier = modifier
                .weight(3f)
                .background(Color.White)
                .fillMaxSize()
                .padding(30.cdp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Base Stats",
                modifier.padding(start = 30.cdp, bottom = 10.cdp),
                fontSize = 50.csp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            if (showLoading.value) {
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

                items(count = entity.value.stats.size) { count ->
                    val item = entity.value.stats[count]
                    AttributeDetailItemView(item, modifier)
                }
            }

        }

    }

}


@Composable
fun AttributeDetailItemView(stat: Stats, modifier: Modifier) {

    CommonAttributeCircularProgress(
        modifier = modifier, content = stat.stat.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }, mProgress =
        (stat.base_stat.toFloat() / MAX_BASE_STATE.toFloat()), text = stat.base_stat.toString()
    )


}


