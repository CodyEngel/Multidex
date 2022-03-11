package dev.multidex.clientrepository

interface Repository<T, Q : RetrieveQuery> {
    suspend fun retrieve(query: Q): Response<T>
}

interface RetrieveQuery

object RetrieveAllQuery : RetrieveQuery