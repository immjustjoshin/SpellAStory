package edu.gatech.spellastory.story_template_list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.story_mode.StoryActivity;

public class StoryListActivity extends AppCompatActivity implements StoryListAdapter.StoryClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_list);

        initView();
    }

    private void initView() {
        RecyclerView storyRecyclerView = findViewById(R.id.rv_stories);
        StoryListAdapter adapter = new StoryListAdapter(getApplicationContext(), getStories());
        adapter.setStoryClickListener(this);
        storyRecyclerView.setAdapter(adapter);
        storyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getStories() {
        List<String> stories = new ArrayList<>();
        stories.add("The Special Invention");
        return stories;
    }

    @Override
    public void onStoryClick(String story) {
        startStoryModeActivityForStory(story);
    }

    private void startStoryModeActivityForStory(String story) {
        Intent intent = new Intent(this, StoryActivity.class);
        intent.putExtra(StoryActivity.EX_WORD, story);
        startActivity(intent);
    }
}
