package cn.tommy.shen.pokemon.network

import cn.tommy.shen.graphqlschema.GetPokemonQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import kotlinx.coroutines.flow.Flow

class PokemonService(private val client: ApolloClient) {

    fun queryPokemon(name: String, limit: Int, offset: Int): Flow<ApolloResponse<GetPokemonQuery.Data>> {
        return client.query(GetPokemonQuery("%$name%", limit, offset)).toFlow()
    }

}