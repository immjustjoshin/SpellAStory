package edu.gatech.spellastory.data

import android.app.Activity
import android.content.Context
import edu.gatech.spellastory.domain.Category
import edu.gatech.spellastory.domain.stories.Story
import edu.gatech.spellastory.domain.stories.StoryBlank
import edu.gatech.spellastory.domain.stories.StoryLine
import edu.gatech.spellastory.domain.stories.StoryToken
import edu.gatech.spellastory.util.SingletonHolder
import org.apache.commons.lang3.NotImplementedException
import java.io.BufferedReader
import java.io.InputStreamReader

val Activity.StoriesDb
    get() = Stories.getInstance(this)

class Stories private constructor(context: Context) {

    private val stories: Map<String, Story> by lazy {
        val reader = BufferedReader(InputStreamReader(context.assets.open("coded_phonemes.csv")))
        val phonemes = read(context, "the_special_invention")
        reader.close()
        phonemes
    }

    fun getStory(story: String) = stories.getValue(story)

    private fun read(context: Context, vararg stories: String) = stories.map { story ->
        val reader = BufferedReader(InputStreamReader(context.assets.open("stories/$story.txt")))
        val text = reader.lineSequence().joinToString(" \n")
        story to Story(parseText(text, Categories.getInstance(context)))
    }.toMap()

    private fun parseText(storyText: String, categoriesDb: Categories): List<StoryToken> {
        val storyTokens = mutableListOf<StoryToken>()
        val tokens = storyText.split(" ")
        val blankCategories = mutableMapOf<String, List<Category>>()

        var i = 0
        while (i < tokens.size) {
            val token = tokens[i]
            var valid = false

            isAudio(token)?.also { audioFile ->
                i++

                val text = mutableListOf<String>()
                while (i < tokens.size && isText(tokens[i]))
                    text.add(tokens[i++])

                storyTokens.add(StoryLine(audioFile, text.joinToString(" ")))
                valid = true
            }

            isBlank(token)?.also { _token ->
                val blankTokens = _token.split("-")

                val identifier = blankTokens[0]

                val categories: List<Category>
                if (blankTokens.size > 1) {
                    val categoryStrings = blankTokens[1].split(",")
                    categories = categoriesDb.getCategories(*categoryStrings.toTypedArray())
                    blankCategories[identifier] = categories
                } else {
                    categories = blankCategories.getValue(identifier)
                }

                storyTokens.add(StoryBlank(identifier, categories))
                i++
                valid = true
            }

            if (isNewline(token)) {
                i++
                valid = true
            }

            if (!valid) throw NotImplementedException("Invalid token: $token")
        }

        return storyTokens
    }

    private fun isAudio(token: String): String? {
        val tempToken = cleanToken(token)
        if (tempToken.startsWith("{") && tempToken.endsWith("}")) return tempToken.drop(1).dropLast(1)
        return null
    }

    private fun isBlank(token: String): String? {
        val tempToken = cleanToken(token)
        if (tempToken.startsWith("[") && tempToken.endsWith("]")) return tempToken.drop(1).dropLast(1)
        return null
    }

    private fun cleanToken(string: String): String {
        var ret = string.trim()
        if (ret.isNotEmpty() && ret.last() in PUNCTUATION) ret = ret.dropLast(1)
        return ret
    }

    private fun isNewline(string: String): Boolean = string == "\n"

    private fun isText(string: String): Boolean =
            isAudio(string) == null && isBlank(string) == null

    companion object : SingletonHolder<Stories, Context>(::Stories) {
        private const val PUNCTUATION = ".,:;"
    }
}