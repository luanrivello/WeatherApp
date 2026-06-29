package com.example.findinglogs.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.findinglogs.R
import com.example.findinglogs.viewmodel.SettingsViewModel

@Composable
fun SettingsView(
    navController: NavController,
    settingsViewModel: SettingsViewModel = viewModel(),
    onBack: () -> Unit
) {
    DisposableEffect(Unit) {
        onDispose { onBack() }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.weather_snow_dark))
    ) {
        Column (
            modifier = Modifier
                .padding(vertical = 64.dp, horizontal = 32.dp)
        ){
            Text(
                text = "Settings",
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(all = 36.dp)
            )

            val localizations by settingsViewModel.localizations.observeAsState(emptyList())

            LazyColumn () {
                itemsIndexed(localizations) { index, latlon ->
                    WeatherItemSettings(settingsViewModel, "test", latlon)
                }

                item {
                    NewLocationInputs(settingsViewModel)
                }
            }
        }
    }
}

@Composable
fun WeatherItemSettings(settingsViewModel: SettingsViewModel, name: String, latlon: kotlin.Pair<String, String>) {
    Card (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ){
                Text(
                    text=latlon.first.take(7),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(12.dp)
                )
                Text(
                    text=latlon.second.take(7),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(12.dp)
                )

            IconButton(
                onClick = { settingsViewModel.removeLocalization(latlon.first, latlon.second) },
                modifier = Modifier
                    .align(Alignment.CenterVertically)

            ) {
                Icon(
                    imageVector = Icons.Default.RemoveCircleOutline,
                    tint = Color.Red,
                    contentDescription = "Remove",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun NewLocationInputs(settingsViewModel: SettingsViewModel) {
    Card (
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        var lat by remember{ mutableStateOf("") }
        var lon by remember{ mutableStateOf("") }

        Row {
            Column(modifier = Modifier.weight(0.3f)) {
                TextField(
                    value = lat,
                    onValueChange = { lat = it },
                    placeholder = { Text("Latitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                TextField(
                    value = lon,
                    onValueChange = { lon = it },
                    placeholder = { Text("Longitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
            }

            IconButton(
                onClick = { settingsViewModel.addLocalization(lat,lon) },
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    tint = Color.Black,
                    contentDescription = "Add",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .padding(4.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun SettingsViewPreview() {
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = viewModel()

    MaterialTheme{
        SettingsView(navController, settingsViewModel, {})
    }
}
