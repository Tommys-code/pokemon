package cn.tommy.shen.pokemon

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import cn.tommy.shen.pokemon.network.HttpModule
import cn.tommy.shen.pokemon.network.PokemonService
import cn.tommy.shen.pokemon.repository.PokemonRepository
import cn.tommy.shen.pokemon.ui.viewmodel.MainViewModel
import com.apollographql.apollo.ApolloClient
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

private const val LAUNCH_STORE = "storeLaunch"
private val Application.launchInfoStore by preferencesDataStore("launchInfo")

class PokemonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            modules(
                module { single<ApolloClient> { HttpModule().client } },
                module {
                    single<PokemonService> { PokemonService(get()) }
                    single<PokemonRepository> { PokemonRepository(get()) }
                    single<DataStore<Preferences>>(qualifier = qualifier(LAUNCH_STORE)) { this@PokemonApplication.launchInfoStore }
                    viewModel<MainViewModel> { MainViewModel(get(qualifier = qualifier(LAUNCH_STORE)), get()) }
                }
            )
        }
    }
}