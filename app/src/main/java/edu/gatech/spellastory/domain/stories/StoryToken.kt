package edu.gatech.spellastory.domain.stories

import java.io.Serializable

interface StoryToken : Serializable {
    val isBlank: Boolean
}
