package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import java.io.BufferedReader
import java.io.InputStreamReader

class Level5Fragment : Fragment(R.layout.fragment_level5) {

    private lateinit var questionNumberText: TextView
    private lateinit var hangmanView: HangmanView
    private lateinit var wordDisplay: TextView
    private lateinit var triesLeft: TextView
    private lateinit var letterGrid: GridLayout
    private lateinit var life1: TextView
    private lateinit var life2: TextView
    private lateinit var life3: TextView

    private val viewModel: QuizViewModel by activityViewModels()
    private var words: List<String> = emptyList()
    private var currentWordIndex = 0
    private var currentWord = ""
    private var guessedLetters = mutableSetOf<Char>()
    private var wrongGuesses = 0
    private var letterButtons = mutableListOf<Button>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        questionNumberText = view.findViewById(R.id.questionNumberText)
        hangmanView = view.findViewById(R.id.hangmanView)
        wordDisplay = view.findViewById(R.id.wordDisplay)
        triesLeft = view.findViewById(R.id.triesLeft)
        letterGrid = view.findViewById(R.id.letterGrid)
        life1 = view.findViewById(R.id.life1)
        life2 = view.findViewById(R.id.life2)
        life3 = view.findViewById(R.id.life3)

        // Load words
        loadWords()

        // Set up letter buttons
        setupLetterButtons()

        // Observe lives changes
        viewModel.lives.observe(viewLifecycleOwner) { lives ->
            updateLives(lives)
        }
    }

    private fun loadWords() {
        val inputStream = requireContext().resources.openRawResource(R.raw.level5)
        val reader = BufferedReader(InputStreamReader(inputStream))
        words = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                line.split(" ")[0].trim()
            }
            .toList()
            .shuffled()
        reader.close()

        if (words.isNotEmpty()) {
            startNewWord()
        }
    }

    private fun setupLetterButtons() {
        val letters = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ"
        letters.forEachIndexed { index, letter ->
            val button = Button(requireContext()).apply {
                text = letter.toString()
                textSize = 24f
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = GridLayout.LayoutParams.WRAP_CONTENT
                    columnSpec = GridLayout.spec(index % 7, 1f)
                    rowSpec = GridLayout.spec(index / 7, 1f)
                    setMargins(4, 4, 4, 4)
                }
                setBackgroundResource(R.drawable.button_background)
                setOnClickListener { onLetterSelected(letter) }
            }
            letterGrid.addView(button)
            letterButtons.add(button)
        }
    }

    private fun startNewWord() {
        if (currentWordIndex >= words.size) {
            // All words completed
            viewModel.completeLevel(5)
            if (viewModel.isAllLevelsCompleted()) {
                viewModel.resetGame() // Reset the game after completing all levels
                findNavController().navigate(R.id.action_level5Fragment_to_winFragment)
            } else {
                findNavController().navigate(R.id.action_level5Fragment_to_levelSelectionFragment)
            }
        } else {
            currentWord = words[currentWordIndex]
            guessedLetters.clear()
            wrongGuesses = 0
            hangmanView.setWrongGuesses(0)
            updateWordDisplay()
            updateTriesLeft()
            enableAllButtons()
            questionNumberText.text = "Επίπεδο 5 - Λέξη ${currentWordIndex + 1}/15"
        }
    }

    private fun updateWordDisplay() {
        val display = currentWord.map { letter ->
            if (guessedLetters.contains(letter)) letter else '_'
        }.joinToString(" ")
        wordDisplay.text = display
    }

    private fun updateTriesLeft() {
        triesLeft.text = "Προσπάθειες: ${8 - wrongGuesses}"
    }

    private fun onLetterSelected(letter: Char) {
        if (letter in guessedLetters) return

        guessedLetters.add(letter)
        letterButtons.find { it.text == letter.toString() }?.isEnabled = false

        if (letter in currentWord) {
            updateWordDisplay()
            if (currentWord.all { it in guessedLetters }) {
                // Word completed successfully
                currentWordIndex++
                startNewWord()
            }
        } else {
            wrongGuesses++
            hangmanView.setWrongGuesses(wrongGuesses)
            updateTriesLeft()

            if (wrongGuesses >= 8) {
                // Word failed
                viewModel.loseLife()
                if (viewModel.lives.value!! <= 0) {
                    // Game over
                    findNavController().navigate(R.id.action_level5Fragment_to_gameOverFragment)
                    return
                }
                currentWordIndex++
                startNewWord()
            }
        }
    }

    private fun enableAllButtons() {
        letterButtons.forEach { it.isEnabled = true }
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