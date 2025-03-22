package cn.tommy.shen.pokemon.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.tommy.shen.graphqlschema.GetPokemonQuery.Pokemon_v2_pokemon
import cn.tommy.shen.graphqlschema.GetPokemonQuery.Pokemon_v2_pokemoncolor
import cn.tommy.shen.graphqlschema.GetPokemonQuery.Pokemon_v2_pokemonspecy
import cn.tommy.shen.pokemon.R
import cn.tommy.shen.pokemon.data.Outcome
import cn.tommy.shen.pokemon.data.PokemonColor
import cn.tommy.shen.pokemon.extensions.dataOrNull
import cn.tommy.shen.pokemon.extensions.isScrolledToTheEnd
import cn.tommy.shen.pokemon.ui.viewmodel.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    toDetail: (String, List<String>) -> Unit,
    viewModel: MainViewModel = koinViewModel(),
) {
    val isFirstLaunch = viewModel.isFirstLaunch.collectAsState(false).value
    if (isFirstLaunch) {
        SplashScreen(viewModel)
    } else {
        MainPage(
            viewModel.pokemonResult,
            hasMore = viewModel.hasMore,
            onSearch = { viewModel.getPokemonList(it) },
            loadMore = { viewModel.loadMore() },
            onPokemonClick = {
                toDetail.invoke(it.name, it.pokemon_v2_pokemonabilities.mapNotNull { ability ->
                    ability.pokemon_v2_ability?.name
                })
            },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainPage(
    result: Outcome<List<Pokemon_v2_pokemonspecy>>,
    hasMore: MutableState<Boolean>,
    onSearch: (String) -> Unit = {},
    onPokemonClick: (Pokemon_v2_pokemon) -> Unit = {},
    loadMore: () -> Unit = {},
) {
    var name by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val listState = rememberLazyListState()
    if (listState.isScrolledToTheEnd(1)) {
        loadMore.invoke()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(stringResource(R.string.main_title))
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
        ) {
            stickyHeader {
                TextField(
                    value = name,
                    singleLine = true,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.search_hint)) },
                    modifier = Modifier.fillParentMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        onSearch.invoke(name)
                        focusManager.clearFocus()
                    }),
                )
            }

            result.dataOrNull()?.let {
                items(it) { data ->
                    PokemonListItem(
                        data = data,
                        onPokemonClick = { pokemon ->
                            onPokemonClick.invoke(pokemon)
                        },
                    )
                }
            }

            when (result) {
                is Outcome.Progress -> {
                    if (result.loading) {
                        item { ProgressIndicator() }
                    }
                }

                is Outcome.Success -> {
                }

                is Outcome.Failure -> {
                    item { TipsItem(result.e.message.orEmpty()) }
                }
            }

            if (hasMore.value.not()) {
                item { TipsItem(stringResource(R.string.no_data)) }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PokemonListItem(
    data: Pokemon_v2_pokemonspecy,
    onPokemonClick: (Pokemon_v2_pokemon) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bgColor = PokemonColor.fromString(data.pokemon_v2_pokemoncolor?.name).color
    val textColor = if (bgColor == Color.Black) {
        Color.White
    } else {
        Color.Black
    }
    Spacer(modifier = Modifier.height(2.dp))
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            stringResource(R.string.pokemon_species_name, data.name),
            color = textColor,
        )
        Text(
            stringResource(R.string.pokemon_capture_rate, data.capture_rate?.toString().orEmpty()),
            color = textColor,
        )

        FlowRow(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(modifier = Modifier.fillMaxRowHeight(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.pokemon_names), color = textColor)
            }
            data.pokemon_v2_pokemons.forEach {
                Text(
                    text = it.name,
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clickable { onPokemonClick.invoke(it) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPokemonListItem() {
    PokemonListItem(
        onPokemonClick = {},
        data = Pokemon_v2_pokemonspecy(
            1,
            "222",
            333,
            pokemon_v2_pokemoncolor = Pokemon_v2_pokemoncolor(1, "red"),
            pokemon_v2_pokemons = listOf(
                Pokemon_v2_pokemon(1, "111", emptyList()),
                Pokemon_v2_pokemon(2, "222", emptyList()),
                Pokemon_v2_pokemon(3, "2213123121321", emptyList()),
                Pokemon_v2_pokemon(4, "444", emptyList())
            ),
        )
    )
}