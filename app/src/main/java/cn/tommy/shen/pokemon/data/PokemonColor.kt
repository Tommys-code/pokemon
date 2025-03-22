package cn.tommy.shen.pokemon.data

import androidx.compose.ui.graphics.Color
import cn.tommy.shen.pokemon.ui.theme.ColorBrown
import cn.tommy.shen.pokemon.ui.theme.ColorPink
import cn.tommy.shen.pokemon.ui.theme.ColorPurple

enum class PokemonColor(val value: String, val color: Color) {
    BLACK("black", Color.Black),
    BLUE("blue", Color.Blue),
    BROWN("brown", ColorBrown),
    GRAY("gray", Color.Gray),
    GREEN("green", Color.Green),
    PINK("pink", ColorPink),
    PURPLE("purple", ColorPurple),
    RED("red", Color.Red),
    WHITE("white", Color.White),
    YELLOW("yellow", Color.Yellow),
    UNKNOWN("unknown", Color.LightGray);

    companion object {
        fun fromString(text: String?): PokemonColor =
            text?.let { entries.firstOrNull { it.value.equals(text, true) } } ?: UNKNOWN
    }
}