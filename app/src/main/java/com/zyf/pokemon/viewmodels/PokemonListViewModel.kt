package com.zyf.pokemon.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zyf.pokemon.data.repositories.PokemonRepository
import com.zyf.pokemon.model.PokemonResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject




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