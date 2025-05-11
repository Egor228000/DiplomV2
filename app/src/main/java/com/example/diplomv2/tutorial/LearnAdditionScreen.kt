package com.example.diplomv2.tutorial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
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
fun LearnAdditionScreen(
    navigation: NavHostController,
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {

                Text(
                    text = "Что такое сложение?",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.raw.kids_2)),
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = "Сложение — это когда мы прибавляем одно число к другому.\n" +
                            "Например, если у тебя есть 2 яблока 🍎 и ты нашёл ещё 3 🍎, то всего у тебя 5!",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("2", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(" + ", fontSize = 24.sp)
                    Text("3", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(" = ", fontSize = 24.sp)
                    Text("5", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF3CB371))
                }

                Text(
                    "🍎🍎  +  🍎🍎🍎  =  🍎🍎🍎🍎🍎",
                    fontSize = 26.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )


                Text(
                    text = "🔍 Что нужно знать:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("• Сложение обозначается знаком +", fontSize = 16.sp)
                Text("• При сложении порядок чисел не важен (2 + 3 = 3 + 2)", fontSize = 16.sp)
                Text("• Результат сложения называют *суммой*", fontSize = 16.sp)


                Text(
                    text = "Совет: сначала посчитай на пальцах, потом в уме!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF555555)
                )


                Text(
                    text = "🧠 Попробуй сам:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                val examples = listOf(
                    "🐰 1 + 1 = 2",
                    "🚗 2 + 2 = 4",
                    "🍬 3 + 4 = 7",
                    "🐶 5 + 0 = 5",
                    "🌟 6 + 3 = 9",
                    "🧃 10 + 1 = 11",
                    "🎈 7 + 2 = 9",
                    "🍕 4 + 5 = 9",
                    "🐸 9 + 1 = 10"
                )

                examples.forEach { example ->
                    Text(text = example, fontSize = 18.sp)
                }


                Text(
                    text = "Сложение — как собирать подарки! 🎁 Чем больше складываешь, тем веселее!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )


            }
        }
    }
}
