package edu.gatech.spellastory.data

import android.app.Activity
import android.content.Context
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.SingletonHolder
import java.util.*

val Activity.LevelsDb
    get() = Levels.getInstance(this)

private val LV5 = Arrays.asList(
        "14", "15", "18", "19", "20", "21", "23", "37", "38", "41", "42", "62", "63", "64", "80", "81", "82", "86",
        "87", "88", "89", "90", "91", "92", "93", "94", "95", "97", "98", "99", "100", "108"
)
private val LV6 = Arrays.asList(
        "5", "6", "10", "11", "12", "27", "28", "29", "30", "34", "35", "47", "48", "68", "69", "70", "71", "72", "75",
        "76", "77", "78", "104"
)
private val LV7 = Arrays.asList(
        "7", "8", "9", "31", "32", "33", "49", "51", "52", "53", "54", "59", "73", "74", "82", "105"
)
private val LV8 = (1..113).map { it.toString() }.toMutableList().apply { add("45a") }.sorted()

class Levels private constructor(private val context: Context) {

    fun getLevelPhonemeCodes(levelNum: Int): List<String> {
        return when (levelNum) {
            5 -> LV5
            6 -> LV6
            7 -> LV7
            8 -> LV8
            else -> throw IllegalArgumentException("$levelNum is not a valid level")
        }
    }

    fun getWordsForLevel(level: Int): List<PhonemeWordsPair> {
        return getLevelPhonemeCodes(level).mapNotNull { code ->
            with(getWordsForPhonemeCode(code)) {
                if (!this.isEmpty()) {
                    val phoneme = Phoneme(code, Phonemes.getInstance(context).getPhonemeSpellings(code)[0])
                    PhonemeWordsPair(phoneme, this)
                } else null
            }
        }
    }

    private fun getWordsForPhonemeCode(code: String): List<Word> {
        return Words.getInstance(context).words.filter { it.contains(code) }
    }

    companion object : SingletonHolder<Levels, Context>(::Levels)
}