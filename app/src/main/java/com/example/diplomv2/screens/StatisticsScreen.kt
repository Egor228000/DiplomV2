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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun StatisticsScreen(
    mathQuizViewModel: MathQuizViewModel,
    expressQuizViewModel: ExpressQuizViewModel,
    shapeGameViewModel: ShapeGameViewModel
) {
    // Для сохранения статистики в пнг
    val context = LocalContext.current
    val bitmapRef = remember { mutableStateOf<Bitmap?>(null) }
    val textRef = remember { mutableStateOf<ComposeView?>(null) }

    val stats by mathQuizViewModel.statistics.collectAsState()
    val levels by mathQuizViewModel.levels.collectAsState()
    var selectedDifficulty by remember { mutableStateOf("") }

    // Доступные уровни сложности
    val difficulties = levels.map { it.difficulty }.distinct()
    if (selectedDifficulty.isEmpty() && difficulties.isNotEmpty()) {
        selectedDifficulty = difficulties.first()
    }


    // Общая статистика по всем уровням
    val totalStats = stats.groupBy { it.levelId }.map { (levelId, statsList) ->
        val level = levels.find { it.id == levelId }
        val totalCorrectAnswers = statsList.sumOf { it.correctAnswers }
        Pair(level?.title ?: "Скоростоной экспресс", totalCorrectAnswers)
    }



    // Подготовка данных для общего графика
    val totalLineData = listOf(
        Line(
            label = "Общее количество правильных ответов",
            values = totalStats.map { it.second.toDouble() },
            color = SolidColor(Color(0xFF00FF27)), // Красный цвет для общего графика
            firstGradientFillColor = Color(0xFF2F813B).copy(alpha = .7f),
            secondGradientFillColor = Color.Transparent,
            strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
            gradientAnimationDelay = 1000,
            drawStyle = DrawStyle.Stroke(width = 2.dp),
        )
    )
    AndroidView(
        modifier = Modifier.background(Color.White).fillMaxSize()
            .onGloballyPositioned { }, // required to render first
        factory = { ctx ->
            ComposeView(ctx).apply {
                setContent {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize()

                        ) {

                            items(1) {
// Показать общий график по всем уровням
                                Text(
                                    "Общее количество правильных ответов по уровням",
                                    fontSize = 18.sp
                                )
                                LineChart(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp)
                                        .padding(horizontal = 22.dp),

                                    data = totalLineData,
                                    animationMode = AnimationMode.Together(delayBuilder = { it * 500L }),
                                )
                            }
                            items(1) {
                                // Дополнительно показываем общую статистику по уровням
                                Text(
                                    "Общее количество правильных ответов по уровням",
                                    fontSize = 18.sp
                                )
                                totalStats.forEach { (levelTitle, totalCorrectAnswers) ->
                                    Spacer(modifier = Modifier.padding(top = 8.dp))
                                    Text("$levelTitle: $totalCorrectAnswers")
                                }
                            }

                            items(1) {
                                val expressStats by expressQuizViewModel.expressStats.collectAsState()

                                Text("📊 Статистика 'Скоростной экспресс'", fontSize = 18.sp)
                                if (expressStats.isEmpty()) {
                                    Text("Нет данных.", fontSize = 18.sp)
                                } else {
                                    val data = expressStats.map { it.correctAnswers.toDouble() }

                                    LineChart(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp)
                                            .padding(horizontal = 22.dp),
                                        data = listOf(
                                            Line(
                                                label = "Правильные ответы за 60 секунд",
                                                values = data,
                                                color = SolidColor(Color(0xFF56CCF2)),
                                                firstGradientFillColor = Color(0xFF2D9CDB).copy(
                                                    alpha = .5f
                                                ),
                                                secondGradientFillColor = Color.Transparent,
                                                strokeAnimationSpec = tween(
                                                    2000,
                                                    easing = EaseInOutCubic
                                                ),
                                                gradientAnimationDelay = 1000,
                                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                                            )
                                        ),
                                        animationMode = AnimationMode.Together(delayBuilder = { it * 500L })
                                    )
                                }
                            }
                            items(1) {
                                val shapeStats by shapeGameViewModel.shapeStats.collectAsState()

                                Text("📊 Статистика 'Геометрическая станция'")
                                if (shapeStats.isEmpty()) {
                                    Text("Нет данных.")
                                } else {
                                    val shapeData = shapeStats.map { it.correctAnswers.toDouble() }

                                    LineChart(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(300.dp)
                                            .padding(horizontal = 22.dp),
                                        data = listOf(
                                            Line(
                                                label = "Правильные ответы за 60 секунд",
                                                values = shapeData,
                                                color = SolidColor(Color(0xFFFFB703)),
                                                firstGradientFillColor = Color(0xFFFFD166).copy(
                                                    alpha = .5f
                                                ),
                                                secondGradientFillColor = Color.Transparent,
                                                strokeAnimationSpec = tween(
                                                    2000,
                                                    easing = EaseInOutCubic
                                                ),
                                                gradientAnimationDelay = 1000,
                                                drawStyle = DrawStyle.Stroke(width = 2.dp),
                                            )
                                        ),
                                        animationMode = AnimationMode.Together(delayBuilder = { it * 500L })
                                    )
                                }
                                Spacer(modifier = Modifier.padding(bottom = 18.dp))
                                Button(onClick = {
                                    mathQuizViewModel.viewModelScope.launch {
                                        textRef.value?.let { view ->
                                            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
                                            val canvas = Canvas(bitmap)
                                            view.draw(canvas)
                                            bitmapRef.value = bitmap

                                            // Сохранение в галерею
                                            bitmapRef.value?.let { bitmap ->
                                                saveBitmapToGallery(context, bitmap)
                                            }
                                        }
                                    }
                                }) {
                                    Text("Сохранить PNG в галерею")
                                }

                            }

                        }
                }
                textRef.value = this
            }
        }

    )


}

fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
    val fileName = generateFileName()

    // Проверяем, если версия Android >= Q (Android 10) используем MediaStore
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Создаем контент-значения для нового изображения
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // Имя файла
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/YourAppName") // Папка в галерее
        }

        // Получаем URI для сохранения изображения
        val contentResolver = context.contentResolver
        val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        // Если URI не пустой, сохраняем изображение
        imageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                Toast.makeText(context, "Изображение сохранено в галерее", Toast.LENGTH_LONG).show()
            }
        }
    } else {
        // Для Android ниже 10, сохраняем в старую директорию
        val directory = context.getExternalFilesDir(null)
        val file = File(directory, fileName)
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            Toast.makeText(context, "Изображение сохранено", Toast.LENGTH_LONG).show()
        }
    }
}

// Генерация имени файла (например, на основе текущей даты и времени)
fun generateFileName(): String {
    val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    val currentDate = sdf.format(Date())
    return "image_$currentDate.png"
}





/*



Column(
modifier = Modifier
.padding(16.dp)
.fillMaxSize(),
) {
    AndroidView(
        modifier = Modifier
            .size(200.dp)
            .onGloballyPositioned { }, // required to render first
        factory = { ctx ->
            ComposeView(ctx).apply {
                setContent {
                    Box(Modifier.background(Color.White).padding(16.dp)) {

                        LineChart(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 22.dp),
                            data = remember {
                                listOf(

                                    Line(
                                        label = "Windows",
                                        values = listOf(28.0, 41.0, 5.0, 10.0, 35.0),
                                        color = SolidColor(Color(0xFF23af92)),
                                        firstGradientFillColor = Color(0xFF2BC0A1).copy(alpha = .5f),
                                        secondGradientFillColor = Color.Transparent,
                                        strokeAnimationSpec = tween(
                                            2000,
                                            easing = EaseInOutCubic
                                        ),
                                        gradientAnimationDelay = 1000,
                                        drawStyle = DrawStyle.Stroke(width = 2.dp),
                                    )
                                )
                            },
                            animationMode = AnimationMode.Together(delayBuilder = {
                                it * 500L
                            }),
                        )
                    }
                }
                textRef.value = this
            }
        }
    )

    Spacer(Modifier.height(16.dp))

    Button(onClick = {
        textRef.value?.let { view ->
            val bitmap = createBitmap(view.width, view.height)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            bitmapRef.value = bitmap

            // Сохраняем PNG
            saveBitmapToStorage(context, bitmap, "text_image.png")
        }
    }) {
        Text("Сохранить PNG")
    }
}*/
