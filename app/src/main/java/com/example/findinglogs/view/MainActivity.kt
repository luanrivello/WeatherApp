package com.example.findinglogs.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.findinglogs.R
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.example.findinglogs.model.model.Weather
import com.example.findinglogs.viewmodel.MainViewModel
import com.example.findinglogs.view.recyclerview.adapter.WeatherListAdapter
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.findinglogs.model.util.Utils
import com.example.findinglogs.viewmodel.WeatherUiInfo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme{
                WeatherMainScreen()
            }
        }

    }
}

@Composable
fun WeatherMainScreen (
    mainViewModel: MainViewModel = viewModel()
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.weather_snow_dark))
    ) {
        Column (
            modifier = Modifier
                .padding(vertical = 64.dp)
        )
        {
            TitleCard(stringResource(R.string.weather_app))

            Box(modifier = Modifier.fillMaxSize()) {
                WeatherInfoCardList(mainViewModel)

                FloatingActionButton(
                    onClick = { mainViewModel.refreshForecasts() },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 24.dp, vertical = 12.dp)
                        .height(60.dp)
                        .width(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TitleCard(title: String){
    Card (
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.weather_few_clouds)
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(116.dp)

    ) {
        Text(
            text = title,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(all = 36.dp)
        )
    }
}

@Composable
fun WeatherInfoCardList(
    mainViewModel: MainViewModel = viewModel()
) {
    val weatherList by mainViewModel.weatherList.observeAsState(emptyList())

    LazyColumn(
        modifier = Modifier.padding(horizontal = 0.dp)
    ) {
        items(weatherList){ weather ->
            WeatherInfoCard(weather)
        }
    }
}

@Composable
fun WeatherInfoCard(
    weather: WeatherUiInfo
) {
    Card (
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(weather.cardBackgroundColor)
        ),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(164.dp)
    ) {
        Row (
            modifier = Modifier.padding(2.dp)
        ) {
            Image(
                bitmap = weather.weatherIcon,
                contentDescription = "Weather Image",
                modifier = Modifier
                    .size(148.dp)
                    .padding(top = 8.dp)
            )

            Column (
                modifier = Modifier.padding(6.dp)
            ){
                WeatherInfoCardText(weather.name, 24, FontWeight.ExtraBold)
                WeatherInfoCardText("Temp. atual: ${weather.tempActual}", 16, FontWeight.Bold)
                WeatherInfoCardText("Temp. max: ${weather.tempMax}", 16)
                WeatherInfoCardText("Temp. min: ${weather.tempMin}", 16)
                WeatherInfoCardText("Pressão: ${weather.pressure}", 14)
                WeatherInfoCardText("Umidade: ${weather.humidity}", 14)
            }
        }
    }
}

@Composable
fun WeatherInfoCardText(info: String, size: Int, fontWeight: FontWeight = FontWeight.Normal){
    Text(
        text = info,
        fontWeight = fontWeight,
        fontSize = size.sp,
        modifier = Modifier
            .padding(horizontal = 4.dp)
    )
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme{
        WeatherMainScreen()
    }
}