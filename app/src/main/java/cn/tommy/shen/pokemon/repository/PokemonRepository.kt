package cn.tommy.shen.pokemon.repository

import cn.tommy.shen.graphqlschema.GetPokemonQuery
import cn.tommy.shen.pokemon.data.Outcome
import cn.tommy.shen.pokemon.network.PokemonService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class PokemonRepository(private val service: PokemonService) {

    fun queryPokemon(name: String, limit: Int, offset: Int): Flow<Outcome<List<GetPokemonQuery.Pokemon_v2_pokemonspecy>>> {
        return service.queryPokemon(name, limit, offset).map {
            Outcome.success(it.dataOrThrow().pokemon_v2_pokemonspecies)
        }.catch {
            emit(Outcome.failure(it))
        }
    }
}