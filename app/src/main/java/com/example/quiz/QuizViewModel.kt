package com.example.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    private val _lives = MutableLiveData(3)
    val lives: LiveData<Int> = _lives

    fun loseLife() {
        _lives.value = (_lives.value ?: 3) - 1
    }

    fun resetLives() {
        _lives.value = 3
    }

    fun startGame() {
        resetLives()
    }
} 