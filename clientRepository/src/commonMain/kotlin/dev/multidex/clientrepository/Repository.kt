package dev.multidex.clientrepository

interface Repository<T> {
    suspend fun retrieve(): Response<T>
}