package com.example.diplomv2.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.screens.StatisticsDao
import com.example.diplomv2.screens.StatisticsEntry
import com.example.diplomv2.tasks.Level
import com.example.diplomv2.tasks.MathProblem
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.random.Random

class MathQuizViewModel(
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    private val _statistics = MutableStateFlow<List<StatisticsEntry>>(emptyList())
    val statistics: StateFlow<List<StatisticsEntry>> = _statistics.asStateFlow()

    private val _levels = MutableStateFlow<List<Level>>(emptyList())
    val levels: StateFlow<List<Level>> = _levels.asStateFlow()

    private val _currentLevelIndex = MutableStateFlow(0)
    val currentLevelIndex: StateFlow<Int> = _currentLevelIndex.asStateFlow()

    private val _currentProblemIndex = MutableStateFlow(0)
    val currentProblemIndex: StateFlow<Int> = _currentProblemIndex.asStateFlow()

    private val _selectedOption = MutableStateFlow<Int?>(null) // Состояние выбранного ответа
    val selectedOption: StateFlow<Int?> = _selectedOption.asStateFlow()

    val currentAnswerResult = MutableStateFlow<Boolean?>(null)

    private var answerResults = mutableListOf<Boolean>()

    init {
        loadLevels()
        loadStats()
    }

    private fun loadStats() {
        viewModelScope.launch {
            _statistics.value = statisticsDao.getAllStats()
        }
    }

    private suspend fun saveStat(levelId: Int, correctAnswers: Int, stars: Int) {
        val updated = StatisticsEntry(
            levelId = levelId,
            correctAnswers = correctAnswers,
            starsEarned = stars
        )
        statisticsDao.insertStat(updated)
        loadStats()
    }

    fun checkAnswer(selectedAnswer: Int): Boolean {
        val currentLevel = levels.value[currentLevelIndex.value]
        val currentProblem = currentLevel.problems[currentProblemIndex.value]
        val isCorrect = selectedAnswer == currentProblem.correctAnswer

        _selectedOption.value = selectedAnswer // Сохраняем выбранный ответ
        currentAnswerResult.value = isCorrect
        answerResults.add(isCorrect)

        // Подготовка к переходу на следующий вопрос
        viewModelScope.launch {
            delay(400) // Задержка, чтобы пользователь успел увидеть ответ

            // Переход к следующему вопросу
            if (_currentProblemIndex.value < currentLevel.problems.size - 1) {
                _currentProblemIndex.value++
            } else {
                // Завершаем уровень, сохраняем статистику и переходим к следующему уровню
                saveLevelResults(currentLevel)

                // Переход к следующему уровню
                if (_currentLevelIndex.value < levels.value.size - 1) {
                    _currentLevelIndex.value++ // Переход к следующему уровню
                    _currentProblemIndex.value = 0 // Начинаем с первого вопроса следующего уровня
                }
            }

            // Сброс состояния после завершения текущего вопроса
            _selectedOption.value = null
            currentAnswerResult.value = null
        }

        return isCorrect
    }

    private suspend fun saveLevelResults(currentLevel: Level) {
        val correct = answerResults.count { it }
        val total = answerResults.size
        val stars = calculateStars(correct, total)

        // Сохраняем статистику
        saveStat(currentLevel.id, correct, stars)

        // Обновляем список уровней
        _levels.value = _levels.value.map {
            if (it.id == currentLevel.id) it.copy(isCompleted = true, starsEarned = stars)
            else it
        }
    }

    private fun calculateStars(correct: Int, total: Int): Int {
        return when (correct) {
            total -> 5  // Все ответы правильные — 5 звёзд
            total - 1 -> 4  // 4 правильных ответа — 4 звезды
            total - 2 -> 3  // 3 правильных ответа — 3 звезды
            total - 3 -> 2  // 2 правильных ответа — 2 звезды
            total - 4 -> 1  // 1 правильный ответ — 1 звезда
            else -> 0  // 0 правильных ответов — 0 звёзд
        }
    }

    fun selectLevel(index: Int) {
        val level = levels.value[index]

        viewModelScope.launch {
            // Если уровень уже завершён — удалить старую статистику
            if (level.isCompleted) {
                statisticsDao.deleteStat(level.id)  // ← добавь такой метод в DAO
                // Сбросим уровень в списке
                _levels.value = _levels.value.map {
                    if (it.id == level.id) it.copy(isCompleted = false, starsEarned = 0)
                    else it
                }
            }

            // Сброс текущего состояния
            _currentLevelIndex.value = index
            _currentProblemIndex.value = 0
            answerResults.clear()
            currentAnswerResult.value = null
            _selectedOption.value = null
        }
    }

    private fun loadLevels() {
        val generatedLevels: List<Level> = listOf(
            Level(1, "Базовый", "basic", ProblemGenerator.generateBasicProblems()),
            Level(2, "Средний", "intermediate", ProblemGenerator.generateIntermediateProblems()),
            Level(3, "Продвинутый", "advanced", ProblemGenerator.generateAdvancedProblems()),
            Level(4, "Эксперт", "expert", ProblemGenerator.generateExpertProblems()),
            Level(5, "Божественный", "god master", ProblemGenerator.generateGodLevelProblems())
        )

        viewModelScope.launch {
            val stats = statisticsDao.getAllStats()
            _levels.value = generatedLevels.map { level ->
                val stat = stats.find { it.levelId == level.id }
                if (stat != null) {
                    level.copy(
                        isCompleted = stat.correctAnswers > 0,
                        starsEarned = stat.correctAnswers.coerceAtMost(5)
                    )
                } else level
            }
        }
    }


}
object ProblemGenerator {

    fun generateBasicProblems(): List<MathProblem> = List(5) {
        val a = Random.nextInt(1, 10)
        val b = Random.nextInt(1, 10)
        val result = a + b
        val options = generateOptions(result)
        MathProblem("$a + $b", result, options)
    }

    fun generateIntermediateProblems(): List<MathProblem> = List(5) {
        val a = Random.nextInt(10, 30)
        val b = Random.nextInt(1, 15)
        val result = a - b
        val options = generateOptions(result)
        MathProblem("$a - $b", result, options)
    }

    fun generateAdvancedProblems(): List<MathProblem> = List(5) {
        val a = Random.nextInt(2, 10)
        val b = Random.nextInt(2, 10)
        val result = a * b
        val options = generateOptions(result)
        MathProblem("$a × $b", result, options)
    }

    fun generateExpertProblems(): List<MathProblem> = List(5) {
        val a = Random.nextInt(2, 10)
        val b = Random.nextInt(2, 10)
        val result = a * b
        val c = Random.nextInt(1, 10)
        val final = result + c
        val options = generateOptions(final)
        MathProblem("$a × $b + $c", final, options)
    }

    fun generateGodLevelProblems(): List<MathProblem> = List(5) {
        val a = Random.nextInt(2, 10)
        val b = Random.nextInt(1, 10)
        val c = Random.nextInt(1, 10)
        val d = Random.nextInt(1, 5)
        val result = a * b - c + d
        val options = generateOptions(result)
        MathProblem("$a × $b - $c + $d", result, options)
    }

    private fun generateOptions(correct: Int): List<Int> {
        val options = mutableSetOf(correct)
        while (options.size < 4) {
            val variation = Random.nextInt(-3, 4)
            if (variation != 0) options.add(correct + variation)
        }
        return options.shuffled()
    }
}
class MathQuizViewModelFactory(
    private val statisticsDao: StatisticsDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MathQuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MathQuizViewModel(statisticsDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
