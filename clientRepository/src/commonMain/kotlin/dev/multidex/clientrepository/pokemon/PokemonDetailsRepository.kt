package dev.multidex.clientrepository.pokemon

import dev.multidex.clientrepository.DefaultHttpClient
import dev.multidex.clientrepository.Repository
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.RetrieveQuery
import dev.multidex.pokemodel.Pokemon
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class PokemonDetailsRepository(
    private val client: HttpClient = DefaultHttpClient
) : Repository<Pokemon, RetrievePokemonDetailsQuery> {
    override suspend fun retrieve(query: RetrievePokemonDetailsQuery): Response<Pokemon> {
        return Response.Success(client.downloadPokemon(query.pokemonId))
    }

    private suspend fun HttpClient.downloadPokemon(id: Int): Pokemon {
        return get("https://pokeapi.co/api/v2/pokemon/$id/").body()
    }
}

data class RetrievePokemonDetailsQuery(val pokemonId: Int) : RetrieveQuery