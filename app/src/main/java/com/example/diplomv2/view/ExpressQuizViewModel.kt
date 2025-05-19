package com.example.diplomv2.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.screens.StatisticsDao
import com.example.diplomv2.screens.StatisticsEntry
import com.example.diplomv2.tasks.MathProblem
import com.example.diplomv2.tasks.generateProblem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpressQuizViewModel(
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    private val _expressStats = MutableStateFlow<List<StatisticsEntry>>(emptyList())
    val expressStats: StateFlow<List<StatisticsEntry>> = _expressStats.asStateFlow()

    // State for express challenge
    private val _timeLeft = MutableStateFlow(60)
    val timeLeft: StateFlow<Int> = _timeLeft.asStateFlow()

    private val _currentProblem = MutableStateFlow(generateProblem())
    val currentProblem: StateFlow<MathProblem> = _currentProblem.asStateFlow()

    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount.asStateFlow()

    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    private val _gameEnded = MutableStateFlow(false)
    val gameEnded: StateFlow<Boolean> = _gameEnded.asStateFlow()

    private var timerStarted = false
    private var timerJob: Job? = null // ← Сохраняем корутину таймера

    init {
        loadExpressStats()
    }

    private fun loadExpressStats() {
        viewModelScope.launch {
            _expressStats.value = statisticsDao.getExpressStats()
        }
    }

    fun saveExpressStat(correctAnswers: Int) {
        viewModelScope.launch {
            val starsEarned = correctAnswers.coerceAtMost(0)
            val entry = StatisticsEntry(
                levelId = -1,
                correctAnswers = correctAnswers,
                starsEarned = starsEarned,
                isExpress = true
            )
            statisticsDao.insertStat(entry)
            loadExpressStats()
        }
    }

    fun startTimer() {
        if (timerJob?.isActive == true) return // already running
        if (timerStarted) return
        timerStarted = true
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0) {
                kotlinx.coroutines.delay(1000)
                _timeLeft.value--
            }
            _gameEnded.value = true
            saveExpressStat(_correctCount.value)
        }
    }

    fun answerSelected(answer: Int) {
        _totalCount.value++
        if (answer == _currentProblem.value.correctAnswer) {
            _correctCount.value++
        }
        _currentProblem.value = generateProblem()
    }

    fun resetGame() {
        timerJob?.cancel() // ← Останавливаем таймер
        _timeLeft.value = 60
        _correctCount.value = 0
        _totalCount.value = 0
        _gameEnded.value = false
        _currentProblem.value = generateProblem()
        timerStarted = false
    }
}


class ExpressQuizViewModelFactory(
    private val statisticsDao: StatisticsDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpressQuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpressQuizViewModel(statisticsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}