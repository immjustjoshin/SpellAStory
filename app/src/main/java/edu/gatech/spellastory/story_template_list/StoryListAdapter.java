package edu.gatech.spellastory.story_template_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.story_mode.StoryActivity;

public class StoryListAdapter extends RecyclerView.Adapter<StoryListAdapter.ViewHolder> {

    private Context context;
    private List<String> stories;
    private StoryClickListener storyClickListener;

    StoryListAdapter(Context context, List<String> stories) {
        this.context = context;
        this.stories = stories;
    }

    public void setStoryClickListener(StoryClickListener storyClickListener) {
        this.storyClickListener = storyClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_story_template_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        ImageButton image = viewHolder.imageStory;
        TextView title = viewHolder.storyTitle;

        final String storyTitle = stories.get(i);
        image.setImageDrawable(setPictureFor(storyTitle));
        title.setText(storyTitle);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyClickListener.onStoryClick(storyTitle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    private Drawable setPictureFor(String word) {
        try {
            InputStream ims = context.getAssets().open("pictures/story_templates/" + word + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            // Could not find picture for story template
            e.printStackTrace();
        }
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageStory;
        TextView storyTitle;

        ViewHolder(View itemView) {
            super(itemView);
            imageStory = itemView.findViewById(R.id.iv_story_picture);
            storyTitle = itemView.findViewById(R.id.tx_story_title);
        }
    }

    interface StoryClickListener {
        void onStoryClick(String story);
    }
}
