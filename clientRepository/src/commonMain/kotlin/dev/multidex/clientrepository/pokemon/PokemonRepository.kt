package dev.multidex.clientrepository.pokemon

import dev.multidex.clientrepository.DefaultHttpClient
import dev.multidex.clientrepository.Repository
import dev.multidex.clientrepository.Response
import dev.multidex.clientrepository.RetrieveAllQuery
import dev.multidex.pokemodel.AbbreviatedPokemonResults
import dev.multidex.pokemodel.Pokemon
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class PokemonRepository(private val client: HttpClient = DefaultHttpClient) :
    Repository<List<Pokemon>, RetrieveAllQuery> {
    suspend fun retrieve(): Response<List<Pokemon>> {
        return retrieve(RetrieveAllQuery)
    }

    override suspend fun retrieve(query: RetrieveAllQuery): Response<List<Pokemon>> {
        return Response.Success(client.downloadPokemon())
    }
}

private suspend fun HttpClient.downloadPokemon(): List<Pokemon> {
    val pokemonToRetrieveRequest = "https://pokeapi.co/api/v2/pokemon/?limit=1"
    val pokemonToRetrieve =
        get(urlString = pokemonToRetrieveRequest).body<AbbreviatedPokemonResults>()
    val requests = pokemonToRetrieve.results.map { abbreviatedPokemon ->
        coroutineScope {
            async {
                get(abbreviatedPokemon.url).body<Pokemon>()
            }
        }
    }

    return requests.map { it.await() }
        .sortedBy { it.order }
}