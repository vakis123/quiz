package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class GameOverFragment : Fragment(R.layout.fragment_game_over) {

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val returnToMenuButton: Button = view.findViewById(R.id.returnToMenuButton)
        returnToMenuButton.setOnClickListener {
            viewModel.resetGame()
            findNavController().navigate(R.id.action_gameOverFragment_to_menuFragment)
        }
    }
} 