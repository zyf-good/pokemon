package com.zyf.pokemon.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.zyf.pokemon.api.PokemonApi
import com.zyf.pokemon.data.datasource.PokemonDataSource
import javax.inject.Inject
import javax.inject.Singleton


/**
 *created by Ronnie Otieno on 20-Dec-20.
 **/

/**
 * Repository used to access data being loaded from network call
 */

@Singleton
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) : BaseRepository() {
    //Returning the fetched data as flow

    fun getPokemon(searchString: String?) = Pager(
        config = PagingConfig(enablePlaceholders = false, pageSize = 25),
        pagingSourceFactory = {
            PokemonDataSource(pokemonApi, searchString)
        }
    ).flow


    suspend fun getSinglePokemon(id: Int) = safeApiCall {
        pokemonApi.getSinglePokemon(id)

    }


}