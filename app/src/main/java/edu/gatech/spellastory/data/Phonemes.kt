package edu.gatech.spellastory.data

import android.app.Activity
import android.content.Context
import com.opencsv.CSVIterator
import com.opencsv.CSVReader
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.util.SingletonHolder
import java.io.InputStreamReader
import java.util.*

val Activity.PhonemesDb
    get() = Phonemes.getInstance(this)

class Phonemes private constructor(context: Context) {

    private val phonemes: Map<String, List<String>> by lazy {
        val reader = CSVReader(InputStreamReader(context.assets.open("coded_phonemes.csv")))
        val phonemes = read(reader)
        reader.close()
        phonemes
    }

    private fun read(reader: CSVReader) = CSVIterator(reader).asSequence().map { line ->
        val code = line[0]
        val letters = Arrays.copyOfRange<String>(line, 1, line.size)
        code to letters.toList()
    }.toMap()

    fun getPhonemeSpellings(code: String): List<String> {
        if (code == "0") return listOf("")
        return phonemes.getValue(code)
    }

    fun getPhonemes(codes: Array<out String>): List<Phoneme> {
        val phonemes = mutableListOf<Phoneme>()
        codes.forEach { code ->
            getPhonemeSpellings(code).sortedByDescending { it.length }.forEach { spelling ->
                phonemes.add(Phoneme(code, spelling))
            }
        }
        return phonemes
    }

    // fun getAllPhonemes(): List<Phoneme> {
    //     val phonemes = mutableListOf<Phoneme>()
    //     this.phonemes.entries.forEach {
    //         val code = it.key
    //         val spellings = it.value
    //         spellings.forEach { spelling ->
    //             phonemes.add(Phoneme(code, spelling))
    //         }
    //     }
    //     return phonemes
    // }

    val randomPhoneme: Phoneme
        get() {
            val code = phonemes.keys.random()
            val spelling = phonemes.getValue(code).random()
            return Phoneme(code, spelling)
        }

    companion object : SingletonHolder<Phonemes, Context>(::Phonemes)
}