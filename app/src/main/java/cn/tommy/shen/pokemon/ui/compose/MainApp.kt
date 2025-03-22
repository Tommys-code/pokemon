package cn.tommy.shen.pokemon.ui.compose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.tommy.shen.pokemon.data.PokemonDetail
import cn.tommy.shen.pokemon.ui.RouteConfig
import cn.tommy.shen.pokemon.ui.viewmodel.SharedViewModel

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel = viewModel<SharedViewModel>()
    NavHost(navController, startDestination = RouteConfig.MAIN_SCREEN) {
        composable(RouteConfig.MAIN_SCREEN) {
            MainScreen(toDetail = { name, list ->
                viewModel.pokemonDetail = PokemonDetail(name, list)
                navController.navigate(RouteConfig.DETAIL_SCREEN)
            })
        }
        composable(RouteConfig.DETAIL_SCREEN) {
            DetailScreen(viewModel.pokemonDetail, navController)
        }
    }
}
