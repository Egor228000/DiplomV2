package com.example.diplomv2.screens

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.MathQuizViewModel
import com.example.diplomv2.view.ShapeGameViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line

@Composable
fun StatisticsScreen(
    mathQuizViewModel: MathQuizViewModel,
    expressQuizViewModel: ExpressQuizViewModel,
    shapeGameViewModel: ShapeGameViewModel
) {
    val stats by mathQuizViewModel.statistics.collectAsState()
    val levels by mathQuizViewModel.levels.collectAsState()
    val expressStats by expressQuizViewModel.expressStats.collectAsState()
    val shapeStats by shapeGameViewModel.shapeStats.collectAsState()





    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        item {
            Text(
                text = "🚄 Статистика 'Быстрые задачки'",
                fontSize = 20.sp,
                color = Color.Black
            )
            if (expressStats.isEmpty()) {
                Text("Нет данных.", fontSize = 16.sp)
            } else {
                val data = expressStats.map { it.correctAnswers.toDouble() }
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    data = listOf(
                        Line(
                            label = "Правильные ответы за 60 секунд",
                            values = data,
                            color = SolidColor(Color(0xFF56CCF2)),
                            firstGradientFillColor = Color(0xFF2D9CDB).copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    animationMode = AnimationMode.Together(delayBuilder = { it * 500L })
                )
            }
        }

        item {
            Text(
                text = "🔷 Статистика 'Фигурки и формы'",
                fontSize = 20.sp,
                color = Color.Black
            )
            if (shapeStats.isEmpty()) {
                Text("Нет данных.", fontSize = 16.sp)
            } else {
                val data = shapeStats.map { it.correctAnswers.toDouble() }
                LineChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    data = listOf(
                        Line(
                            label = "Правильные ответы за 60 секунд",
                            values = data,
                            color = SolidColor(Color(0xFFFFB703)),
                            firstGradientFillColor = Color(0xFFFFD166).copy(alpha = .5f),
                            secondGradientFillColor = Color.Transparent,
                            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                            gradientAnimationDelay = 1000,
                            drawStyle = DrawStyle.Stroke(width = 2.dp),
                        )
                    ),
                    animationMode = AnimationMode.Together(delayBuilder = { it * 500L })
                )
            }
        }
    }
}
