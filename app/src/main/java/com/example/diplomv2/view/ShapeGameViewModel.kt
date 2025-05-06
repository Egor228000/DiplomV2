package com.example.diplomv2.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.diplomv2.screens.ShapeStatEntity
import com.example.diplomv2.tasks.ShapeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShapeGameViewModel(
    private val shapeRepository: ShapeRepository
) : ViewModel() {

    val shapeStats = shapeRepository.getAll().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun saveShapeStat(correctAnswers: Int) {
        viewModelScope.launch {
            shapeRepository.insert(
                ShapeStatEntity(correctAnswers = correctAnswers, timestamp = System.currentTimeMillis())
            )
        }
    }
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