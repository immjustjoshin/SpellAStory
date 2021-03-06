package edu.gatech.spellastory.game

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.TextView
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.PhonemesDb
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.story.GameFinishIntent
import edu.gatech.spellastory.util.AudioPlayer
import edu.gatech.spellastory.util.getWordDrawable
import kotlinx.android.synthetic.main.activity_game.*
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.math.truncate

fun Context.GameIntent(word: Word, level: Int) = Intent(this, GameActivity::class.java)
        .apply {
            putExtra(INTENT_WORD, word)
            putExtra(INTENT_LVL, level)
        }

private const val INTENT_WORD = "word"
private const val INTENT_LVL = "level"

class GameActivity : AppCompatActivity() {

    private lateinit var word: Word
    private var level = -1
    private lateinit var spellingSequence: List<Phoneme>
    private var phonemeOptionsCount = 4
    private var spelledCount = 0
    private val currentPhoneme: Phoneme
        get() = spellingSequence[spelledCount]
    private var correctSpelledCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        level = intent.getSerializableExtra(INTENT_LVL) as Int
        require(level != -1) { "no level provided in Intent extras" }
        if (level == 8) phonemeOptionsCount = 15

        word = intent.getSerializableExtra(INTENT_WORD) as Word
        requireNotNull(word) { "no word provided in Intent extras" }

        spellingSequence = makeSpellingSequence()

        setSpellingText()
        btn_image.setImageDrawable(getWordDrawable(word))
        btn_image.setOnClickListener { AudioPlayer.fromWord(word, this).play() }

        setPhonemeButtons()
    }

    private fun setSpellingText() = txt_spelling.setText(Html.fromHtml(makeUnderlines()), TextView.BufferType.SPANNABLE)

    private fun makeUnderlines(): String {
        var underlines = ""

        val spelled = spellingSequence.subList(0, spelledCount).toMutableList()

        word.phonemes.forEach { phoneme ->
            if (spelled.isNotEmpty() && spelled[0] == phoneme) {
                spelled.removeAt(0)
                phoneme.spelling.forEach { underlines += "<u>$it</u> " }
            } else {
                phoneme.spelling.forEach {
                    underlines += if (phoneme.isSilent) "<font color='green'><u>$it</u></font> " else "<u>_</u> "
                }
            }
        }
        return underlines
    }

    private fun makeSpellingSequence(): List<Phoneme> = word.phonemes.mapNotNull { if (!it.isSilent) it else null }

    private fun setPhonemeButtons() {
        grid.removeAllViews()
        val (col, row) = square(phonemeOptionsCount)
        grid.columnCount = col
        grid.rowCount = row

        makePhonemeOptions().forEach { phoneme ->
            val button = PhonemeButton(this, phoneme)
            button.setOnClickListener {
                if ((it as PhonemeButton).phoneme == currentPhoneme) {
                    AudioPlayer.positiveSound(this).play()
                    spelledCount++
                    setSpellingText()
                    if (level == 8) it.isEnabled = false
                    else if (spelledCount < spellingSequence.size) setPhonemeButtons()

                    if (spelledCount == spellingSequence.size) handleCorrectSpelling()
                } else AudioPlayer.negativeSound(this).play()
            }

            button.setOnLongClickListener {
                button.playAudio()
                true
            }

            grid.addView(button)
        }
    }

    private fun makePhonemeOptions(): List<Phoneme> {
        val options = mutableListOf<Phoneme>()
        if (level == 8) options.addAll(spellingSequence) else options.add(currentPhoneme)

        while (options.size < phonemeOptionsCount) {
            val randomPhoneme = PhonemesDb.randomPhoneme
            if (randomPhoneme.isSilent) continue
            if (randomPhoneme.spelling in options.map { it.spelling }) continue
            if (options.any { it.spelling in randomPhoneme.spelling }) continue // don't let "b" and "r" show up as separate when the phoneme is "br"
            options.add(randomPhoneme)
        }

        return options.shuffled()
    }

    private fun handleCorrectSpelling() {
        correctSpelledCount++
        if (level == 8 && correctSpelledCount < 3) {
            AudioPlayer.praiseWord(this).play { resetSpelling() }
        } else onComplete()
    }

    private fun onComplete() {
        AudioPlayer.praiseWord(this).play()
        markWordAsComplete()
        PopupFragment.newInstance { onFinishClicked() }.show(supportFragmentManager, "win")
    }

    private fun markWordAsComplete() {
        val prefs = applicationContext.getSharedPreferences("completedWords", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putBoolean(word.spelling, true)
        editor.apply()
    }

    private fun onFinishClicked() {
        setResult(Activity.RESULT_OK, GameFinishIntent(word))
        finish()
    }

    private fun resetSpelling() {
        spelledCount = 0
        setPhonemeButtons()
        setSpellingText()
    }

    data class Square(val h: Int, val v: Int)

    private fun square(k: Int): Square {
        val v = ceil(sqrt(k.toDouble())).toInt()
        val h = truncate(k.toDouble() / v).toInt()
        return Square(h, v)
    }
}
