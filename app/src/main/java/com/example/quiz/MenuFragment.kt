package com.example.quiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MenuFragment : Fragment(R.layout.fragment_menu) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val quizButton: Button = view.findViewById(R.id.quizButton)
        val rulesButton: Button = view.findViewById(R.id.rulesButton)

        quizButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_quizFragment)
        }

        rulesButton.setOnClickListener {
            findNavController().navigate(R.id.action_menuFragment_to_rulesFragment)
        }
    }
}
