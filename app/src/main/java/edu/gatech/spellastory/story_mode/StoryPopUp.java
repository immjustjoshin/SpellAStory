package edu.gatech.spellastory.story_mode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import edu.gatech.spellastory.R;
import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.domain.Word;

public class StoryPopUp extends AppCompatActivity {

    public static final String CATEGORIES = "categories";
    private ImageButton pic1, pic2, pic3;
    private TextView title1, title2, title3, spellMore;
    private ArrayList<Word> selectedWords = new ArrayList<>();
    private ArrayList<String> categories;

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
        spellMore = findViewById(R.id.tv_spell_more);

        setImages();
    }

    /**
     * Sets up the pop up window metrics/layout
     */
    private void setWindowMetrics() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;

        // Not sure what these two do
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    private void setImages() {
        ImageButton[] pics = {pic1, pic2, pic3};
        TextView[] titles = {title1, title2, title3};
        Intent intent = getIntent();
        categories = intent.getStringArrayListExtra(CATEGORIES);
        Collections.shuffle(categories);

        if (categories.contains("friends")) {   // Show friends
            showSpellMoreMessage(pics, titles, false);
            showFriendsOptions(pics, titles);
        } else {
            List<Word> completedWords = getCompletedWords(getApplicationContext());
            for (int i = 0; i < 3; i++) {
                Collections.shuffle(completedWords);
            }
            for (int i = 0; i < 3; i++) {
                selectedWords.add(completedWords.get(i));
                ImageView pic = pics[i];
                TextView title = titles[i];
                pic.setImageDrawable(setPictureFor(completedWords.get(i).getSpelling()));
                title.setText(completedWords.get(i).getSpelling());
            }



            // Temporary comment. Later to be implemented
//            ArrayList<Word> completedWords = selectRandomPicturesFrom(categories);
//
//            if (completedWords.size() < 3) {
////                showSpellMoreMessage(pics, titles, true);
//                showSpellMoreMessage(pics,titles, false);
//
//                for (int i = 0; i < 3; i++) {
//                    selectedWords.add(completedWords.get(0));
//                    String completedWord = completedWords.get(0).getSpelling();
//                    ImageButton pic = pics[i];
//                    TextView title = titles[i];
//                    pic.setImageDrawable(setPictureFor(completedWord));
//                    title.setText(completedWord);
//                }
//            } else {
//                showSpellMoreMessage(pics, titles, false);
//
//                // Shuffles words randomly and selects first 3 words
//                Collections.shuffle(completedWords);
//                for (int i = 0; i < 3; i++) {
//                    selectedWords.add(completedWords.get(i));
//                    String completedWord = completedWords.get(i).getSpelling();
//                    ImageButton pic = pics[i];
//                    TextView title = titles[i];
//                    pic.setImageDrawable(setPictureFor(completedWord));
//                    title.setText(completedWord);
//                }
//            }
        }
        setImageButtonActions();
    }

    private void showFriendsOptions(ImageButton[] pics, TextView[] titles) {
        String[] temp;
        try {
            temp = getAssets().list("audio/names/");
            ArrayList<String> list = new ArrayList<>();
            if (temp != null) {
                list.addAll(Arrays.asList(temp));
                for (int i = 0; i < 3; i++) {
                    Collections.shuffle(list);
                }
                for (int i = 0; i < 3; i++) {
                    ImageButton pic = pics[i];
                    TextView title = titles[i];

//                    pic.setImageDrawable(setPictureFor(list.get(0))); uncomment when pics for all names are given
                    // Removes .mp3 from title
                    String name = list.get(i).substring(0, list.get(i).length()-4);
                    title.setText(name);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setImageButtonActions() {
        pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                Intent returnData = new Intent();
                String friends = "friends";
                if (categories.contains(friends)) {
                    returnData.putExtra(StoryActivity.EX_NAME, title1.getText().toString());
                    setResult(StoryActivity.NAME_CODE, returnData);
                } else {
                    returnData.putExtra(StoryActivity.EX_WORD, selectedWords.get(0));
                    setResult(StoryActivity.WORD_CODE, returnData);
                }
                finish();
            }
        });

        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                Intent returnData = new Intent();
                String friends = "friends";
                if (categories.contains(friends)) {
                    returnData.putExtra(StoryActivity.EX_NAME, title2.getText().toString());
                    setResult(StoryActivity.NAME_CODE, returnData);
                } else {
                    returnData.putExtra(StoryActivity.EX_WORD, selectedWords.get(1));
                    setResult(StoryActivity.WORD_CODE, returnData);
                }
                finish();
            }
        });

        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add animation or visual feedback of selected photo
                Intent returnData = new Intent();
                String friends = "friends";
                if (categories.contains(friends)) {
                    returnData.putExtra(StoryActivity.EX_NAME, title3.getText().toString());
                    setResult(StoryActivity.NAME_CODE, returnData);
                } else {
                    returnData.putExtra(StoryActivity.EX_WORD, selectedWords.get(2));
                    setResult(StoryActivity.WORD_CODE, returnData);
                }
                finish();
            }
        });
    }

    private void showSpellMoreMessage(ImageButton[] pics, TextView[] titles, boolean show) {
        for (ImageButton pic : pics) {
            if (show) {
                pic.setVisibility(View.GONE);
            } else {
                pic.setVisibility(View.VISIBLE);
            }
        }
        for (TextView title : titles) {
            if (show) {
                title.setVisibility(View.GONE);
            } else {
                title.setVisibility(View.VISIBLE);
            }
        }
        if (show) {
            spellMore.setVisibility(View.VISIBLE);
        } else {
            spellMore.setVisibility(View.GONE);
        }
    }

    private ArrayList<Word> selectRandomPicturesFrom(ArrayList<String> categories) {
        ArrayList<Word> selectedWords = new ArrayList<>();
        try {
            Database db = new Database(getAssets());
            for (String c : categories) {
                List<Word> temp = db.getSolvedWordsFromCategory(3, c, getApplicationContext());
                selectedWords.addAll(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return selectedWords;
    }

    /**
     * Sets picture for word completed
     *
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

    private MediaPlayer setAudioFor(String name) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/names/" + name + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associate with the word
            e.printStackTrace();
        }
        return mp;
    }

    private List<Word> getCompletedWords(Context context) {
        List<Word> wordsCompleted = new ArrayList<>();
        SharedPreferences pref = context.getSharedPreferences("completedWords", MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
        List<String> keys = new ArrayList<>(map.keySet());
        Gson gson = new Gson();
        for (int i = 0; i < keys.size(); i++) {
            String json = pref.getString(keys.get(i), "");
            Word word = gson.fromJson(json, Word.class);
            if (word.isComplete()) {
                wordsCompleted.add(word);
            }
        }
        return wordsCompleted;
    }
}
