package com.example.diplomv2.tasks

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diplomv2.R
import com.example.diplomv2.ui.theme.CustomButton
import com.example.diplomv2.ui.theme.CustomText
import com.example.diplomv2.view.ExpressQuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun ExpressChallengeScreen(
    navigation: NavHostController,
    expressQuizViewModel: ExpressQuizViewModel
) {
    val context = LocalContext.current
    var backPressedTime by remember { mutableStateOf(0L) }

    val timeLeft by expressQuizViewModel.timeLeft.collectAsState()
    val currentProblem by expressQuizViewModel.currentProblem.collectAsState()
    val correctCount by expressQuizViewModel.correctCount.collectAsState()
    val totalCount by expressQuizViewModel.totalCount.collectAsState()
    val gameEnded by expressQuizViewModel.gameEnded.collectAsState()

    LaunchedEffect(Unit) {
        expressQuizViewModel.startTimer()
    }

    BackHandler {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            expressQuizViewModel.resetGame()
            navigation.popBackStack()
        } else {
            Toast.makeText(
                context,
                "Нажмите ещё раз для выхода",
                Toast.LENGTH_SHORT
            ).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
    ) {
        if (!gameEnded) {
            Text("⏳ Осталось времени: $timeLeft сек", fontSize = 18.sp)
            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(Color(0xFFC1C1C1))
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    CustomText(
                        "Реши: ${currentProblem.question}",
                        modifier = Modifier,
                        textStyle = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                currentProblem.options.forEach { option ->
                    CustomButton(
                        onClick = {
                            expressQuizViewModel.answerSelected(option)
                        },
                        "$option",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
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

                if (correctCount == totalCount) {
                    Text("Молодец. Продролжай в том же духе!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.good)
                    )
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                } else if (correctCount > 10 || totalCount > 20) {
                    Text("Молодец. Продролжай в том же духе!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.five)
                    )
                    LottieAnimation(
                        composition,
                        modifier = Modifier.fillMaxWidth(0.7f),
                        iterations = LottieConstants.IterateForever
                    )
                } else if (correctCount <= 5 || totalCount > 15) {
                    Text("Ты идешь хорошо, но можешь лучше!")
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(R.raw.maybe)
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
                        onClick = {
                            expressQuizViewModel.resetGame()
                            navigation.popBackStack()
                                  },
                        "Назад",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}


fun generateProblem(): MathProblem {
    val operations = listOf("+", "-", "*", "/")
    val operation = operations.random()

    val (a, b, question, correctAnswer) = when (operation) {
        "+" -> {
            val a = (1..10).random()
            val b = (1..10).random()
            Quad(a, b, "$a + $b", a + b)
        }

        "-" -> {
            val a = (5..10).random() // чтобы не было отрицательных
            val b = (1..a).random()
            Quad(a, b, "$a - $b", a - b)
        }

        "*" -> {
            val a = (1..10).random()
            val b = (1..10).random()
            Quad(a, b, "$a × $b", a * b)
        }

        "/" -> {
            val b = (1..10).random()
            val correct = (1..10).random()
            val a = b * correct // чтобы делилось нацело
            Quad(a, b, "$a ÷ $b", correct)
        }

        else -> {
            val a = 1
            val b = 1
            Quad(a, b, "$a + $b", a + b)
        }
    }

    val options = mutableSetOf(correctAnswer)
    while (options.size < 4) {
        val wrong = correctAnswer + (-5..5).random()
        if (wrong != correctAnswer && wrong >= 0) options.add(wrong)
    }

    return MathProblem(
        question = question,
        correctAnswer = correctAnswer,
        options = options.shuffled()
    )
}

data class Quad(val a: Int, val b: Int, val question: String, val correct: Int)
