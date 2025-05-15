package com.example.diplomv2.screens

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap.createBitmap
import android.os.Environment
import android.widget.Toast
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.view.ExpressQuizViewModel
import com.example.diplomv2.view.MathQuizViewModel
import com.example.diplomv2.view.ShapeGameViewModel
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    val totalStats = stats.groupBy { it.levelId }.map { (levelId, statsList) ->
        val level = levels.find { it.id == levelId }
        val totalCorrectAnswers = statsList.sumOf { it.correctAnswers }
        Pair(level?.title ?: "Без названия", totalCorrectAnswers)
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        item {
            Text(
                text = "🚄 Статистика 'Скоростной экспресс'",
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
                text = "🔷 Статистика 'Геометрическая станция'",
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
