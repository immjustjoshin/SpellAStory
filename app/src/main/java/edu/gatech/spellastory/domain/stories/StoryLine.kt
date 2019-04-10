package edu.gatech.spellastory.domain.stories

class StoryLine(val audioFile: String, val text: String) : StoryToken {
    override val isBlank = false
}