package cn.tommy.shen.pokemon.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cn.tommy.shen.pokemon.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: MainViewModel,
) {
    var time by remember { mutableIntStateOf(3) }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Splash Screen", style = MaterialTheme.typography.bodyLarge)
            Text(
                "$time", modifier = Modifier
                    .padding(innerPadding)
                    .align(Alignment.TopEnd)
                    .background(Color.DarkGray)
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                color = Color.White
            )
        }
    }
    LaunchedEffect(Unit) {
        repeat(3) {
            delay(1000)
            time--
        }
        viewModel.editFirstLaunch()
    }
}