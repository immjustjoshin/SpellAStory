package edu.gatech.spellastory.util

import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import edu.gatech.spellastory.R
import edu.gatech.spellastory.domain.Word
import java.io.FileNotFoundException

fun Context.getWordDrawable(word: Word): Drawable {
    return try {
        val ims = assets.open("pictures/words/${word.spelling}.png")
        Drawable.createFromStream(ims, null)
    } catch (e: FileNotFoundException) {
        resources.getDrawable(R.drawable.placeholder)
    }
}

fun Context.getStoryDrawable(story: String): Drawable {
    val ims = assets.open("pictures/story_templates/$story.png")
    return Drawable.createFromStream(ims, null)
}

fun Drawable.convertToGreyscale(): Drawable {
    val matrix = ColorMatrix()
    matrix.setSaturation(0f)
    val filter = ColorMatrixColorFilter(matrix)
    colorFilter = filter
    return this
}