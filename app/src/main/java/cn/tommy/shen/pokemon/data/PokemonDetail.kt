package cn.tommy.shen.pokemon.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetail(
    val name: String,
    val abilities: List<String>,
) : Parcelable