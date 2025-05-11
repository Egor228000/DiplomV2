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
fun LearnMultiplicationScreen(
    navigation: NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(1) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Что такое умножение?",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.raw.kids_2)),
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = "Умножение — это когда мы складываем одно и то же число несколько раз.\n" +
                            "Например: 2 × 3 — это то же самое, что 2 + 2 + 2.",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "2 × 3 = 6",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3CB371)
                    )
                }

                Text(
                    "🐱🐱  × 3 = 🐱🐱 + 🐱🐱 + 🐱🐱 = 🐱🐱🐱🐱🐱🐱",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Подсказка: умножение — это как делать одинаковые шаги по лестнице!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF555555)
                )

                Text(
                    text = "🔍 Что нужно знать:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("✔ Умножение — это повторение сложения одного и того же числа.", fontSize = 16.sp)
                Text("✔ 3 × 4 — это 3 раза по 4, или 4 + 4 + 4.", fontSize = 16.sp)
                Text("✔ Переместительный закон: 3 × 4 = 4 × 3.", fontSize = 16.sp)
                Text("✔ Умножение на 1 — это то же число: 5 × 1 = 5.", fontSize = 16.sp)
                Text("✔ Умножение на 0 — всегда 0: 9 × 0 = 0.", fontSize = 16.sp)

                Text(
                    text = "Примеры:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text("🐶 3 × 2 = 🐶 + 🐶 + 🐶 = 6", fontSize = 18.sp)
                Text("🍎 4 × 1 = 🍎 + 🍎 + 🍎 + 🍎 = 4", fontSize = 18.sp)
                Text("🐟 2 × 5 = 🐟 + 🐟 + 🐟 + 🐟 + 🐟 = 10", fontSize = 18.sp)
                Text("👟 5 × 3 = 15 (5 шагов по 3 раза!)", fontSize = 18.sp)
                Text("💡 10 × 0 = 0 (ничего не прибавили!)", fontSize = 18.sp)

                Text(
                    text = "Представь: у тебя 4 коробки по 3 яблока 🍎. Сколько всего яблок?",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )

                Text(
                    text = "🍎🍎🍎 + 🍎🍎🍎 + 🍎🍎🍎 + 🍎🍎🍎 = 12 яблок!",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Умножение — это волшебный способ быстро узнать, сколько всего!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
    }
}
