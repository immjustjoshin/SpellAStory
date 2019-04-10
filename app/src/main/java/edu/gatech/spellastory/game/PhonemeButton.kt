package edu.gatech.spellastory.game

import android.content.Context
import android.widget.Button
import edu.gatech.spellastory.domain.Phoneme

class PhonemeButton(context: Context, val phoneme: Phoneme) : Button(context) {

    init {
        this.text = phoneme.spelling
    }
}