package edu.gatech.spellastory.story_mode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import edu.gatech.spellastory.R;

public class StoryActivity extends AppCompatActivity {

    public static final String EX_WORD = "story";
    private static final String SPEECH_BUBBLE_IMG = "speech_bubble";
    private ImageView storyTemplate, speechBubble;
    private TextView placeHolder;
    private Button popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // Initial Set Up
        String storyTitle = getStoryTitle();
        storyTemplate = findViewById(R.id.iv_story_template);
        speechBubble = findViewById(R.id.iv_speech_bubble);
        placeHolder = findViewById(R.id.tv_placeholder);
        setStoryTemplateFor(storyTitle);

        popup = findViewById(R.id.popup);
        popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StoryPopUp.class);
                startActivity(intent);
                String s = "hah ha hah  ha ah ah a ha ah aha aha ah ah";
                placeHolder.setText(placeHolder.getText() + s);
            }
        });

    }

    private String getStoryTitle() {
        Intent intent = getIntent();
        return intent.getStringExtra(EX_WORD);
    }

    /**
     * Sets the template image and speech bubble for
     * the spell a story mode screen
     * @param story Title of story selected
     */
    private void setStoryTemplateFor(String story) {
        Drawable template = setPictureFor(story);
        Drawable bubble = setPictureFor(SPEECH_BUBBLE_IMG);
        placeHolder.setMovementMethod(new ScrollingMovementMethod());

        if (template != null) {
            storyTemplate.setImageDrawable(template);
        }
        if (bubble != null) {
            speechBubble.setImageDrawable(bubble);
        }
    }

    private Drawable setPictureFor(String image) {
        try {
            InputStream ims = getAssets().open("pictures/story_templates/" + image + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            // Could not find picture for Story Template
            e.printStackTrace();
        }
        return null;
    }
}
