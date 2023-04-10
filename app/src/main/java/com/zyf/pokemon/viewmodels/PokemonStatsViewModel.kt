package com.zyf.pokemon.viewmodels

import androidx.lifecycle.ViewModel
import com.zyf.pokemon.utils.NetworkResource
import com.zyf.pokemon.utils.extractId
import dagger.hilt.android.lifecycle.HiltViewModel
import com.zyf.pokemon.data.repositories.PokemonRepository

import kotlinx.coroutines.flow.flow
import javax.inject.Inject


/**
 *created by Ronnie Otieno on 13-Feb-21.
 **/


@HiltViewModel
class PokemonStatsViewModel @Inject constructor(private val pokemonRepository: PokemonRepository) :
    ViewModel() {

     fun getSinglePokemon(url: String) = flow {
        val id = url.extractId()
        emit(NetworkResource.Loading)
        emit(pokemonRepository.getSinglePokemon(id))
    }

}