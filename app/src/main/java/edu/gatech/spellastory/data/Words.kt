package edu.gatech.spellastory.data

import android.app.Activity
import android.content.Context
import com.opencsv.CSVIterator
import com.opencsv.CSVReader
import edu.gatech.spellastory.domain.Category
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.SingletonHolder
import java.io.InputStreamReader
import java.util.*

val Activity.WordsDb
    get() = Words.getInstance(this)

class Words private constructor(private val context: Context) {

    val words: List<Word> by lazy {
        val reader = CSVReader(InputStreamReader(context.assets.open("coded_words.csv")))
        val words = read(reader, context)
        reader.close()
        words
    }

    private fun read(reader: CSVReader, context: Context) = CSVIterator(reader).asSequence().map { line ->
        val spelling = line[0]
        val category =
                if (line[1].isEmpty()) null else Categories.getInstance(context).getCategory(Integer.parseInt(line[1]))
        val phonemeCodes = Arrays.copyOfRange(line, 2, line.size)
        val phonemes = Phonemes.getInstance(context).getPhonemes(phonemeCodes)

        val word = makeWord(spelling, phonemes, category)
        word.validate()

        // Friends are unlocked by default
        if (word.category?.code == 2) {
            val prefs = context.applicationContext.getSharedPreferences("completedWords", Context.MODE_PRIVATE)
            val editor = prefs.edit()

            editor.putBoolean(word.spelling, true)
            editor.apply()
        }
        word
    }.toList()

    private fun makeWord(spelling: String, possiblePhonemes: List<Phoneme>, category: Category?): Word {
        var spellingCopy = spelling
        val phonemes = mutableListOf<Phoneme>()
        possiblePhonemes.forEach {
            if (it.isSilent) {
                val letter = spellingCopy[0].toString()
                phonemes.add(Phoneme(it.code, letter))
                spellingCopy = spellingCopy.drop(1)
            } else if (spellingCopy.startsWith(it.spelling, true)) {
                phonemes.add(it)
                spellingCopy = spellingCopy.drop(it.spelling.length)
            }
        }
        return Word(spelling, phonemes, category)
    }

    fun getWord(spelling: String) = words.find { it.spelling == spelling }

    val completedWords: List<Word>
        get() {
            val prefs = context.applicationContext.getSharedPreferences("completedWords", Context.MODE_PRIVATE)
            return words.filter { it.spelling in prefs.all.keys }
        }

    val incompleteWords: List<Word>
        get() {
            val prefs = context.applicationContext.getSharedPreferences("completedWords", Context.MODE_PRIVATE)
            return words.filter { it.spelling !in prefs.all.keys }
        }

    fun getCompletedWordsForCategory(category: Category) = completedWords.filter { it.category == category }

    fun getIncompleteWordsForCategory(category: Category) = incompleteWords.filter { it.category == category }

    fun getCompletedWordsForCategories(categories: List<Category>): List<Word> =
            completedWords.filter { it.category in categories }

    fun getIncompleteWordsForCategories(categories: List<Category>): List<Word> =
            incompleteWords.filter { it.category in categories }

    companion object : SingletonHolder<Words, Context>(::Words)
}