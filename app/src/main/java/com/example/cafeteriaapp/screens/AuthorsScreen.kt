package com.example.cafeteriaapp.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.R
import com.example.cafeteriaapp.components.AppCard
import com.example.cafeteriaapp.components.AppLogo
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun AuthorsScreen() {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Autorzy")
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AuthorInfoCard(Modifier.weight(1f))
                IdeisLogoCard(Modifier.weight(1f))
            }
        }
    } else {
        ScreenContainer("Autorzy") {
            AuthorInfoCard(Modifier.fillMaxWidth())
            IdeisLogoCard(Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    Text(title, fontSize = 30.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
}

@Composable
private fun AuthorInfoCard(modifier: Modifier = Modifier) {
    AppCard(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            AppLogo(88)
            Spacer(Modifier.height(10.dp))
            Text("CafeteriaApp", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text("Aplikacja lojalnosciowa - kawiarnia", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(18.dp))
            Text("Autorzy: Hubert Rola, Lukasz Janus", color = MaterialTheme.colorScheme.onSurface)
            Text("Grupa: lab2/3/PROGS", color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun IdeisLogoCard(modifier: Modifier = Modifier) {
    AppCard(modifier) {
        Text("Logo IDEIS", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface)
        Image(
            painter = painterResource(id = R.drawable.ideis),
            contentDescription = "Logo IDEIS",
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp),
            contentScale = ContentScale.Fit
        )
    }
}