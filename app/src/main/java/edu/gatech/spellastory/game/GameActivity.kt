package edu.gatech.spellastory.game

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.widget.TextView
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.PhonemesDb
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.story.GameFinishIntent
import edu.gatech.spellastory.util.getWordDrawable
import kotlinx.android.synthetic.main.activity_game.*
import java.io.FileNotFoundException
import kotlin.math.ceil
import kotlin.math.sqrt
import kotlin.math.truncate

fun Context.GameIntent(word: Word, level: Int) = Intent(this, GameActivity::class.java)
        .apply { putExtra(INTENT_WORD, word)
                 putExtra(INTENT_LVL, level)}

private const val INTENT_WORD = "word"
private const val INTENT_LVL = "level"

class GameActivity : AppCompatActivity(), GameEndDialogFragment.Listener {

    private lateinit var word: Word
    private var level = -1
    private lateinit var spellingSequence: List<Phoneme>
    private var phonemeOptionsCount = 4
    private var spelledCount = 0
    private val currentPhoneme: Phoneme
        get() = spellingSequence[spelledCount]


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

        val audio = makeWordAudio()
        btn_image.setOnClickListener { audio.start() }

        setPhonemeButtons(makePhonemeOptions())
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

    private fun makeWordAudio(): MediaPlayer {
        val mp = MediaPlayer()
        try {
            val afd =
                    assets.openFd("audio/${if (word.category?.name == "friends") "names" else "words"}/${word.spelling}.mp3")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.prepare()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return mp
    }

    private fun makeSpellingSequence(): List<Phoneme> = word.phonemes.mapNotNull { if (!it.isSilent) it else null }

    private fun makePhonemeOptions(): List<Phoneme> {
        val options = mutableListOf<Phoneme>()
        if (level == 8) {
            options.addAll(spellingSequence)
        } else {
            options.add(currentPhoneme)
        }

        while (options.size < phonemeOptionsCount) {
            val randomPhoneme = PhonemesDb.randomPhoneme
            if (randomPhoneme.isSilent) continue
            if (randomPhoneme.spelling in options.map { it.spelling }) continue
            options.add(randomPhoneme)
        }

        return options.shuffled()
    }

    private fun setPhonemeButtons(phonemes: List<Phoneme>) {
        grid.removeAllViews()

        val (col, row) = square(phonemeOptionsCount)
        grid.columnCount = col
        grid.rowCount = row

        phonemes.forEach { phoneme ->
            val button = PhonemeButton(this, phoneme)
            button.setOnClickListener {
                if ((it as PhonemeButton).phoneme == currentPhoneme) {
                    spelledCount++
                    setSpellingText()

                    if (spelledCount == spellingSequence.size) {
                        markWordAsComplete()
                        GameEndDialogFragment.newInstance().show(supportFragmentManager, "win")
                    } else {
                        playPositiveSound()
                        if (level == 8) {it.setEnabled(false)}
                        else {setPhonemeButtons(makePhonemeOptions())}
                    }
                } else {
                    playNegativeSound()
                }
            }

            button.setOnLongClickListener {
                makePhonemeAudio(phoneme).start()
                true
            }

            grid.addView(button)
        }
    }

    private fun makePhonemeAudio(phoneme: Phoneme): MediaPlayer {
        val mp = MediaPlayer()
        val afd = assets.openFd("audio/phonemes/${phoneme.spelling}(${phoneme.code}).mp3")
        mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        mp.prepare()
        return mp
    }

    data class Square(val h: Int, val v: Int)

    private fun square(k: Int): Square {
        val v = ceil(sqrt(k.toDouble())).toInt()
        val h = truncate(k.toDouble() / v).toInt()
        return Square(h, v)
    }

    private fun playPositiveSound() {
        val mp = MediaPlayer()
        val afd = assets.openFd("audio/Ding Sound Effect.mp3")
        mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        mp.prepare()
        mp.start()
    }

    private fun playNegativeSound() {
        val mp = MediaPlayer()
        val afd = assets.openFd("audio/Try Again.mp3")
        mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        mp.prepare()
        mp.start()
    }

    private fun markWordAsComplete() {
        playPraiseWord()

        val prefs = applicationContext.getSharedPreferences("completedWords", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putBoolean(word.spelling, true)
        editor.apply()
    }

    private fun playPraiseWord() {
        val mp = MediaPlayer()
        val randomFile = assets.list("audio/positive_praise_words").random()
        val afd = assets.openFd("audio/positive_praise_words/$randomFile")
        mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        afd.close()
        mp.prepare()
        mp.start()
    }

    override fun onFinishClicked() {
        setResult(Activity.RESULT_OK, GameFinishIntent(word))
        finish()
    }
}
