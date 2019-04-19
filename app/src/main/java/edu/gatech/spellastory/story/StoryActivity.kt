package edu.gatech.spellastory.story

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.ScrollView
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.StoriesDb
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.domain.stories.Story
import edu.gatech.spellastory.domain.stories.StoryBlank
import edu.gatech.spellastory.domain.stories.StoryLine
import edu.gatech.spellastory.game.GameIntent
import edu.gatech.spellastory.util.AudioPlayer
import edu.gatech.spellastory.util.getStoryDrawable
import kotlinx.android.synthetic.main.activity_story.*

fun Context.StoryIntent(story_title: String): Intent = Intent(this, StoryActivity::class.java)
        .apply { putExtra(INTENT_TITLE, story_title) }

private const val INTENT_TITLE = "title"

fun Context.GameFinishIntent(word: Word) = Intent().apply { putExtra(INTENT_WORD, word) }

private const val INTENT_WORD = "word"

class StoryActivity : AppCompatActivity() {

    private lateinit var story: Story
    private val blanks = mutableMapOf<String, Word>()
    private var storyPosition: Int = -1
    private var dialog: AlertDialog? = null
    private var audioPlayer: AudioPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        val title = intent.getStringExtra(INTENT_TITLE)
        requireNotNull(title) { "no story title provided in Intent extras" }
        story = StoriesDb.getStory(title)!!

        img_background.setImageDrawable(getStoryDrawable(story))
        txt_story.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                scroll.fullScroll(ScrollView.FOCUS_DOWN)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        showNextLine()
    }

    private fun setBlank(blank: StoryBlank, word: Word) {
        blanks[blank.identifier] = word
    }

    private fun getBlank(blank: StoryBlank) = blanks[blank.identifier]

    private fun showNextLine() {
        storyPosition++
        if (storyPosition >= story.tokens.size) return
        val next = story.tokens[storyPosition]
        if (next.isBlank) {
            val blank = next as StoryBlank
            val word = getBlank(blank)
            if (word == null) {
                txt_story.animateAppend(UNDERLINE) {
                    dialog = StoryBlankDialog(
                            blank.categories,
                            { chooseWord(it) },
                            { startActivityForResult(GameIntent(it, 0), GAME_RESULT_REQUEST) }) {
                        cancelable = false
                        isBackGroundTransparent = false
                    }
                    dialog?.show()
                }
            } else {
                chooseWord(word)
            }
        } else {
            val line = next as StoryLine

            val text = line.text
            audioPlayer = AudioPlayer.fromStory(story, line.audioFile, this)
            setDelay(text, audioPlayer!!)
            playLine(audioPlayer!!, text)
        }
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
        setBlank(blank, word)

        dialog?.dismiss()
        dialog = null

        val text = word.spelling
        audioPlayer = AudioPlayer.fromWord(word, this)
        setDelay(text, audioPlayer!!)
        playLine(audioPlayer!!, text)
    }

    private fun playLine(audioPlayer: AudioPlayer, text: String) {
        var audioDone = false
        var textDone = false
        var nextLineTriggered = false

        audioPlayer.play {
            audioDone = true
            if (textDone && !nextLineTriggered) {
                nextLineTriggered = true
                showNextLine()
            }
        }

        if (txt_story.text.endsWith(UNDERLINE)) txt_story.remove(UNDERLINE.length)
        txt_story.animateAppend(text) {
            textDone = true
            if (audioDone && !nextLineTriggered) {
                nextLineTriggered = true
                showNextLine()
            }
        }
    }

    private fun setDelay(text: kotlin.String, ap: AudioPlayer) {
        txt_story.delay = (ap.duration / text.length * 0.7).toLong()
    }

    override fun onDestroy() {
        super.onDestroy()
        dialog?.dismiss()
        dialog = null
        audioPlayer?.stop()
        audioPlayer = null
    }

    companion object {
        private const val UNDERLINE = "______"
        private const val GAME_RESULT_REQUEST = 1
    }
}
