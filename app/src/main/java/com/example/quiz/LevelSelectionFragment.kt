package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class LevelSelectionFragment : Fragment(R.layout.fragment_level_selection) {

    private lateinit var level1Button: Button
    private lateinit var level2Button: Button
    private lateinit var level3Button: Button
    private lateinit var level4Button: Button
    private lateinit var level5Button: Button

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        level1Button = view.findViewById(R.id.level1Button)
        level2Button = view.findViewById(R.id.level2Button)
        level3Button = view.findViewById(R.id.level3Button)
        level4Button = view.findViewById(R.id.level4Button)
        level5Button = view.findViewById(R.id.level5Button)

        // Set up button click listeners
        level1Button.setOnClickListener { navigateToLevel(1) }
        level2Button.setOnClickListener { navigateToLevel(2) }
        level3Button.setOnClickListener { navigateToLevel(3) }
        level4Button.setOnClickListener { navigateToLevel(4) }
        level5Button.setOnClickListener { navigateToLevel(5) }

        // Update button states based on completed levels
        updateButtonStates()
    }

    private fun navigateToLevel(level: Int) {
        when (level) {
            1 -> findNavController().navigate(R.id.action_levelSelectionFragment_to_level1Fragment)
            2 -> findNavController().navigate(R.id.action_levelSelectionFragment_to_level2Fragment)
            3 -> findNavController().navigate(R.id.action_levelSelectionFragment_to_level3Fragment)
            4 -> findNavController().navigate(R.id.action_levelSelectionFragment_to_level4Fragment)
            5 -> findNavController().navigate(R.id.action_levelSelectionFragment_to_level5Fragment)
        }
    }

    private fun updateButtonStates() {
        val completedLevels = viewModel.getCompletedLevels()
        
        // All buttons are enabled by default
        level1Button.isEnabled = true
        level2Button.isEnabled = true
        level3Button.isEnabled = true
        level4Button.isEnabled = true
        level5Button.isEnabled = true

        // Update button appearance based on completion status
        updateButtonAppearance(level1Button, !completedLevels.contains(1))
        updateButtonAppearance(level2Button, !completedLevels.contains(2))
        updateButtonAppearance(level3Button, !completedLevels.contains(3))
        updateButtonAppearance(level4Button, !completedLevels.contains(4))
        updateButtonAppearance(level5Button, !completedLevels.contains(5))
    }

    private fun updateButtonAppearance(button: Button, isActive: Boolean) {
        if (isActive) {
            button.setBackgroundResource(R.drawable.button_background)
            button.alpha = 1.0f
        } else {
            button.setBackgroundResource(R.drawable.disabled_button_background)
            button.alpha = 0.5f
        }
    }
} 