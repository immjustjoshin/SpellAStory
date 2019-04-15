package edu.gatech.spellastory.domain.stories

import edu.gatech.spellastory.domain.Word
import java.io.Serializable

class Story(val filename: String, val tokens: List<StoryToken>) : Serializable {

    private val blanks = mutableMapOf<String, Word>()
    val title = filename.split('_').joinToString(" ") { it.capitalize() }

    fun setBlank(blank: StoryBlank, word: Word) {
        blanks[blank.identifier] = word
    }

    fun getBlank(blank: StoryBlank): Word? = blanks[blank.identifier]
}