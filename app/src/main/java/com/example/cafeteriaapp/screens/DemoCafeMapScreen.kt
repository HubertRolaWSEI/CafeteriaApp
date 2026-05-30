package com.example.cafeteriaapp.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cafeteriaapp.components.AppCard
import com.example.cafeteriaapp.components.AppLogo
import com.example.cafeteriaapp.components.ScreenContainer

private const val DEMO_CAFE_NAME = "CafeteriaApp Rynek Kraków"
private const val DEMO_CAFE_ADDRESS = "Rynek Główny 1, 31-042 Kraków"
private const val DEMO_CAFE_LAT = 50.06143
private const val DEMO_CAFE_LNG = 19.93658

@Composable
fun DemoCafeMapScreen() {
    val landscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (landscape) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            ScreenTitle("Kawiarnie")
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                CafeDetailsCard(Modifier.weight(1f))
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    NearbyCafesCard()
                }
            }
        }
    } else {
        ScreenContainer("Kawiarnie") {
            CafeDetailsCard()
            NearbyCafesCard()
        }
    }
}

@Composable
private fun ScreenTitle(title: String) {
    Text(
        title,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun CafeDetailsCard(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    AppCard(modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppLogo(74)
            Spacer(Modifier.height(8.dp))
            Text(
                DEMO_CAFE_NAME,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            Text(
                "Lokal demonstracyjny",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        }

        DetailRow("Adres", DEMO_CAFE_ADDRESS)
        DetailRow("Godziny", "Pon-pt 7:30-19:00, sob 9:00-16:00")
        DetailRow("Współrzędne", "$DEMO_CAFE_LAT, $DEMO_CAFE_LNG")

        Button(
            onClick = { openDemoCafeInMaps(context) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Otwórz w Mapach")
        }
    }
}

@Composable
private fun NearbyCafesCard() {
    AppCard {
        Text(
            "Pozostałe punkty",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        DemoBranch("CafeteriaApp Dworzec Główny", "planowane", "1,0 km")
        DemoBranch("CafeteriaApp Kazimierz", "planowane", "1,4 km")
        DemoBranch("CafeteriaApp AGH", "wkrótce", "1,9 km")
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(label, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            value,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun DemoBranch(name: String, status: String, distance: String) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    name,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    status,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 13.sp
                )
            }
            OutlinedButton(onClick = {}) {
                Text(distance)
            }
        }
    }
}

private fun openDemoCafeInMaps(context: Context) {
    val query = Uri.encode("$DEMO_CAFE_LAT,$DEMO_CAFE_LNG($DEMO_CAFE_NAME)")
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:$DEMO_CAFE_LAT,$DEMO_CAFE_LNG?q=$query")
    )

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "Brak aplikacji map na urządzeniu", Toast.LENGTH_SHORT).show()
    }
}
