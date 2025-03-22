package cn.tommy.shen.pokemon.extensions

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.isScrolledToTheEnd(positionFromTheEnd: Int = 0): Boolean =
    with(layoutInfo.visibleItemsInfo.lastOrNull()?.index) {
        if (this == null) {
            false
        } else {
            this >= layoutInfo.totalItemsCount - 1 - positionFromTheEnd
        }
    }