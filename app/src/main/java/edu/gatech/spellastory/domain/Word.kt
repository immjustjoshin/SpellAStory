package edu.gatech.spellastory.domain

import java.io.Serializable

class Word(val spelling: String, val phonemes: List<Phoneme>, val category: Category?) : Serializable {
    fun contains(code: String): Boolean = code in phonemes.map { it.code }
    fun validate() {
        val phonemeSpelling = phonemes.joinToString("") { it.spelling }
        if (!spelling.equals(phonemeSpelling, true))
            throw Exception("$spelling doesn't match its phonemes $phonemeSpelling")
    }
}