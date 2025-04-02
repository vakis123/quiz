package com.example.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val _lives = MutableLiveData<Int>()
    val lives: LiveData<Int> = _lives

    private val _completedLevels = MutableLiveData<Set<Int>>()
    val completedLevels: LiveData<Set<Int>> = _completedLevels

    init {
        _lives.value = 3
        _completedLevels.value = emptySet()
    }

    fun startGame() {
        _lives.value = 3
    }

    fun loseLife() {
        _lives.value = (_lives.value ?: 3) - 1
    }

    fun resetLives() {
        _lives.value = 3
    }

    fun completeLevel(level: Int) {
        val currentCompleted = _completedLevels.value ?: emptySet()
        _completedLevels.value = currentCompleted + level
    }

    fun getCompletedLevels(): Set<Int> {
        return _completedLevels.value ?: emptySet()
    }

    fun isAllLevelsCompleted(): Boolean {
        return completedLevels.value?.size == 5
    }

    fun resetGame() {
        _lives.value = 3
        _completedLevels.value = emptySet()
    }
} 