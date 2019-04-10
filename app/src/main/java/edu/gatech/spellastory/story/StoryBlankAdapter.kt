package edu.gatech.spellastory.story

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.convertToGreyscale
import edu.gatech.spellastory.util.getWordDrawable
import kotlinx.android.synthetic.main.item_story_blank.view.*

class StoryBlankAdapter(
    private val words: List<Word>,
    private val completed: Boolean,
    private val clickListener: (Word) -> Unit
) :
    RecyclerView.Adapter<StoryBlankAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word, completed: Boolean, clickListener: (Word) -> Unit) {
            if (completed)
                itemView.btn_word.text = word.spelling
            else
                itemView.btn_word.text = word.spelling.map { '?' }.joinToString("")

            val drawable = itemView.context.getWordDrawable(word)
            drawable.setBounds(0, 0, 400, 400)
            if (!completed) drawable.convertToGreyscale()
            itemView.btn_word.setCompoundDrawables(null, drawable, null, null)

            itemView.btn_word.setOnClickListener { clickListener(word) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_blank, parent, false))
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) = holder.bind(words[i], completed, clickListener)
}