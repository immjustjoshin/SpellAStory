package edu.gatech.spellastory.util

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import edu.gatech.spellastory.domain.Phoneme
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.domain.stories.Story
import java.io.FileNotFoundException
import java.lang.IllegalStateException

class AudioPlayer private constructor(path: kotlin.String, context: Context) {

    private val mp = MediaPlayer()
    var ready: Boolean = false
    val duration
        get() = if (ready) mp.duration else 0

    init {
        ready = try {
            val afd = context.assets.openFd(path)
            mp.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            afd.close()
            mp.prepare()
            true
        } catch (e: FileNotFoundException) {
            Log.e("AudioPlayer", "No file found at $path")
            false
        }
    }

    fun play() {
        if (ready) mp.start()
    }

    fun stop() {
        try {
            mp.stop()
            mp.release()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun play(onFinish: () -> Unit) {

        fun finish() {
            mp.release()
            onFinish()
        }

        if (ready) {
            mp.setOnCompletionListener { finish() }
            mp.start()
        } else finish()
    }

    companion object {
        fun fromWord(word: Word, context: Context) = AudioPlayer("audio/${if (word.category?.name == "friends") "names" else "words"}/${word.spelling}.mp3", context)
        fun fromPhoneme(phoneme: Phoneme, context: Context) = AudioPlayer("audio/phonemes/${phoneme.spelling}(${phoneme.code}).mp3", context)
        fun fromStory(story: Story, audioFile: kotlin.String, context: Context) = AudioPlayer("audio/stories/" + story.filename + "/" + audioFile + ".mp3", context)
        fun positiveSound(context: Context) = AudioPlayer("audio/Ding Sound Effect.mp3", context)
        fun negativeSound(context: Context) = AudioPlayer("audio/Try Again.mp3", context)
        fun praiseWord(context: Context): AudioPlayer {
            val randomFile = context.assets.list("audio/positive_praise_words")?.random()
            return AudioPlayer("audio/positive_praise_words/$randomFile", context)
        }
    }
}