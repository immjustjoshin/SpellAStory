package edu.gatech.spellastory.story.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.gatech.spellastory.R
import edu.gatech.spellastory.domain.stories.Story
import edu.gatech.spellastory.util.getStoryDrawable
import kotlinx.android.synthetic.main.item_story_list.view.*

class StoryListAdapter(private val stories: List<Story>, private val clickListener: (Story) -> Unit) :
        RecyclerView.Adapter<StoryListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(story: Story, clickListener: (Story) -> Unit) {
            itemView.btn_story.text = story.title

            val drawable = itemView.context.getStoryDrawable(story)
            drawable.setBounds(0, 0, 400, 400)
            itemView.btn_story.setCompoundDrawables(drawable, null, null, null)

            itemView.btn_story.setOnClickListener { clickListener(story) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_story_list, parent, false))
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) = holder.bind(stories[i], clickListener)
}