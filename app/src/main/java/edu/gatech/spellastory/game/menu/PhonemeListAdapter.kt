package edu.gatech.spellastory.game.menu

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import edu.gatech.spellastory.data.PhonemeWordsPair
import edu.gatech.spellastory.domain.Word
import kotlinx.android.synthetic.main.item_phoneme_list.view.*

class PhonemeListAdapter(
    private val phonemeWordsPairs: List<PhonemeWordsPair>,
    private val clickListener: (Word) -> Unit
) :
    RecyclerView.Adapter<PhonemeListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(phonemeWordsPair: PhonemeWordsPair, clickListener: (Word) -> Unit) {
            itemView.txt_phoneme.text = phonemeWordsPair.phoneme.spelling

            itemView.rv_images.adapter =
                ImageListAdapter(phonemeWordsPair.words, clickListener)
            itemView.rv_images.isNestedScrollingEnabled = true
            itemView.rv_images.layoutManager =
                LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_phoneme_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return phonemeWordsPairs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) = holder.bind(phonemeWordsPairs[i], clickListener)
}