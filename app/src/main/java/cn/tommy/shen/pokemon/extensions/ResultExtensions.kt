@file:Suppress("UNCHECKED_CAST")

package cn.tommy.shen.pokemon.extensions

import cn.tommy.shen.pokemon.data.Outcome

fun <T> Outcome<T>.dataOrNull(): T? = when (this) {
    is Outcome.Success -> data
    is Outcome.Progress -> partialData
    is Outcome.Failure -> partialData
}

fun <T : List<*>> Outcome<T>.combineListData(oldData: T?): Outcome<T> {
    return when (this) {
        is Outcome.Success -> {
            Outcome.Success(oldData?.let { (it + data) as T } ?: data)
        }

        is Outcome.Progress -> Outcome.loading(loading, oldData)
        is Outcome.Failure -> Outcome.failure(e, oldData)
    }
}