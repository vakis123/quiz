package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.random.Random

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private lateinit var questionText: TextView
    private lateinit var optionsGroup: RadioGroup
    private lateinit var submitButton: Button

    private var questions: MutableList<Pair<String, List<String>>> = mutableListOf()
    private var correctAnswer: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionText = view.findViewById(R.id.questionText)
        optionsGroup = view.findViewById(R.id.optionsGroup)
        submitButton = view.findViewById(R.id.submitButton)

        loadQuestions()
        displayNextQuestion()

        submitButton.setOnClickListener {
            checkAnswer()
        }
    }

    private fun loadQuestions() {
        val inputStream = requireContext().resources.openRawResource(R.raw.questions)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            val parts = line!!.split("|")  // Example format: "Question?|Answer1|Answer2|Answer3|Answer4"
            if (parts.size >= 2) {
                val question = parts[0]
                val answers = parts.subList(1, parts.size).shuffled(Random)
                questions.add(Pair(question, answers))
            }
        }
        reader.close()
        questions.shuffle()
    }


    private fun displayNextQuestion() {
        if (questions.isNotEmpty()) {
            val (question, answers) = questions.removeAt(0)
            correctAnswer = answers[0] // The first option in the shuffled list is the correct answer

            questionText.text = question
            optionsGroup.removeAllViews()

            for (answer in answers) {
                val radioButton = RadioButton(requireContext())
                radioButton.text = answer
                optionsGroup.addView(radioButton)
            }
        } else {
            Toast.makeText(requireContext(), "Quiz Finished!", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkAnswer() {
        val selectedId = optionsGroup.checkedRadioButtonId
        if (selectedId != -1) {
            val selectedButton = view?.findViewById<RadioButton>(selectedId)
            val selectedAnswer = selectedButton?.text.toString()

            if (selectedAnswer == correctAnswer) {
                Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Wrong! The correct answer was: $correctAnswer", Toast.LENGTH_LONG).show()
            }

            displayNextQuestion()
        } else {
            Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show()
        }
    }
}
