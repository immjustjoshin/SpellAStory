package edu.gatech.spellastory.bank

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import edu.gatech.spellastory.domain.Word
import edu.gatech.spellastory.util.getWordDrawable
import kotlinx.android.synthetic.main.item_bank.view.*

class BankAdapter(private val words: List<Word>) : RecyclerView.Adapter<BankAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(word: Word) {
            itemView.txt_word.text = word.spelling

            val drawable = itemView.context.getWordDrawable(word)
            drawable.setBounds(0, 0, 200, 200)
            itemView.txt_word.setCompoundDrawables(drawable, null, null, null)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false))
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) = holder.bind(words[i])
}