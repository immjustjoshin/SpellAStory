package edu.gatech.spellastory.domain.stories

import edu.gatech.spellastory.domain.Word

class Story(val tokens: List<StoryToken>) {

    private val blanks = mutableMapOf<String, Word>()

    fun setBlank(blank: StoryBlank, word: Word) {
        blanks[blank.identifier] = word
    }

    fun getBlank(blank: StoryBlank): Word? = blanks[blank.identifier]
}