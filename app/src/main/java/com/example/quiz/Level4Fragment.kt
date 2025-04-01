package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import java.io.BufferedReader
import java.io.InputStreamReader

class Level4Fragment : Fragment(R.layout.fragment_level4) {

    private lateinit var questionNumberText: TextView
    private lateinit var leftWord1: Button
    private lateinit var leftWord2: Button
    private lateinit var leftWord3: Button
    private lateinit var leftWord4: Button
    private lateinit var rightWord1: Button
    private lateinit var rightWord2: Button
    private lateinit var rightWord3: Button
    private lateinit var rightWord4: Button
    private lateinit var life1: TextView
    private lateinit var life2: TextView
    private lateinit var life3: TextView

    private val viewModel: QuizViewModel by activityViewModels()
    private var wordPairs: List<WordPair> = emptyList()
    private var currentLevel = 4
    private var currentPairIndex = 0
    private var selectedLeftWord: Button? = null
    private var selectedRightWord: Button? = null
    private var matchedPairs = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        questionNumberText = view.findViewById(R.id.questionNumberText)
        leftWord1 = view.findViewById(R.id.leftWord1)
        leftWord2 = view.findViewById(R.id.leftWord2)
        leftWord3 = view.findViewById(R.id.leftWord3)
        leftWord4 = view.findViewById(R.id.leftWord4)
        rightWord1 = view.findViewById(R.id.rightWord1)
        rightWord2 = view.findViewById(R.id.rightWord2)
        rightWord3 = view.findViewById(R.id.rightWord3)
        rightWord4 = view.findViewById(R.id.rightWord4)
        life1 = view.findViewById(R.id.life1)
        life2 = view.findViewById(R.id.life2)
        life3 = view.findViewById(R.id.life3)

        // Load word pairs
        loadWordPairs()

        // Set up button click listeners
        val leftButtons = listOf(leftWord1, leftWord2, leftWord3, leftWord4)
        val rightButtons = listOf(rightWord1, rightWord2, rightWord3, rightWord4)

        leftButtons.forEach { button ->
            button.setOnClickListener { onLeftWordSelected(button) }
        }

        rightButtons.forEach { button ->
            button.setOnClickListener { onRightWordSelected(button) }
        }

        // Observe lives changes
        viewModel.lives.observe(viewLifecycleOwner) { lives ->
            updateLives(lives)
        }
    }

    private fun loadWordPairs() {
        val inputStream = requireContext().resources.openRawResource(R.raw.level4)
        val reader = BufferedReader(InputStreamReader(inputStream))
        wordPairs = reader.lineSequence()
            .filter { it.isNotBlank() }
            .map { line ->
                val words = line.split(" ")
                WordPair(words[0].trim(), words[1].trim())
            }
            .map { pair ->
                // Modify the right word according to the given conditions
                val transformedRightWord = when (pair.rightWord) {
                    "Ψυχολογική", "Σωματική", "Λεκτική" -> "${pair.rightWord} Βία"
                    "Παγκόσμιο" -> "${pair.rightWord} Φαινόμενο"
                    else -> pair.rightWord
                }

                // Return a new WordPair with the transformed right word
                WordPair(pair.leftWord, transformedRightWord)
            }
            .toList()
        reader.close()

        // Take groups of 4 pairs, shuffle them, and then pick the first group
        val chunkedPairs = wordPairs.chunked(4)

        if (chunkedPairs.isNotEmpty()) {
            // Take the first 5 pairs from the first chunk (you can adjust based on your needs)
            val selectedPairs = chunkedPairs.shuffled().take(5)
            wordPairs = selectedPairs.flatten()
            displayWordPairs()
        }
    }

    private fun displayWordPairs() {
        val startIndex = currentPairIndex * 4
        val endIndex = minOf(startIndex + 4, wordPairs.size)
        val currentLevelPairs = wordPairs.subList(startIndex, endIndex)

        questionNumberText.text = "Επίπεδο $currentLevel - Αντιστοίχιση ${currentPairIndex + 1}/5"

        // Shuffle the words
        val leftWords = currentLevelPairs.map { it.leftWord }.shuffled()
        val rightWords = currentLevelPairs.map { it.rightWord }
            .shuffled()
        // Set button texts and make them visible
        leftWord1.text = leftWords[0]
        leftWord2.text = leftWords[1]
        leftWord3.text = leftWords[2]
        leftWord4.text = leftWords[3]
        rightWord1.text = rightWords[0]
        rightWord2.text = rightWords[1]
        rightWord3.text = rightWords[2]
        rightWord4.text = rightWords[3]

        // Reset selection and make all buttons visible
        selectedLeftWord = null
        selectedRightWord = null
        resetButtonColors()
        setButtonsVisibility(true)
    }

    private fun onLeftWordSelected(button: Button) {
        if (selectedLeftWord != null) {
            selectedLeftWord?.setBackgroundResource(R.drawable.button_background)
        }
        selectedLeftWord = button
        button.setBackgroundResource(R.drawable.selected_button_background)
        checkMatch()
    }

    private fun onRightWordSelected(button: Button) {
        if (selectedRightWord != null) {
            selectedRightWord?.setBackgroundResource(R.drawable.button_background)
        }
        selectedRightWord = button
        button.setBackgroundResource(R.drawable.selected_button_background)
        checkMatch()
    }

    private fun checkMatch() {
        if (selectedLeftWord != null && selectedRightWord != null) {
            val leftWord = selectedLeftWord?.text.toString()
            val rightWord = selectedRightWord?.text.toString()
            val startIndex = currentPairIndex * 4
            val currentLevelPairs = wordPairs.subList(startIndex, startIndex + 4)
            val currentPair = currentLevelPairs.find { it.matches(leftWord, rightWord) }

            if (currentPair != null) {
                // Correct match
                matchedPairs++
                selectedLeftWord?.visibility = View.GONE
                selectedRightWord?.visibility = View.GONE
                selectedLeftWord = null
                selectedRightWord = null

                if (matchedPairs >= 4) {
                    // Level completed successfully
                    currentPairIndex++
                    matchedPairs = 0
                    
                    if (currentPairIndex >= 10) {
                        // All levels completed
                        findNavController().navigate(R.id.action_level4Fragment_to_level5Fragment)
                    } else {
                        displayWordPairs()
                    }
                }
            } else {
                // Wrong match
                viewModel.loseLife()
                selectedLeftWord?.setBackgroundResource(R.drawable.button_background)
                selectedRightWord?.setBackgroundResource(R.drawable.button_background)
                selectedLeftWord = null
                selectedRightWord = null

                if (viewModel.lives.value!! <= 0) {
                    // Game over
                    findNavController().navigate(R.id.action_level4Fragment_to_gameOverFragment)
                }
            }
        }
    }

    private fun resetButtonColors() {
        val buttons = listOf(leftWord1, leftWord2, leftWord3, leftWord4, rightWord1, rightWord2, rightWord3, rightWord4)
        buttons.forEach { it.setBackgroundResource(R.drawable.button_background) }
    }

    private fun setButtonsVisibility(visible: Boolean) {
        val buttons = listOf(leftWord1, leftWord2, leftWord3, leftWord4, rightWord1, rightWord2, rightWord3, rightWord4)
        buttons.forEach { it.visibility = if (visible) View.VISIBLE else View.GONE }
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

data class WordPair(
    val leftWord: String,
    val rightWord: String
) {
    fun matches(left: String, right: String): Boolean {
        return (left == leftWord && right == rightWord) || (left == rightWord && right == leftWord)
    }
} 