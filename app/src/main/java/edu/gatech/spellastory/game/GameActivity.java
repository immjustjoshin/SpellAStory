package edu.gatech.spellastory.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

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

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

import static org.apache.commons.collections.CollectionUtils.size;

public class GameActivity extends AppCompatActivity implements GameEndDialogFragment.Listener {

    public static final String EX_WORD = "word";
    private int letterIndex = 0;
    private static final int phonemeCount = 4;
    private Map<Integer, List<Integer>> gridLayouts = new HashMap<>();
    private List<Phoneme> phonemeOptionsList;
    private List<Phoneme> correctPhonemeSequence = new ArrayList<>();
    private int phonemeSpelledCount = 0;
    private ImageButton pictureImageButton;
    private TextView userSpelling;
    private String toSpell = "";
    FragmentManager fm;

    private Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        userSpelling = findViewById(R.id.userSpelling);
        pictureImageButton = findViewById(R.id.imageButton_picture);
        Intent intent = getIntent();
        Word word = (Word) intent.getSerializableExtra(EX_WORD);

        // Sets up initial underlines and silent letters
        String startingUnderlines = setUpUnderlines(word);
        userSpelling.setText(Html.fromHtml(startingUnderlines), TextView.BufferType.SPANNABLE);

        // Sets up audio for word
        setPictureFor(word);
        final MediaPlayer picture = setAudioFor(word);
        pictureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picture.start();
            }
        });

        //There is probably a better way to do this. But for now, storing
        //layouts in this map
        gridLayouts.put(3, new ArrayList<>(Arrays.asList(3, 1)));
        gridLayouts.put(4, new ArrayList<>(Arrays.asList(2, 2)));

        rand = new Random(); //This random is not good! We should instantiate it
        // At the beginning of app instantiation.

        for (Phoneme correctPhoneme : word.getPhonemes()) {
            if (!correctPhoneme.getCode().equals("0")) {
                correctPhonemeSequence.add(correctPhoneme);
            }
        }
        phonemeOptionsList = generateGamePhonemeList();
        setPhonemeButtons(word);

        fm = getSupportFragmentManager();



    }

    private List<Phoneme> generateGamePhonemeList() {
        Log.d("GENERATE", "Phoneme list being generated");
        List<Phoneme> phonemeList = new ArrayList<>();
        Phoneme correct = correctPhonemeSequence.get(phonemeSpelledCount);
        phonemeList.add(correct);

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
            boolean silent = false;
            //making sure no duplicate spellings
            for (Phoneme phoneme : phonemeList) {
                if (phoneme.getSpelling().contains(phonemeToAdd.getSpelling())) {
                    duplicate = true;
                }
            }
            if (phonemeToAdd.getCode().equals("0")) {
                silent = true;
            }
            if (!duplicate && !silent) {
                phonemeList.add(phonemeToAdd);
            }
        }
        Collections.shuffle(phonemeList);
        return phonemeList;
    }

    private void setPhonemeButtons(final Word word) {
        GridLayout grid = findViewById(R.id.gameGrid);
        grid.removeAllViews();
        int columnCount = Objects.requireNonNull(gridLayouts.get(phonemeCount)).get(0);
        int rowCount = Objects.requireNonNull(gridLayouts.get(phonemeCount)).get(1);
        grid.setColumnCount(columnCount);
        grid.setRowCount(rowCount);
        for (int i = 0; i < phonemeCount; i++) {
            final Button phonemeButton = new Button(this);
            final Phoneme phoneme = phonemeOptionsList.get(i);
            phonemeButton.setText(phoneme.getSpelling());
            phonemeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            phonemeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //Checking if correct answer
                    CharSequence buttonSpelling = phonemeButton.getText();
                    int spellingSize = buttonSpelling.length();
                    if (toSpell.length() >= letterIndex + spellingSize) {
                        CharSequence toMatch = toSpell.substring
                                (letterIndex, letterIndex + spellingSize);
                        if (phonemeButton.getText().equals(toMatch)) {
                            // Correct answer!
                            letterIndex += spellingSize;
                            // Should do a check of isInstance here
                            Spannable userSpellingString = (Spannable) userSpelling.getText();
                            String newSpelling = Html.toHtml(userSpellingString);
                            for (int i = 0; i < phonemeButton.getText().length(); i++) {
                                int indexToChange = newSpelling.indexOf("_");
                                newSpelling = newSpelling.substring(0, indexToChange)
                                        + phonemeButton.getText().charAt(i) +
                                        newSpelling.substring(indexToChange + 1);
                            }
                            userSpelling.setText(Html.fromHtml(newSpelling), TextView.BufferType.SPANNABLE);
                            v.setVisibility(View.INVISIBLE);

                            phonemeSpelledCount++;

                            // Plays positive audio if user chooses the correct answer
                            if (toSpell.length() != letterIndex) {
                                playPositiveSound().start();
                                //resetGrid();
                                phonemeOptionsList = generateGamePhonemeList();
                                setPhonemeButtons(word); //Wow it's recursive!
                            } else {
                                // User has spelled the word completely!
                                markWordAsComplete(word);
                                resetGrid();
                                GameEndDialogFragment.newInstance(1).show(fm,"win");
                            }
                        } else {
                            // Incorrect answer!
                            playNegativeSound().start();
                        }
                    }
                }
            });

            phonemeButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Plays phoneme audio on long press
                    setAudioFor(phoneme).start();
                    return true;
                }
            });

            grid.addView(phonemeButton);
        }
    }
    /**
     * Resets the phonemeButton grid
     */
    private void resetGrid() {
        GridLayout grid = findViewById(R.id.gameGrid);
        grid.removeAllViews();
    }

    /**
     * Sets the picture that is seen in the game play
     *
     * @param word the word associated with the picture
     */
    private void setPictureFor(Word word) {
        pictureImageButton = findViewById(R.id.imageButton_picture);

        try {
            InputStream ims = getAssets().open("pictures/words/" + word.getSpelling() + ".png");
            Drawable d = Drawable.createFromStream(ims, null);
            pictureImageButton.setImageDrawable(d);
        } catch (IOException e) {
            // Could not find picture associated with the word
            e.printStackTrace();
        }
    }

    /**
     * Sets up the starting underlines of the word being spelled.
     * Inputs any silent letters which are color coded green.
     * @param word word user is spelling
     * @return correct letter spelling order (without silent letters)
     */
    private String setUpUnderlines(Word word) {
        String startingUnderlines = "";
        //Create starting underlines and get correct letter spelling order (without silents)
        for (Phoneme phoneme : word.getPhonemes()) {
            for (int i = 0; i < phoneme.getSpelling().length(); i++) {
                if (phoneme.getCode().equals("0")) {
                    //if it's a silent then it must appear at start.
                    startingUnderlines = startingUnderlines.
                            concat("<font color='green'><u>" + phoneme.getSpelling().charAt(i) + "</u></font> ");
                } else {
                    toSpell = toSpell.concat(String.valueOf(phoneme.getSpelling().charAt(i)));
                    startingUnderlines = startingUnderlines.
                            concat("<u>" + "_" + "</u> ");
                }
            }
        }
        return startingUnderlines;
    }

    /**
     * Sets audio for the picture used in game play
     *
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

    /**
     * Sets audio for the phoneme options used in game play
     *
     * @param phoneme one of the phoneme options
     * @return MediaPlayer object that will play the audio for the phoneme
     */
    private MediaPlayer setAudioFor(Phoneme phoneme) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/phonemes/" + phoneme.getSpelling() + "(" + phoneme.getCode() + ").mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associate with the word
            e.printStackTrace();
        }
        return mp;
    }

    /**
     * Plays positive sound when user chooses the correct phoneme.
     * @return MediaPlayer with positive sound
     */
    private MediaPlayer playPositiveSound() {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/Ding Sound Effect.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mp;
    }

    /**
     * Plays encouraging audio when user chooses wrong phoneme.
     * @return MediaPlayer with encouraging audio
     */
    private MediaPlayer playNegativeSound() {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/Try Again.mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mp;
    }

    /**
     * Marks word as complete and saves it in Shared Preferences
     * @param word word to mark as complete
     */
    private void markWordAsComplete(Word word) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("completedWords", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        word.setComplete(true);
        Gson gson = new Gson();
        String json = gson.toJson(word);

        editor.putString(word.getSpelling(), json);
        editor.apply();

        // plays praise word audio when user spells word correctly
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetManager temp = getAssets();
            String[] files = temp.list("audio/positive_praise_words/");

            // Chooses random integer to call random praise word audio
            Random r = new Random();
            int i = 1;
            if (files != null) {
                i = r.nextInt(files.length) + 1;
            }
            AssetFileDescriptor afd = getAssets().openFd("audio/positive_praise_words/ppw(" + i + ").mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            mp.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
            }

    private int randInt(int min, int max) {

        //The leaps and bounds I go to generate something random...
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void onItemClicked(int position) {
        // TODO: make an enum
        //First button on bottom sheet is always go back.
        if (position == 0){
            finish();
        }
    }
}
