package edu.gatech.spellastory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.data.Phonemes;
import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

import static org.apache.commons.collections.CollectionUtils.size;

public class GameActivity extends AppCompatActivity {

    public static final String EX_WORD = "word";
    public static final int phonemeNumber = 9;
    public Map<Integer, List<Integer>> gridLayouts = new HashMap<>();
    private List<Phoneme> phonemeOptionsList;
    Random rand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        Word word = (Word) intent.getSerializableExtra(EX_WORD);

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(word.getSpelling());
        //There is probably a better way to do this. But for now, storing
        //layouts in this map
        gridLayouts.put(9, new ArrayList<Integer>(Arrays.asList(3,3)));
        gridLayouts.put(10, new ArrayList<Integer>(Arrays.asList(4,3)));

        rand = new Random(); //This random is not good! We should instantiate it
        //At the beginning of app instantiation.

        phonemeOptionsList = generateGamePhonemeList(word);
    }

    List<Phoneme> generateGamePhonemeList(Word word){
        List<Phoneme> phonemeList = new ArrayList<>(word.getPhonemes());
        List<Phoneme> allPhonemesList = null;
        try {
            Database db = new Database(getAssets());
            allPhonemesList = db.getAllPhonemes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (allPhonemesList == null){
            throw new Error("Could not load from database");
        }

        //Adding random phonemes
        while (size(phonemeList) < phonemeNumber){
            int randIndex = randInt(0,size(allPhonemesList));
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

        return phonemeList;
    }

    private void setButtonListeners(){
        GridLayout grid = findViewById(R.id.gameGrid);
        int columnCount = Objects.requireNonNull(gridLayouts.get(phonemeNumber)).get(0);
        int rowCount = Objects.requireNonNull(gridLayouts.get(phonemeNumber)).get(1);
        grid.setColumnCount(columnCount);
        grid.setRowCount(rowCount);
        for (int i = 0; i < phonemeNumber; i++){
            final Button phonemeSelection = new Button(this);
            phonemeSelection.setText(phonemeOptionsList.get(i).getSpelling());
            phonemeSelection.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    phonemeSelection.getText();
                }});
            grid.addView(phonemeSelection);
        }
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
