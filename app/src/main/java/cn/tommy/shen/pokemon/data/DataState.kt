package cn.tommy.shen.pokemon.data

import androidx.compose.runtime.Immutable

@Immutable
sealed class Outcome<out T> {
    @Immutable
    data class Progress<T>(val loading: Boolean, val partialData: T? = null) : Outcome<T>()

    @Immutable
    data class Success<T>(val data: T) : Outcome<T>()

    @Immutable
    data class Failure<T>(val e: Throwable, val partialData: T? = null) : Outcome<T>()

    companion object {
        fun <T> loading(isLoading: Boolean = true, partialData: T? = null): Outcome<T> = Progress(isLoading, partialData)

        fun <T> success(data: T): Outcome<T> = Success(data)

        fun <T> failure(e: Throwable, partialData: T? = null): Outcome<T> = Failure(e, partialData)
    }
}