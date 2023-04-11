package com.zyf.pokemon.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResult(
    val name: String,
    val url: String,
    var backgroundColor:Int = 0
) : Parcelable