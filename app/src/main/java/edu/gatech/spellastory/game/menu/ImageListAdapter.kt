package edu.gatech.spellastory.game.menu

import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.getWordDrawable
import kotlinx.android.synthetic.main.item_image_list.view.*

class ImageListAdapter(private val words: List<Word>, private val clickListener: (Word) -> Unit) :
    RecyclerView.Adapter<ImageListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word, image: Drawable, clickListener: (Word) -> Unit) {
            itemView.btn_image.setOnClickListener { clickListener(word) }
            itemView.btn_image.setImageDrawable(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_image_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val word = words[i]
        val drawable = holder.itemView.context.getWordDrawable(word)
        holder.bind(word, drawable, clickListener)
    }
}