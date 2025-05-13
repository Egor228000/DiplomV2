package com.example.diplomv2.tasks

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.diplomv2.R
import com.example.diplomv2.data.Screens
import com.example.diplomv2.ui.theme.CustomButton
import com.example.diplomv2.view.MathQuizViewModel
import kotlinx.serialization.Serializable

@Composable
fun MathRailsScreen(
    navigation: NavHostController,
    mathQuizViewModel: MathQuizViewModel
) {
    val levels by mathQuizViewModel.levels.collectAsState()


    Column(
        modifier = Modifier
            .padding(top = 50.dp)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color(0xFFF3F3F3)),

        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Решай задачки и собирай золотые звёздочки!",
                )

            }
        }

        Text(
            text = "Выбери уровень сложности:",
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            fontSize = 18.sp
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(levels) { level ->
                LevelCard(
                    level = level,
                    onClick = {
                        mathQuizViewModel.selectLevel(levels.indexOf(level))
                        navigation.navigate(Screens.Game.route)
                    }
                )
            }

        }


    }
}

@Composable
fun LevelCard(
    level: Level,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(1f),
        onClick = onClick,
        colors = CardDefaults.cardColors(Color(0xFFC1C1C1))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = level.title,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            if (level.isCompleted) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(1f)
                ) {
                    val Stars by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.stars
                        )
                    )
                    repeat(level.starsEarned) {

                        LottieAnimation(
                            Stars,
                            modifier = Modifier.size(70.dp),

                            )
                    }
                    val compositionNoStars by rememberLottieComposition(
                        LottieCompositionSpec.RawRes(
                            R.raw.no_stars
                        )
                    )
                    repeat(5 - level.starsEarned) {

                        LottieAnimation(
                            compositionNoStars,
                            modifier = Modifier.size(70.dp),

                            )
                    }
                }
            }
        }
    }
}


@Serializable
data class Level(
    val id: Int,
    val title: String,
    val difficulty: String,
    val problems: List<MathProblem>,
    val isCompleted: Boolean = false,
    val starsEarned: Int = 0
)

@Serializable
data class MathProblem(
    val question: String,
    val correctAnswer: Int,
    val options: List<Int>
)