package edu.gatech.spellastory.domain.stories

import edu.gatech.spellastory.domain.Category

class StoryBlank(val identifier: String, val categories: List<Category>) : StoryToken {
    override val isBlank = true
}