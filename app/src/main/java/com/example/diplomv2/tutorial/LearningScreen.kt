package com.example.diplomv2.tutorial

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
fun LearningScreen(
    navigation: NavHostController,
) {

    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                "Обучающие материалы",
                fontSize = 28.sp,
                fontFamily = FontFamily(Font(R.raw.kids_2)),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        item {
            LearnCard(
                name = "Сложение",
                description = "Узнай, как складывать числа и тренируйся на примерах!",
                onClick = { navigation.navigate(Screens.LearnAddition.route)}
            )
        }

        item {
            LearnCard(
                name = "Вычитание",
                description = "Научись вычитать — это как брать сладости, но по науке!",
                onClick = { navigation.navigate(Screens.LearnSubtraction.route)}
            )
        }

        item {
            LearnCard(
                name = "Умножение",
                description = "Секреты таблицы умножения и крутые лайфхаки.",
                onClick = { navigation.navigate(Screens.LearnMultiplication.route) }
            )
        }

        item {
            LearnCard(
                name = "Деление",
                description = "Как честно делить конфеты между друзьями? Узнай здесь!",
                onClick = { navigation.navigate(Screens.LearnDivision.route) }
            )
        }

        item {
            LearnCard(
                name = "Фигуры и формы",
                description = "Круги, квадраты, треугольники — учим и различаем.",
                onClick = { navigation.navigate(Screens.LearnShapes.route)}
            )
        }
    }
}


@Composable
fun LearnCard(name: String, description: String, onClick: () -> Unit) {
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
