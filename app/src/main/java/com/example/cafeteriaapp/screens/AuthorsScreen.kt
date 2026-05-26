package com.example.cafeteriaapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.cafeteriaapp.R
import com.example.cafeteriaapp.components.AppLogo
import com.example.cafeteriaapp.components.ScreenContainer

@Composable
fun AuthorsScreen() {
    ScreenContainer("Autorzy") {
        Card(
            Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(Modifier.padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                AppLogo()
                Spacer(Modifier.height(16.dp))
                Text(
                    "CafeteriaApp",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Aplikacja lojalnosciowa - kawiarnia",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(22.dp))
                Text(
                    "Autorzy: Hubert Rola, Lukasz Janus",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    "Grupa: lab2/3/PROGS",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(22.dp))
                Image(
                    painter = painterResource(id = R.drawable.ideis),
                    contentDescription = "Logo IDEIS",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(72.dp),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}