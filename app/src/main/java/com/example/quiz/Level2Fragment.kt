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

class Level2Fragment : Fragment(R.layout.fragment_level2) {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var answer1: Button
    private lateinit var answer2: Button
    private lateinit var answer3: Button
    private lateinit var answer4: Button
    private lateinit var life1: TextView
    private lateinit var life2: TextView
    private lateinit var life3: TextView

    private val viewModel: QuizViewModel by activityViewModels()
    private var questions: List<MultipleChoiceQuestion> = emptyList()
    private var currentQuestionIndex = 0
    private var currentLevel = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        questionText = view.findViewById(R.id.questionText)
        questionNumberText = view.findViewById(R.id.questionNumberText)
        answer1 = view.findViewById(R.id.answer1)
        answer2 = view.findViewById(R.id.answer2)
        answer3 = view.findViewById(R.id.answer3)
        answer4 = view.findViewById(R.id.answer4)
        life1 = view.findViewById(R.id.life1)
        life2 = view.findViewById(R.id.life2)
        life3 = view.findViewById(R.id.life3)

        // Load questions
        loadQuestions()

        // Set up button click listeners
        val answers = listOf(answer1, answer2, answer3, answer4)
        answers.forEach { button ->
            button.setOnClickListener { checkAnswer(button.text.toString()) }
        }

        // Observe lives changes
        viewModel.lives.observe(viewLifecycleOwner) { lives ->
            updateLives(lives)
        }
    }

    private fun loadQuestions() {
        val inputStream = requireContext().resources.openRawResource(R.raw.level2)
        val reader = BufferedReader(InputStreamReader(inputStream))
        questions = reader.lineSequence()
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { lines ->
                MultipleChoiceQuestion(
                    question = lines[0],
                    correctAnswer = lines[1],
                    wrongAnswers = listOf(lines[2], lines[3], lines[4])
                )
            }
            .toList()
        reader.close()

        if (questions.isNotEmpty()) {
            displayQuestion()
        }
    }

    private fun displayQuestion() {
        questionNumberText.text = "Επίπεδο $currentLevel - Ερώτηση ${currentQuestionIndex + 1}/10"
        questionText.text = questions[currentQuestionIndex].question

        // Shuffle answers
        val answers = (questions[currentQuestionIndex].wrongAnswers + questions[currentQuestionIndex].correctAnswer).shuffled()
        
        // Set button texts
        answer1.text = answers[0]
        answer2.text = answers[1]
        answer3.text = answers[2]
        answer4.text = answers[3]
    }

    private fun checkAnswer(userAnswer: String) {
        val correctAnswer = questions[currentQuestionIndex].correctAnswer

        if (userAnswer == correctAnswer) {
            // Correct answer
            currentQuestionIndex++
            
            if (currentQuestionIndex >= questions.size) {
                // Level completed successfully
                findNavController().navigate(R.id.action_level2Fragment_to_level3Fragment)
            } else {
                displayQuestion()
            }
        } else {
            // Wrong answer
            viewModel.loseLife()
            currentQuestionIndex++
            
            if (viewModel.lives.value!! <= 0) {
                // Game over
                findNavController().navigate(R.id.action_level2Fragment_to_gameOverFragment)
            } else if (currentQuestionIndex >= questions.size) {
                // Level completed successfully
                findNavController().navigate(R.id.action_level2Fragment_to_level3Fragment)
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

data class MultipleChoiceQuestion(
    val question: String,
    val correctAnswer: String,
    val wrongAnswers: List<String>
) 