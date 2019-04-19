package edu.gatech.spellastory.domain.stories

import java.io.Serializable

class Story(val filename: String, val tokens: List<StoryToken>) : Serializable {
    val title = filename.split('_').joinToString(" ") { it.capitalize() }
}