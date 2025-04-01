package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class GameOverFragment : Fragment(R.layout.fragment_game_over) {

    private lateinit var returnToMenuButton: Button
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnToMenuButton = view.findViewById(R.id.returnToMenuButton)
        returnToMenuButton.setOnClickListener {
            viewModel.resetLives()
            findNavController().navigate(R.id.action_gameOverFragment_to_menuFragment)
        }
    }
} 