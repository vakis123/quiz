package com.example.quiz

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.widget.Button
import android.widget.TextView
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class RulesFragment : Fragment(R.layout.fragment_rules) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*
        The original approach uses hardcoded index numbers for applying styles to the text.
        This is not robust, as changes to the text content would break the styling.
        The following improved version uses a more dynamic method to find the titles and
        important words within the text and apply the bold style, making it independent of the exact position.
         */

        super.onViewCreated(view, savedInstanceState)

        val rulesText: TextView = view.findViewById(R.id.rulesText)
        val rulesText2: TextView = view.findViewById(R.id.rulesText2)
        val whatToDoButton: Button = view.findViewById(R.id.whatToDoButton)

        val firstPart = SpannableString(
            "\n📜 Εισαγωγή\n" +
                    "Η εφαρμογή «ἔφηΒία» είναι ένα παιχνίδι για κινητά τηλέφωνα που υποστηρίζεται από σύστημα Android. Το επίκεντρο του παιχνιδιού είναι η βία και ο στόχος του είναι διττός:\n" +
                    "🎮 Ως παιχνίδι έχει στόχο να ψυχαγωγήσει.\n" +
                    "📢 Ως μέσο προσέγγισης των παιδιών, να ενημερώσει αξιόπιστα για θέματα σχετικά με τη βία.\n\n" +

                    "Ζώντας σε ένα περιβάλλον όπου τα περιστατικά βίας αυξάνονται, διαφαίνεται επιτακτική η ανάγκη έγκυρης και έγκαιρης εκπαίδευσης του νεανικού πληθυσμού σχετικά με το τι είναι η βία και ποιες επιπτώσεις μπορεί να έχει.\n"

)

        val secondPart = SpannableString(
            "🕹️ Περιγραφή\n" +
                    "Το παιχνίδι περιλαμβάνει πέντε επίπεδα δράσης τα οποία απαιτούν, διαφορετικού τύπου επίλυσης το καθένα, και διαφοροποιούνται ως προς τον βαθμό δυσκολίας τους.\n" +
                    "Υπάρχουν ερωτήσεις στις οποίες ο παίκτης πρέπει να επιλέξει Σωστό ή Λάθος, άλλες Πολλαπλών Επιλογών, ερωτήσεις Αντιστοίχισης, και τέλος συμπλήρωση κενών για να αποκαλυφθεί μία λέξη.\n" +
                    "Όλα τα επίπεδα επικεντρώνονται σε ερωτήσεις που έχουν σχέση με τη Βία ακόμη κι αν δεν περιγράφουν το συγκεκριμένο φαινόμενο. Μπορεί να αναφέρονται σε περιστατικά, στόχους, συνέπειες, μορφές βίας κλπ.\n\n" +

                    "📌 Δομή των επιπέδων:\n" +
                    "✅ Επίπεδα 1 & 3: 10 ερωτήσεις τύπου Σωστό ή Λάθος.\n" +
                    "✅ Επίπεδο 2: 10 ερωτήσεις πολλαπλών επιλογών, με μία σωστή απάντηση από τις τέσσερις διαθέσιμες.\n" +
                    "✅ Επίπεδο 4: 10 ομάδες από ζεύγη λέξεων που πρέπει να αντιστοιχηθούν μεταξύ τους. Κάθε ομάδα έχει τέσσερα ζεύγη.\n" +
                    "✅ Επίπεδο 5: Συμπλήρωση των κενών 15 λέξεων που εμφανίστηκαν στα προηγούμενα επίπεδα. Παρέχεται ένα δοσμένο γράμμα για κάθε λέξη.\n\n" +

                    "🎯 Πρόοδος στο Παιχνίδι\n" +
                    "Όταν απαντηθούν σωστά όλες οι ερωτήσεις ενός επιπέδου, ο παίχτης μπορεί να προχωρήσει στο επόμενο.\n" +
                    "Έχει τη δυνατότητα να κάνει δύο λάθη σε όλο το παιχνίδι, χρησιμοποιώντας τις τρεις ζωές που του παρέχονται (💖💖💖).\n" +
                    "❌ Στο τρίτο λάθος, το παιχνίδι τελειώνει και θα πρέπει να ξεκινήσει από την αρχή.\n\n" +

                    "Καλή επιτυχία! 🎮✨\n"
        )

        val titlesAndWordsToBold = listOf(
            "📜 Εισαγωγή",
            "🕹️ Περιγραφή",
            "🎯 Πρόοδος στο Παιχνίδι"
        )

        //Dynamically apply styles to the given words
        for (titleOrWord in titlesAndWordsToBold) {
            val startIndex = firstPart.indexOf(titleOrWord)
            if (startIndex != -1) {
                val endIndex = startIndex + titleOrWord.length
                firstPart.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            
            val startIndex2 = secondPart.indexOf(titleOrWord)
            if (startIndex2 != -1) {
                val endIndex2 = startIndex2 + titleOrWord.length
                secondPart.setSpan(
                    StyleSpan(Typeface.BOLD),
                    startIndex2,
                    endIndex2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }

        // Set the text
        rulesText.text = firstPart
        rulesText2.text = secondPart

        // Handle button click
        whatToDoButton.setOnClickListener {
            findNavController().navigate(R.id.action_rulesFragment_to_whatToDoFragment)
        }
    }
}
