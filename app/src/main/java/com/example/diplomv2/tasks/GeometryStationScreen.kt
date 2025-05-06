package com.example.diplomv2.tasks

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
    paddingValues: PaddingValues,
    shapeGameViewModel: ShapeGameViewModel
) {
    var currentProblem by remember { mutableStateOf(generateShapeProblem()) }
    var correctCount by remember { mutableStateOf(0) }
    var totalCount by remember { mutableStateOf(0) }
    var gameEnded by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(60) }

    LaunchedEffect(Unit) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft--
        }
        gameEnded = true
        shapeGameViewModel.viewModelScope.launch {
            shapeGameViewModel.saveShapeStat(correctCount)
        }
    }

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (!gameEnded) {
            Text("⏳ Осталось времени: $timeLeft сек", fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))

            val shapeWithSize = getShapeWithSize(currentProblem.correctShape)
            Box(
                modifier = Modifier
                    .size(
                        width = 100.dp * shapeWithSize.widthRatio,
                        height = 100.dp * shapeWithSize.heightRatio
                    )
                    .background(
                        color = currentProblem.color,
                        shape = shapeWithSize.shape
                    )
            )

            Spacer(Modifier.height(16.dp))

            currentProblem.options.forEach { option ->
                CustomButton(
                    onClick = {
                        totalCount++
                        if (option == currentProblem.correctShape) correctCount++
                        currentProblem = generateShapeProblem()
                    },
                    option,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🏁 Время вышло!", fontSize = 18.sp)

                if (correctCount == totalCount) {
                    Text("Молодец. Продролжай в том же духе!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.good
                        )
                    )
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth(0.7f),
                    )
                } else if (correctCount > 10 || totalCount > 20) {
                    Text("Молодец. Продролжай в том же духе!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.five
                        )
                    )
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        iterations = LottieConstants.IterateForever
                    )
                } else if (correctCount <= 5 || totalCount > 15) {
                    Text("Ты идешь хорошо, но можешь лучше!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.maybe
                        )
                    )
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        iterations = LottieConstants.IterateForever
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Правильных ответов: $correctCount из $totalCount", fontSize = 18.sp)
                    Spacer(Modifier.height(16.dp))
                    CustomButton(
                        onClick = { navigation.popBackStack() },
                        "Назад",
                        modifier = Modifier.fillMaxWidth(1f)
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

// Новая функция для получения формы с соотношением сторон
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
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
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
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
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
