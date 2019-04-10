package edu.gatech.spellastory.util

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView

/**
 * CREDIT: https://gist.github.com/Antarix/6388606
 */
class TypeWriter : TextView {

    private var _text: CharSequence = ""
    private var index: Int = 0
    var delay: Long = 50

    private val _handler = Handler()
    private fun characterAdder() = characterAdder {}
    private fun characterAdder(onFinished: () -> Unit): Runnable {
        return object : Runnable {
            override fun run() {
                text = _text.subSequence(0, index++)
                if (index <= _text.length) {
                    _handler.postDelayed(this, delay)
                } else {
                    onFinished()
                }
            }
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun animateText(text: CharSequence) {
        _text = text
        index = 0

        setText("")
        _handler.removeCallbacks(characterAdder())
        _handler.postDelayed(characterAdder(), delay)
    }

    fun animateAppend(text: CharSequence) {
        _text = _text.toString() + text
        _handler.removeCallbacks(characterAdder())
        _handler.postDelayed(characterAdder(), delay)
    }

    fun animateAppend(text: CharSequence, onFinished: () -> Unit) {
        if (_text.isNotEmpty() && _text.last() != ' ') _text = "$_text "
        _text = _text.toString() + text
        _handler.removeCallbacks(characterAdder())
        _handler.postDelayed(characterAdder(onFinished), delay)
    }

    fun remove(numChars: Int) {
        _text = _text.dropLast(numChars)
        index -= numChars
        text = _text
    }
}
