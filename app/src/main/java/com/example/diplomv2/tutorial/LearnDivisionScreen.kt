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
fun LearnDivisionScreen(
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
                    text = "Что такое деление?",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.raw.kids_2)),
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = "Деление — это когда мы делим что-то поровну между друзьями или группами.\n" +
                            "Например, у тебя есть 6 конфет 🍬, и ты хочешь поделить их между 2 друзьями. Каждому достанется по 3 🍬!",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        "6 ÷ 2 = 3",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3CB371)
                    )
                }

                Text(
                    "🍬🍬🍬🍬🍬🍬 ÷ 2 друга = 🍬🍬🍬 каждому",
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Подсказка: представь, как делишь игрушки между друзьями — это и есть деление!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF555555)
                )

                Text(
                    text = "🔍 Что нужно знать:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text("✔ Деление — это обратное умножение.", fontSize = 16.sp)
                Text("✔ Если ты знаешь, что 3 × 2 = 6, то 6 ÷ 2 = 3.", fontSize = 16.sp)
                Text("✔ Делимое ÷ делитель = частное.", fontSize = 16.sp)
                Text(
                    "✔ Делить можно всё: конфеты, игрушки, пиццу, даже внимание учителя!",
                    fontSize = 16.sp
                )

                Text(
                    text = "Примеры:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text(
                    "🍕 8 ÷ 2 = 4   (8 кусочков пиццы на 2 человека — по 4 каждому)",
                    fontSize = 18.sp
                )
                Text("🍬 9 ÷ 3 = 3   (9 конфет — 3 друга)", fontSize = 18.sp)
                Text("⚽ 10 ÷ 5 = 2  (10 мячей на 5 команд)", fontSize = 18.sp)
                Text("🧃 6 ÷ 1 = 6   (никого не делим — всё одному!)", fontSize = 18.sp)
                Text("🧩 0 ÷ 4 = 0   (нет пазлов — никому не достанется)", fontSize = 18.sp)
                Text("🐻 12 ÷ 4 = 3 (12 игрушек — 4 друга)", fontSize = 18.sp)
                Text("🎈 15 ÷ 5 = 3 (15 шариков — 5 детей)", fontSize = 18.sp)

                Text(
                    text = "Представь, у тебя 12 машинок 🚗 и 4 друга. Сколько машинок получит каждый?",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )

                Text(
                    text = "🚗🚗🚗🚗🚗🚗🚗🚗🚗🚗🚗🚗 ÷ 4 = 🚗🚗🚗 каждому",
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Деление — это как поделиться весельем с друзьями! 😊",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
    }
}

