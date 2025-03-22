package cn.tommy.shen.pokemon.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cn.tommy.shen.pokemon.R
import cn.tommy.shen.pokemon.data.PokemonDetail

@Composable
fun DetailScreen(
    pokemonDetail: PokemonDetail?,
    navController: NavController = rememberNavController()
) {
    if (pokemonDetail == null) {
        navController.popBackStack()
        return
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(stringResource(R.string.detail_screen_title)) {
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(
                stringResource(R.string.pokemon_name, pokemonDetail.name),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
            Text(
                stringResource(R.string.pokemon_abilities, pokemonDetail.abilities.joinToString(",")),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}

@Preview
@Composable
private fun PreviewDetailScreen() {
    DetailScreen(PokemonDetail("1", listOf("1", "2", "3")))
}

