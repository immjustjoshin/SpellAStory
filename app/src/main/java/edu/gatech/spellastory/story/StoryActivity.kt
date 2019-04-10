package edu.gatech.spellastory.story

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ScrollView
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.Stories
import edu.gatech.spellastory.domain.Category
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.domain.stories.Story
import edu.gatech.spellastory.domain.stories.StoryBlank
import edu.gatech.spellastory.domain.stories.StoryLine
import edu.gatech.spellastory.game.GameIntent
import edu.gatech.spellastory.util.getStoryDrawable
import kotlinx.android.synthetic.main.activity_story.*
import java.io.IOException

fun Context.StoryIntent(story: String): Intent = Intent(this, StoryActivity::class.java)
    .apply { putExtra(INTENT_STORY, story) }

private const val INTENT_STORY = "story"

fun Context.GameFinishIntent(word: Word) = Intent().apply { putExtra(INTENT_WORD, word) }

private const val INTENT_WORD = "word"

class StoryActivity : AppCompatActivity() {

    private lateinit var title: String
    private lateinit var story: Story
    private var storyPosition: Int = -1
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        title = intent.getStringExtra(INTENT_STORY)
        requireNotNull(title) { "no story provided in Intent extras" }
        this.story = Stories.getInstance(this).getStory(convertTitle(title))

        img_background.setImageDrawable(getStoryDrawable(title))
        txt_story.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                scroll.fullScroll(ScrollView.FOCUS_DOWN)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        showNextLine()
    }

    private fun convertTitle(title: String): String {
        return title.replace(" ", "_").toLowerCase()
    }

    private fun showNextLine() {
        storyPosition++
        val next = story.tokens[storyPosition]
        if (next.isBlank) {
            val blank = next as StoryBlank
            story.getBlank(blank)?.let {
                chooseWord(it)
            } ?: run {
                txt_story.animateAppend(UNDERLINE) { showStoryBlankDialog(blank.categories) }
            }
        } else {
            val line = next as StoryLine

            val text = line.text
            val mp = makeStoryAudio(line.audioFile)
            setDelay(text, mp)
            mp?.start()
            txt_story.animateAppend(text) { showNextLine() }
        }
    }

    private fun showStoryBlankDialog(categories: List<Category>) {
        println(categories)
        dialog = StoryBlankDialog(
            categories,
            { word -> chooseWord(word) },
            { word -> startActivityForResult(GameIntent(word), GAME_RESULT_REQUEST) }) {
            cancelable = false
            isBackGroundTransparent = false
        }
        dialog?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GAME_RESULT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val word = data?.getSerializableExtra(INTENT_WORD) as Word
                requireNotNull(story) { "no word provided in Intent extras" }
                println("Word ${word.spelling} completed!")
                chooseWord(word)
            } else {
                println("Word not completed...")
            }
        }
    }

    private fun chooseWord(word: Word) {
        val blank = story.tokens[storyPosition] as StoryBlank
        story.setBlank(blank, word)

        dialog?.hide()
        dialog = null

        val text = word.spelling
        val mp = if (word.category?.name == "friends") {
            makeNameAudio(word.spelling)
        } else {
            makeWordAudio(word.spelling)
        }
        setDelay(text, mp)
        mp?.start()

        if (txt_story.text.endsWith(UNDERLINE)) txt_story.remove(UNDERLINE.length)
        txt_story.animateAppend(text) { showNextLine() }
    }

    private fun setDelay(text: String, mp: MediaPlayer?) {
        if (mp != null) {
            txt_story.delay = (mp.duration / text.length * 0.7).toLong()
        } else {
            txt_story.delay = 10
        }
    }

    private fun makeStoryAudio(storyAudio: String): MediaPlayer? {
        val mp = MediaPlayer()
        return try {
            val afd = assets.openFd("audio/stories/" + convertTitle(title) + "/" + storyAudio + ".mp3")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.prepare()
            mp
        } catch (e: IOException) {
            // Could not find audio file associate with the word
            e.printStackTrace()
            null
        }
    }

    private fun makeNameAudio(name: String): MediaPlayer? {
        val mp = MediaPlayer()
        return try {
            val afd = assets.openFd("audio/names/$name.mp3")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.prepare()
            mp
        } catch (e: IOException) {
            // Could not find audio file associate with the word
            e.printStackTrace()
            null
        }
    }

    private fun makeWordAudio(word: String): MediaPlayer? {
        val mp = MediaPlayer()
        return try {
            val afd = assets.openFd("audio/words/$word.mp3")
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.prepare()
            mp
        } catch (e: IOException) {
            // Could not find audio file associate with the word
            e.printStackTrace()
            null
        }
    }

    companion object {
        private const val UNDERLINE = "______"
        private const val GAME_RESULT_REQUEST = 1
    }
}
