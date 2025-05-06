package com.example.diplomv2.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.screens.StatisticsDao
import com.example.diplomv2.screens.StatisticsEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExpressQuizViewModel(
    private val statisticsDao: StatisticsDao
) : ViewModel() {

    private val _expressStats = MutableStateFlow<List<StatisticsEntry>>(emptyList())
    val expressStats: StateFlow<List<StatisticsEntry>> = _expressStats.asStateFlow()

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