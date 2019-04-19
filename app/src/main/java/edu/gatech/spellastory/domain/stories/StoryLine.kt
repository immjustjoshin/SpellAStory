package edu.gatech.spellastory.domain.stories

import kotlin.String

class StoryLine(val audioFile: String, val text: String) : StoryToken {
    override val isBlank = false
}