package cn.tommy.shen.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import cn.tommy.shen.pokemon.data.PokemonDetail

class SharedViewModel : ViewModel() {
    var pokemonDetail: PokemonDetail? = null
}