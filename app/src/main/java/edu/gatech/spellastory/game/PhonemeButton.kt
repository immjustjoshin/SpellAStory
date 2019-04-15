package edu.gatech.spellastory.game

import android.content.Context
import android.widget.Button
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.util.AudioPlayer

class PhonemeButton(context: Context, val phoneme: Phoneme) : Button(context) {

    private val audioPlayer: AudioPlayer

    init {
        this.text = phoneme.spelling
        this.audioPlayer = AudioPlayer.fromPhoneme(phoneme, context)
    }

    fun playAudio() = this.audioPlayer.play()
}