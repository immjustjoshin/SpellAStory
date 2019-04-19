package edu.gatech.spellastory.domain

import java.io.Serializable

class Phoneme(val code: String, val spelling: String) : Serializable {
    val isSilent: Boolean = code == "0"
}