package edu.gatech.spellastory;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

import static org.apache.commons.collections.CollectionUtils.size;

public class GameActivity extends AppCompatActivity {

    public static final String EX_WORD = "word";
    private int letterIndex = 0;
    private Word word = null;
    public static final int phonemeCount = 9;
    public Map<Integer, List<Integer>> gridLayouts = new HashMap<>();
    private List<Phoneme> phonemeOptionsList;
    private ImageButton pictureImageButton;


    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        word = (Word) intent.getSerializableExtra(EX_WORD);

        pictureImageButton = (ImageButton) findViewById(R.id.imageButton_picture);
        setPictureFor(word);
        final MediaPlayer mp = setAudioFor(word);
        pictureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

        //There is probably a better way to do this. But for now, storing
        //layouts in this map
        gridLayouts.put(9, new ArrayList<Integer>(Arrays.asList(3, 3)));
        gridLayouts.put(10, new ArrayList<Integer>(Arrays.asList(4, 3)));

        rand = new Random(); //This random is not good! We should instantiate it
        //At the beginning of app instantiation.

        phonemeOptionsList = generateGamePhonemeList(word);
        setPhonemeButtons();
    }

    List<Phoneme> generateGamePhonemeList(Word word) {
        Log.d("GENERATE", "Phoneme list being generated");
        List<Phoneme> phonemeList = new ArrayList<>(word.getPhonemes());
        List<Phoneme> allPhonemesList = null;
        try {
            Database db = new Database(getAssets());
            allPhonemesList = db.getAllPhonemes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (allPhonemesList == null) {
            throw new Error("Could not load from database");
        }

        //Adding random phonemes
        while (size(phonemeList) < phonemeCount) {
            int randIndex = randInt(0, size(allPhonemesList) - 1);
            Phoneme phonemeToAdd = allPhonemesList.get(randIndex);
            boolean duplicate = false;
            //making sure no duplicate spellings
            for (Phoneme correctPhoneme : phonemeList) {
                if (correctPhoneme.getSpelling().contains(phonemeToAdd.getSpelling())) {
                    duplicate = true;
                }
            }
            if (!duplicate) {
                phonemeList.add(phonemeToAdd);
            }
        }
        Collections.shuffle(phonemeList);
        return phonemeList;
    }

    private void setPhonemeButtons() {
        GridLayout grid = findViewById(R.id.gameGrid);
        int columnCount = Objects.requireNonNull(gridLayouts.get(phonemeCount)).get(0);
        int rowCount = Objects.requireNonNull(gridLayouts.get(phonemeCount)).get(1);
        grid.setColumnCount(columnCount);
        grid.setRowCount(rowCount);
        for (int i = 0; i < phonemeCount; i++) {
            final Button phonemeButton = new Button(this);
            phonemeButton.setText(phonemeOptionsList.get(i).getSpelling());
            phonemeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Checking if correct answer
                    CharSequence buttonSpelling = phonemeButton.getText();
                    int spellingSize = buttonSpelling.length();
                    if (word.getSpelling().length() >= letterIndex + spellingSize) {
                        CharSequence toMatch = word.getSpelling().substring
                                (letterIndex, letterIndex + spellingSize);
                        if (phonemeButton.getText().equals(toMatch)) {
                            //Correct answer!
                            letterIndex += spellingSize;
                            TextView userSpelling = findViewById(R.id.userSpelling);
                            String newSpelling = userSpelling.getText().toString() + buttonSpelling.toString();
                            userSpelling.setText(newSpelling);
                            v.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });
            grid.addView(phonemeButton);
        }
    }

    /**
     * Sets the picture that is seen in the game play
     * @param word the word associated with the picture
     */
    private void setPictureFor(Word word) {
        pictureImageButton = findViewById(R.id.imageButton_picture);

        try {
            InputStream ims = getAssets().open("pictures/" + word.getSpelling() + ".png");
            Drawable d = Drawable.createFromStream(ims, null);
            pictureImageButton.setImageDrawable(d);
        } catch (IOException e) {
            // Could not find picture associated with the word
            e.printStackTrace();
        }
    }

    /**
     * Sets audio for the picture used in game play
     * @param word word associated with the picture
     * @return MediaPlayer object that will play the audio for the picture
     */
    private MediaPlayer setAudioFor(Word word) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/words/" + word.getSpelling() + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associated with the word
            e.printStackTrace();
        }
        return mp;
    }

    public int randInt(int min, int max) {

        //The leaps and bounds I go to generate something random...
        return rand.nextInt((max - min) + 1) + min;
    }


    //FindviewbyID -- cast to a button
    //Set text

    //Things that need to happen
    //1. Get the Word to be spelled from Phoneme ListActivity
    //2. Generate list with Word phoneme sequence and random phonemes (cannot match spelling!)
    //3. Display Picture in Top Center of word
    //4. Display phoneme list on bottom (list of buttons)
    //5. Correct clicks advance the pictures. Incorrect clicks do nothing


}
