package com.zyf.pokemon.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.zyf.pokemon.model.PokemonResult
import dagger.hilt.android.lifecycle.HiltViewModel
import com.zyf.pokemon.data.repositories.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

import javax.inject.Inject


/**
 *created by Ronnie Otieno on 20-Dec-20.
 **/

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
) :
    ViewModel() {


    // 宝可梦列表数据流  currentResult是为了保证从详情页面返回时不重新加载数据
    var currentResult: Flow<PagingData<PokemonResult>>? = null

    fun getPokemon(searchString: String?): Flow<PagingData<PokemonResult>> {
        val newResult = pokemonRepository.getPokemon(searchString).cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }



}