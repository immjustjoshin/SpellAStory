package edu.gatech.spellastory.story_mode;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.domain.Word;

public class StoryPopUp extends AppCompatActivity {

    private ImageButton pic1, pic2, pic3;
    private TextView title1, title2, title3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_pop_up);

        // Set Up
        initComponents();
        setWindowMetrics();
    }

    /**
     * Initializes components used in this activity
     */
    private void initComponents() {
        pic1 = findViewById(R.id.ib_picture1);
        title1 = findViewById(R.id.tv_picture1);
        pic2 = findViewById(R.id.ib_picture2);
        title2 = findViewById(R.id.tv_picture2);
        pic3 = findViewById(R.id.ib_picture3);
        title3 = findViewById(R.id.tv_picture3);

        setImages();
        setImageButtonActions();
    }


    /**
     * Sets up the pop up window metrics/layout
     */
    private void setWindowMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;

        // Not sure what these two do
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private List<String> getCompletedWords() {
        Random rand = new Random();
        List<String> wordsCompleted = new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("completedWords", MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
        List<String> keys = new ArrayList<>(map.keySet());
        Gson gson = new Gson();

        // Be sure to account for that if the user has spelled less than 3 words

        if (keys.size() >= 3) {
            while (wordsCompleted.size() < 3) {
                int i = rand.nextInt(keys.size());
                String json = pref.getString(keys.get(i), "");
                Word word = gson.fromJson(json, Word.class);

                if (word.isComplete() && !wordsCompleted.contains(word.getSpelling())) {
                    wordsCompleted.add(word.getSpelling());
                }
            }
        }

        return wordsCompleted;
    }

    private void setImages() {
        List<String> completedWords = getCompletedWords();
        pic1.setImageDrawable(setPictureFor(completedWords.get(0)));
        title1.setText(completedWords.get(0));
        pic2.setImageDrawable(setPictureFor(completedWords.get(1)));
        title2.setText(completedWords.get(1));
        pic3.setImageDrawable(setPictureFor(completedWords.get(2)));
        title3.setText(completedWords.get(2));
    }

    private void setImageButtonActions() {
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                finish();
            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                finish();
            }
        });

        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                finish();
            }
        });
    }

    /**
     * Sets picture for word completed
     * @param word word that is completed
     * @return drawable of word
     */
    private Drawable setPictureFor(String word) {
        try {
            InputStream ims = getAssets().open("pictures/words/" + word + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            // Could not find picture associated with the word
            e.printStackTrace();
        }
        return null;
    }
}
