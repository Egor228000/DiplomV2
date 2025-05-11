package com.example.diplomv2.tasks

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diplomv2.view.MathQuizViewModel

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.example.diplomv2.ui.theme.CustomButton
import java.util.Locale

@Composable
fun GameScreen(
    mathQuizViewModel: MathQuizViewModel,
    onBack: () -> Unit
) {

    val context = LocalContext.current
    val tts = remember { mutableStateOf<TextToSpeech?>(null) }

    LaunchedEffect(Unit) {
        tts.value = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.value?.language = Locale("ru")
            }
        }
    }




    val levels by mathQuizViewModel.levels.collectAsState()
    val currentLevelIndex by mathQuizViewModel.currentLevelIndex.collectAsState()
    val currentProblemIndex by mathQuizViewModel.currentProblemIndex.collectAsState()

    val currentLevel = levels[currentLevelIndex]
    val currentProblem = currentLevel.problems[currentProblemIndex]
    val answerResult by mathQuizViewModel.currentAnswerResult.collectAsState()
    val selectedOption by mathQuizViewModel.selectedOption.collectAsState()

    var showAllDoneDialog by remember { mutableStateOf(false) }
    var wasDialogShown by remember { mutableStateOf(false) }

// ✅ Показываем диалог после последней задачи, не важно правильно или нет
    if (
        currentLevelIndex == levels.lastIndex &&
        currentProblemIndex == currentLevel.problems.lastIndex &&
        selectedOption != null &&
        !wasDialogShown
    ) {
        showAllDoneDialog = true
        wasDialogShown = true
    }

    if (showAllDoneDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    showAllDoneDialog = false
                    onBack() // Go back or exit
                }) {
                    Text("Отлично")
                }
            },
            title = { Text("Поздравляем!") },
            text = { Text("Вы выполнили все примеры !") },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }

    Column(
        modifier = Modifier
            .padding(16.dp)

        .padding(top = 50.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier

                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentLevel.title,
                    fontSize = 18.sp
                )
                CustomButton(
                    onClick = {
                        tts.value?.stop() // Остановить предыдущую речь
                        tts.value?.speak("Сколько будет ${currentProblem.question}?", TextToSpeech.QUEUE_FLUSH, null, null)
                    },
                    "Задача вслух",
                    modifier = Modifier
                )

                Text(
                    text = "Задача ${currentProblemIndex + 1}/${currentLevel.problems.size}",
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.White)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = currentProblem.question + " = ?",
                    fontSize = 36.sp,
                    color = Color.Black
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(currentProblem.options) { option ->

                AnswerButton(
                    number = option,
                    isSelected = selectedOption == option,
                    isCorrect = if (selectedOption == option) answerResult else null,
                    onClick = {
                        if (answerResult == null) {
                            mathQuizViewModel.checkAnswer(option)
                        }
                    }
                )
            }
        }


    }
}


@Composable
fun AnswerButton(
    number: Int,
    isSelected: Boolean,
    isCorrect: Boolean?,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect == null -> Color(0xFFE5E4E4)
        isCorrect && isSelected -> Color(0xFF388E3C)
        !isCorrect && isSelected -> Color(0xFFD32F2F)
        else -> Color(0xFFE5E4E4)
    }

    Button(
        onClick = onClick,
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(1.5.dp, Color.Red)
    ) {
        Text(text = number.toString(), fontSize = 24.sp, color = Color.Black)
    }
}
