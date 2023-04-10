package com.zyf.pokemon.model

import android.os.Parcelable
import com.zyf.pokemon.model.Stat
import kotlinx.parcelize.Parcelize

@Parcelize
data class Stats(
    val base_stat: Int,
    val effort: Int,
    val stat: Stat
) : Parcelable