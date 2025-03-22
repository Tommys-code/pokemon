package cn.tommy.shen.pokemon.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.tommy.shen.graphqlschema.GetPokemonQuery.Pokemon_v2_pokemonspecy
import cn.tommy.shen.pokemon.data.Outcome
import cn.tommy.shen.pokemon.extensions.combineListData
import cn.tommy.shen.pokemon.extensions.dataOrNull
import cn.tommy.shen.pokemon.repository.PokemonRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val isFirstLaunchKey = booleanPreferencesKey("firstLaunch")

@OptIn(FlowPreview::class)
class MainViewModel(
    private val dataStore: DataStore<Preferences>,
    private val repository: PokemonRepository,
) : ViewModel() {

    private val limit = 10
    private val offset = MutableStateFlow(0)
    private val queryName = MutableSharedFlow<String>()
    val hasMore = mutableStateOf(true)

    var pokemonResult by mutableStateOf<Outcome<List<Pokemon_v2_pokemonspecy>>>(Outcome.loading(false))
        private set

    val isFirstLaunch = dataStore.data.map { it[isFirstLaunchKey] ?: true }

    init {
        viewModelScope.launch {
            queryName.combine(offset) { name, offset ->
                repository.queryPokemon(name, limit, offset)
            }.debounce(100L).collectLatest {
                val newData = it.last()
                when (newData) {
                    is Outcome.Success -> hasMore.value = newData.data.size == limit
                    else -> {}
                }
                pokemonResult = newData.combineListData(pokemonResult.dataOrNull())
            }
        }
    }

    fun editFirstLaunch() {
        viewModelScope.launch {
            dataStore.edit { it[isFirstLaunchKey] = false }
        }
    }

    fun getPokemonList(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            hasMore.value = true
            pokemonResult = Outcome.loading()
            offset.value = 0
            queryName.emit(name)
        }
    }

    fun loadMore() {
        if (pokemonResult is Outcome.Progress || hasMore.value.not()) return
        pokemonResult = Outcome.loading(partialData = pokemonResult.dataOrNull())
        offset.value += limit
    }
}