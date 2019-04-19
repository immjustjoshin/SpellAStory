package edu.gatech.spellastory.domain.stories

import edu.gatech.spellastory.domain.Category
import kotlin.String

class StoryBlank(val identifier: String, val categories: List<Category>) : StoryToken {
    override val isBlank = true
}