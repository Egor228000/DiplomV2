package com.example.diplomv2.tasks

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diplomv2.R
import com.example.diplomv2.screens.ShapeStatDao
import com.example.diplomv2.screens.ShapeStatEntity
import com.example.diplomv2.ui.theme.CustomButton
import com.example.diplomv2.view.ShapeGameViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun GeometryStationScreen(
    navigation: NavHostController,
    shapeGameViewModel: ShapeGameViewModel
) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableStateOf(0L) }

    // Обработка выхода
    BackHandler {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            navigation.popBackStack()
        } else {
            Toast.makeText(context, "Нажмите ещё раз для выхода", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    LaunchedEffect(Unit) {
        shapeGameViewModel.startTimer()
    }

    val currentProblem = shapeGameViewModel.currentProblem
    val correctCount = shapeGameViewModel.correctCount
    val totalCount = shapeGameViewModel.totalCount
    val timeLeft = shapeGameViewModel.timeLeft
    val gameEnded = shapeGameViewModel.gameEnded

    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (!gameEnded) {
            Text("⏳ Осталось времени: $timeLeft сек", fontSize = 18.sp)
            Spacer(Modifier.height(64.dp))

            val shapeWithSize = getShapeWithSize(currentProblem.correctShape)
            Box(
                modifier = Modifier
                    .size(
                        width = 150.dp * shapeWithSize.widthRatio,
                        height = 150.dp * shapeWithSize.heightRatio
                    )
                    .background(
                        color = currentProblem.color,
                        shape = shapeWithSize.shape
                    )
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                currentProblem.options.forEach { option ->
                    Spacer(modifier = Modifier.padding(top = 16.dp))
                    CustomButton(
                        onClick = {
                            shapeGameViewModel.selectAnswer(option)
                        },
                        option,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🏁 Время вышло!", fontSize = 18.sp)

                val compositionRes = when {
                    correctCount == totalCount -> R.raw.good
                    correctCount > 10 || totalCount > 20 -> R.raw.five
                    correctCount <= 5 || totalCount > 15 -> R.raw.maybe
                    else -> R.raw.maybe
                }
                val composition by rememberLottieComposition(
                    LottieCompositionSpec.RawRes(compositionRes)
                )
                LottieAnimation(
                    composition,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    iterations = LottieConstants.IterateForever
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Правильных ответов: $correctCount из $totalCount", fontSize = 18.sp)

                    Spacer(Modifier.height(8.dp))
                    CustomButton(
                        onClick = { navigation.popBackStack() },
                        "Назад",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


data class ShapeProblem(
    val correctShape: String,
    val options: List<String>,
    val color: Color
)

fun generateShapeProblem(): ShapeProblem {
    val shapes = listOf(
        "Круг", "Треугольник", "Квадрат", "Прямоугольник",
        "Ромб"
    )
    val correct = shapes.random()
    val options = shapes.shuffled().take(4).toMutableList()
    if (!options.contains(correct)) {
        options[Random.nextInt(options.size)] = correct
    }
    val color = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow).random()

    return ShapeProblem(correct, options.shuffled(), color)
}

data class ShapeWithSize(val shape: Shape, val widthRatio: Float, val heightRatio: Float)

fun getShapeWithSize(name: String): ShapeWithSize {
    return when (name) {
        "Круг" -> ShapeWithSize(CircleShape, 1f, 1f)
        "Квадрат" -> ShapeWithSize(RectangleShape, 1f, 1f)
        "Прямоугольник" -> ShapeWithSize(RectangleShape, 1.5f, 1f)
        "Треугольник" -> ShapeWithSize(TriangleShape(), 1f, 1f)
        "Ромб" -> ShapeWithSize(DiamondShape(), 1f, 1f)
        else -> ShapeWithSize(RectangleShape, 1f, 1f)
    }
}

class TriangleShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

class DiamondShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height / 2f)
            lineTo(size.width / 2f, size.height)
            lineTo(0f, size.height / 2f)
            close()
        }
        return Outline.Generic(path)
    }
}

class ShapeRepository(private val dao: ShapeStatDao) {
    suspend fun insert(stat: ShapeStatEntity) = dao.insert(stat)
    fun getAll(): Flow<List<ShapeStatEntity>> = dao.getAll()
}
