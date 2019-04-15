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
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.domain.stories.Story
import edu.gatech.spellastory.domain.stories.StoryBlank
import edu.gatech.spellastory.domain.stories.StoryLine
import edu.gatech.spellastory.game.GameIntent
import edu.gatech.spellastory.util.AudioPlayer
import edu.gatech.spellastory.util.getStoryDrawable
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.StoryIntent(story: Story): Intent = Intent(this, StoryActivity::class.java)
        .apply { putExtra(INTENT_STORY, story) }

private const val INTENT_STORY = "story"

fun Context.GameFinishIntent(word: Word) = Intent().apply { putExtra(INTENT_WORD, word) }

private const val INTENT_WORD = "word"

class StoryActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private lateinit var story: Story
    private var storyPosition: Int = -1
    private var dialog: AlertDialog? = null
    private var audioPlayer: AudioPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        story = intent.getSerializableExtra(INTENT_STORY) as Story
        requireNotNull(story) { "no story provided in Intent extras" }

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

    private fun showNextLine() {
        storyPosition++
        val next = story.tokens[storyPosition]
        if (next.isBlank) {
            val blank = next as StoryBlank
            val word = story.getBlank(blank)
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

            launch {
                var audioDone = false
                var textDone = false

                audioPlayer!!.play { audioDone = true }
                txt_story.animateAppend(text) { textDone = true }

                while (!(audioDone && textDone)) delay(100L)
                showNextLine()
            }
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
        story.setBlank(blank, word)

        dialog?.dismiss()
        dialog = null

        val text = word.spelling
        audioPlayer = AudioPlayer.fromWord(word, this)
        setDelay(text, audioPlayer!!)

        launch {
            var audioDone = false
            var textDone = false

            audioPlayer!!.play { audioDone = true }
            if (txt_story.text.endsWith(UNDERLINE)) txt_story.remove(UNDERLINE.length)
            txt_story.animateAppend(text) { textDone = true }

            while (!(audioDone && textDone)) delay(100L)
            showNextLine()
        }
    }

    private fun setDelay(text: String, ap: AudioPlayer) {
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
