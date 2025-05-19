package com.example.diplomv2.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.screens.ShapeStatEntity
import com.example.diplomv2.tasks.ShapeRepository
import com.example.diplomv2.tasks.generateShapeProblem
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShapeGameViewModel(
    private val shapeRepository: ShapeRepository
) : ViewModel() {


    var timeLeft by mutableStateOf(60)
        private set

    var correctCount by mutableStateOf(0)
        private set

    var totalCount by mutableStateOf(0)
        private set

    var gameEnded by mutableStateOf(false)
        private set

    var currentProblem by mutableStateOf(generateShapeProblem())
        private set

    private var timerStarted = false
    private var timerJob: Job? = null // ← Сохраняем корутину таймера

    fun startTimer() {
        if (timerJob?.isActive == true) return // already running
        if (timerStarted) return
        timerStarted = true
        timerJob = viewModelScope.launch {
            while (timeLeft > 0) {
                delay(1000)
                timeLeft--
            }
            gameEnded = true
            saveShapeStat(correctCount)
        }
    }

    fun selectAnswer(answer: String) {
        totalCount++
        if (answer == currentProblem.correctShape) correctCount++
        currentProblem = generateShapeProblem()
    }

    fun resetGame() {
        timerJob?.cancel() // ← Останавливаем таймер
        timeLeft = 60
        correctCount = 0
        totalCount = 0
        gameEnded = false
        currentProblem = generateShapeProblem()
        timerStarted = false
    }

    fun saveShapeStat(correctAnswers: Int) {
        viewModelScope.launch {
            shapeRepository.insert(
                ShapeStatEntity(correctAnswers = correctAnswers, timestamp = System.currentTimeMillis())
            )
        }
    }
    val shapeStats = shapeRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )


}

class ShapeGameViewModelFactory(
    private val shapeRepository: ShapeRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShapeGameViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ShapeGameViewModel(shapeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}