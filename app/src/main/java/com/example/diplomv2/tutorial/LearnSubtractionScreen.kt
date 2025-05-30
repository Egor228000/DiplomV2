package com.example.diplomv2.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.R

@Composable
fun LearnSubtractionScreen(
    navigation: NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(1) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

                Text(
                    text = "Что такое вычитание?",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.raw.kids_2)),
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = "Вычитание — это когда мы что-то убираем или теряем.\nНапример, у тебя было 5 яблок 🍎, ты съел 2 🍎. Сколько осталось?",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("5", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(" - ", fontSize = 24.sp)
                    Text("2", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(" = ", fontSize = 24.sp)
                    Text(
                        "3",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3CB371)
                    )
                }

                Text(
                    text = "🍎🍎🍎🍎🍎  -  🍎🍎  =  🍎🍎🍎",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Подсказка: представь, что убираешь игрушки — вот это и есть вычитание!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF555555)
                )

                Text(
                    text = "🔍 Что нужно знать:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("✔ Вычитание — это обратное сложению.", fontSize = 16.sp)
                Text("✔ Мы всегда начинаем с большего числа и убираем меньшее.", fontSize = 16.sp)
                Text("✔ Если уберём всё — останется 0!", fontSize = 16.sp)

                Text(
                    text = "Примеры:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text("🎈 4 - 1 = 3", fontSize = 18.sp)
                Text("🍫 6 - 3 = 3", fontSize = 18.sp)
                Text("🧩 5 - 0 = 5", fontSize = 18.sp)
                Text("🐻 3 - 2 = 1", fontSize = 18.sp)
                Text("⚽ 7 - 7 = 0", fontSize = 18.sp)

                Text(
                    text = "Вычитание — как делиться: было много, поделился — стало меньше!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
    }
}
