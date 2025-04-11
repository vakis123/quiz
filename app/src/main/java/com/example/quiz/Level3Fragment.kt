package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import java.io.BufferedReader
import java.io.InputStreamReader

class Level3Fragment : Fragment(R.layout.fragment_level3) {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var life1: TextView
    private lateinit var life2: TextView
    private lateinit var life3: TextView

    private val viewModel: QuizViewModel by activityViewModels()
    private var questions: List<Pair<String, Boolean>> = emptyList()
    private var currentQuestionIndex = 0
    private var currentLevel = 3

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        questionText = view.findViewById(R.id.questionText)
        questionNumberText = view.findViewById(R.id.questionNumberText)
        trueButton = view.findViewById(R.id.trueButton)
        falseButton = view.findViewById(R.id.falseButton)
        life1 = view.findViewById(R.id.life1)
        life2 = view.findViewById(R.id.life2)
        life3 = view.findViewById(R.id.life3)

        // Load questions
        loadQuestions()

        // Set up button click listeners
        trueButton.setOnClickListener { checkAnswer(true) }
        falseButton.setOnClickListener { checkAnswer(false) }

        // Observe lives changes
        viewModel.lives.observe(viewLifecycleOwner) { lives ->
            updateLives(lives)
        }
    }

    private fun loadQuestions() {
        val inputStream = requireContext().resources.openRawResource(R.raw.level3)
        val reader = BufferedReader(InputStreamReader(inputStream))
        questions = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(" ")
                val question = parts.dropLast(1).joinToString(" ")
                val answer = parts.last() == "+"
                Pair(question, answer)
            }
            .toList()
            .shuffled()
        reader.close()

        if (questions.isNotEmpty()) {
            displayQuestion()
        }
    }

    private fun displayQuestion() {
        questionNumberText.text = "Επίπεδο $currentLevel - Ερώτηση ${currentQuestionIndex + 1}/10"
        questionText.text = questions[currentQuestionIndex].first
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questions[currentQuestionIndex].second

        if (userAnswer == correctAnswer) {
            // Correct answer
            currentQuestionIndex++
            
            if (currentQuestionIndex >= questions.size) {
                // All questions completed
                viewModel.completeLevel(3)
                if (viewModel.isAllLevelsCompleted()) {
                    viewModel.resetGame() // Reset the game after completing all levels
                    findNavController().navigate(R.id.action_level3Fragment_to_winFragment)
                } else {
                    findNavController().navigate(R.id.action_level3Fragment_to_levelSelectionFragment)
                }
            } else {
                displayQuestion()
            }
        } else {
            // Wrong answer
            viewModel.loseLife()
            currentQuestionIndex++
            
            if (viewModel.lives.value!! <= 0) {
                // Game over
                findNavController().navigate(R.id.action_level3Fragment_to_gameOverFragment)
            } else if (currentQuestionIndex >= questions.size) {
                // Level completed successfully
                viewModel.completeLevel(3)
                findNavController().navigate(R.id.action_level3Fragment_to_levelSelectionFragment)
            } else {
                displayQuestion()
            }
        }
    }

    private fun updateLives(lives: Int) {
        when (lives) {
            3 -> {
                life1.visibility = View.VISIBLE
                life2.visibility = View.VISIBLE
                life3.visibility = View.VISIBLE
            }
            2 -> {
                life1.visibility = View.VISIBLE
                life2.visibility = View.VISIBLE
                life3.visibility = View.GONE
            }
            1 -> {
                life1.visibility = View.VISIBLE
                life2.visibility = View.GONE
                life3.visibility = View.GONE
            }
            0 -> {
                life1.visibility = View.GONE
                life2.visibility = View.GONE
                life3.visibility = View.GONE
            }
        }
    }
} 