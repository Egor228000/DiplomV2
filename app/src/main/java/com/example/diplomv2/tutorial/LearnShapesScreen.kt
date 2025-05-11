package com.example.diplomv2.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.R


@Composable
fun LearnShapesScreen(
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
                    text = "Фигуры вокруг нас!",
                    fontSize = 28.sp,
                    fontFamily = FontFamily(Font(R.raw.kids_2)),
                    color = Color(0xFF2D2D2D)
                )

                Text(
                    text = "Фигуры — это как формы вещей вокруг. Давай познакомимся!",
                    fontSize = 18.sp,
                    color = Color.DarkGray
                )

                Text("⬛ Квадрат — у него 4 равные стороны", fontSize = 20.sp)
                Text("⚪ Круг — он ровный и без углов", fontSize = 20.sp)
                Text("🔺 Треугольник — у него 3 стороны", fontSize = 20.sp)
                Text(
                    "🟦 Прямоугольник — похож на квадрат, но с длинными сторонами",
                    fontSize = 20.sp
                )

                Text(
                    text = "Ищи фигуры в игрушках, окнах и знаках вокруг тебя!",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF555555)
                )

                Text(
                    text = "🔍 Что нужно знать:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "✔ Все фигуры имеют форму: у круга нет углов, у треугольника — 3 угла, у квадрата — 4 одинаковые стороны.",
                    fontSize = 16.sp
                )
                Text("✔ Фигуры можно встретить в повседневной жизни — они везде!", fontSize = 16.sp)
                Text(
                    "✔ Фигуры помогают понимать мир и создавать красивые рисунки.",
                    fontSize = 16.sp
                )

                Text(
                    text = "Популярные фигуры:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Text("🟢 Круг — мяч, солнце, блин", fontSize = 18.sp)
                Text("🟦 Квадрат — окно, шахматная клетка", fontSize = 18.sp)
                Text("🔶 Ромб — знак на дороге", fontSize = 18.sp)
                Text("🟥 Прямоугольник — дверь, книга", fontSize = 18.sp)
                Text("🔺 Треугольник — крышa домика, кусочек пиццы", fontSize = 18.sp)
                Text("⭐ Звезда — символ награды", fontSize = 18.sp)

                Text(
                    text = "Попробуй найти фигуры у себя дома: подушка (прямоугольник), тарелка (круг), окошко (квадрат).",
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        }
    }
}
