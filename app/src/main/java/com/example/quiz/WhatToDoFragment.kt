package com.example.quiz

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class WhatToDoFragment : Fragment(R.layout.fragment_what_to_do) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val whatToDoText: TextView = view.findViewById(R.id.whatToDoText)

        val spannable = SpannableString(
            "Τι κάνω αν:\n\n" +
            "Σε περίπτωση που κάποιο παιδί υποστεί οποιαδήποτε μορφή βίας Πρέπει και Μπορεί να:\n\n" +

            "ΣΤΟ ΣΧΟΛΕΙΟ:\n" +
            "• Κάθε σχολείο έχει υπεύθυνους καταγραφής & αντιμετώπισης περιστατικών βίας.\n" +
            "• Μπορείς να απευθυνθείς σε αυτούς αν είσαι θύμα βίας.\n\n" +

            "ΣΤΟ ΣΠΙΤΙ:\n" +
            "• Να καλέσει το 100 ή να στείλει γραπτό μήνυμα με τα στοιχεία, και είδος βίας.\n" +
            "• Μπορεί επίσης να καλέσει τη Γραμμή SOS 15900.\n" +
            "• Τέλος, η Εθνική Τηλεφωνική Γραμμή για τα Παιδιά SOS 1056 λειτουργεί από «Το Χαμόγελο του Παιδιού» για την παροχή βοήθειας & υποστήριξης\n\n" +

            "ΦΙΛΟΙ:\n" +
            "• Αν κάποιος που θεωρείς φίλο σου σε εκφοβίζει ή σε κακοποιεί Μπορείς & Πρέπει να το συζητήσεις με την οικογένειά σου ή/και κάποιο δάσκαλό σου"
        )

        // Apply bold style to titles and important sections
        val titlesToBold = listOf(
            "Τι κάνω αν:",
            "ΣΤΟ ΣΧΟΛΕΙΟ:",
            "ΣΤΟ ΣΠΙΤΙ:",
            "ΦΙΛΟΙ:"
        )

        // Apply bold style to the titles
        for (title in titlesToBold) {
            val startIndex = spannable.indexOf(title)
            if (startIndex != -1) {
                val endIndex = startIndex + title.length
                spannable.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        whatToDoText.text = spannable
    }
} 