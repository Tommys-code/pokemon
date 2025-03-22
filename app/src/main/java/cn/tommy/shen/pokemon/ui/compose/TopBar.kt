package cn.tommy.shen.pokemon.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cn.tommy.shen.pokemon.R
import cn.tommy.shen.pokemon.ui.theme.Pink80

@Composable
fun TopBar(
    title: String,
    onBack: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier
            .background(Pink80)
    ) {
        Spacer(
            Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)
        )
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp),
                textAlign = TextAlign.Center
            )
            onBack?.let {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .align(Alignment.CenterStart)
                        .size(32.dp)
                        .clickable { it.invoke() }
                )
            }
        }
    }
}

@Composable
fun ProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(36.dp)
            .padding(10.dp)
    )
}

@Composable
fun TipsItem(tips: String, modifier: Modifier = Modifier) {
    Text(
        text = tips,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun BarTopSubPageHeading() {
    TopBar("Pokemon") {}
}

@Preview(showBackground = true)
@Composable
private fun PreviewTipsItem() {
    TipsItem("This is a tip")
}