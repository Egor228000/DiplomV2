package com.example.diplomv2.screens

import android.R.attr.maxHeight
import android.R.attr.maxWidth
import android.graphics.drawable.Icon
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.diplomv2.R
import com.example.diplomv2.data.Screens
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.ShapeGameViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    navigation: NavHostController,
    expressQuizViewModel: ExpressQuizViewModel,
    shapeGameViewModel: ShapeGameViewModel
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier

            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize()
    ) {

        items(1) {
            CardTask(
                "Математические рельсы",
                "Решай задачки и собирай золотые звёздочки! Чем больше решишь — тем длиннее твой поезд!",
                onClick = { navigation.navigate(Screens.MathRails.route) },
                icon = R.drawable.train_rails_fz82f7vze0gd
            )
        }
        items(1) {
            CardTask(
                "Скоростной экспресс",
                "Успей решить максимум примеров, пока не упёрся в станцию! Тик-так, время пошло!",
                onClick = {
                    expressQuizViewModel.resetGame()

                    navigation.navigate(Screens.ExpressChallenge.route)
                          },
                icon = R.drawable.express_qpl6mb6w73hk
            )
        }
        items(1) {
            CardTask(
                "Геометрическая станция",
                "Узнавай фигуры и собирай пазлы! Круглые колёса, квадратные окошки.",
                onClick = {
                    shapeGameViewModel.resetGame()
                    navigation.navigate(Screens.GeometryStation.route)
                          },
                icon = R.drawable.geometry_3wedqpqf6cj4
            )
        }
        item {
            CardTask(
                "Обучайка",
                "Освой правила и секреты математики! Теория, примеры, подсказки — всё в одном месте.",
                onClick = { navigation.navigate(Screens.Learning.route) },
                icon = R.drawable.learning_923qwd5bl1wh
            )
        }
        items(1) {
            Text(
                "Новые вагончики с заданиями уже в пути! \uD83D\uDE82✨",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray
            )

        }
    }
}


@Composable
fun CardTask(name: String, description: String, onClick: () -> Unit, icon: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .background(Color(0xFFC1C1C1)) // Фон карточки
    ) {
        // Слой с повторяющимися иконками на фоне
        IconBackgroundGrid(icon = icon)

        // Содержимое карточки поверх фона
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                name,
                fontSize = 35.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.raw.kids_2))
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(description, fontSize = 15.sp, color = Color.Black)
        }
    }
}


@Composable
fun IconBackgroundGrid(icon: Int) {
    val iconPainter = painterResource(id = icon)
    val rows = 20
    val cols = 20

    Column {
        repeat(rows) {
            Row {
                repeat(cols) {
                    Icon(
                        painter = iconPainter,
                        contentDescription = null,
                        tint = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier
                            .size(70.dp)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}
