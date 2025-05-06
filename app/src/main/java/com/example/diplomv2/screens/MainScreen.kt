package com.example.diplomv2.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.R
import com.example.diplomv2.data.Screens

@Composable
fun MainScreen(
    navigation: NavHostController,
    paddingValues: PaddingValues,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp).fillMaxSize(1f)
    ) {

        items(1) {
            CardTask(
                "Математические рельсы",
                "Решай задачки и собирай золотые звёздочки! Чем больше решишь — тем длиннее твой поезд!",
                onClick = { navigation.navigate(Screens.MathRails.route) }
            )
        }
        items(1) {
            CardTask(
                "Скоростной экспресс",
                "Успей решить максимум примеров, пока не упёрся в станцию! Тик-так, время пошло!",
                onClick = { navigation.navigate(Screens.ExpressChallenge.route) }
            )
        }
        items(1) {
            CardTask(
                "Геометрическая станция",
                "Узнавай фигуры и собирай пазлы! Круглые колёса, квадратные окошки.",
                onClick = { navigation.navigate(Screens.GeometryStation.route) }
            )
        }
        item {
            CardTask(
                "Обучайка",
                "Освой правила и секреты математики! Теория, примеры, подсказки — всё в одном месте.",
                onClick = { navigation.navigate(Screens.Learning.route) }
            )
        }
        items(1) {
            Text("Новые вагончики с заданиями уже в пути! \uD83D\uDE82✨", fontSize = 20.sp, textAlign = TextAlign.Center, color = Color.Gray)

        }
    }
}


@Composable
fun CardTask(name: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier

            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
        ,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC1C1C1))
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(name, fontSize = 30.sp, color = Color.Black, fontFamily = FontFamily(Font(R.raw.kids_2)))
            Spacer(modifier = Modifier.padding(6.dp))
            Text(description, fontSize = 15.sp, color = Color.Black)
        }
    }
}