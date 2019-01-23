package edu.gatech.spellastory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class WordListActivity extends AppCompatActivity {


    private static final String EX_PHONEME = "phoneme";
    private static final String EX_LEVEL = "level";
    private Phoneme phoneme;
    private int level;
    private List<Word> wordChoices;
    private int[] wordImageID;
    private ListView pictureList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        pictureList = (ListView) findViewById(R.id.pictureListView);

        if (getIntent() != null) {
            phoneme = (Phoneme) getIntent().getExtras().getSerializable(EX_PHONEME);
            level = (int) getIntent().getExtras().getSerializable(EX_LEVEL);
            wordChoices = getWords(phoneme, level);
        }

        int i = 0;
        for (Word word : wordChoices) {
            wordImageID[i] = word.picture;
            i++;
        }
        PictureListAdapter adapter = new PictureListAdapter(WordListActivity.this, wordImageID);
        pictureList.setAdapter(adapter);

//        Phoneme bl = new Phoneme("bl");
//        Phoneme o = new Phoneme("o");
//        Phoneme w = new Phoneme("w");
//        Phoneme d = new Phoneme("d");
//        Phoneme a = new Phoneme("a");
//        Phoneme c = new Phoneme("c");
//        Phoneme k = new Phoneme("k");
//        List<Phoneme> black1 = new ArrayList<>();
//        black1.add(bl);
//        black1.add(a);
//        black1.add(c);
//        black1.add(k);
//        Word black = new Word(black1);
//        black.setPicture(R.drawable.aot1);
//        List<Phoneme> blow1 = new ArrayList<>();
//        blow1.add(bl);
//        blow1.add(o);
//        blow1.add(w);
//        Word blow = new Word(blow1);
//        blow.setPicture(R.drawable.aot2);
//        List<Phoneme> blood1 = new ArrayList<>();
//        blood1.add(bl);
//        blood1.add(o);
//        blood1.add(o);
//        blood1.add(d);
//        Word blood = new Word(blood1);
//        blood.setPicture(R.drawable.aot3);
//        wordChoices = new ArrayList<>();
//        wordChoices.add(black);
//        wordChoices.add(blow);
//        wordChoices.add(blood);
//        wordImageID[0] = black.getPicture();
//        wordImageID[1] = blow.getPicture();
//        wordImageID[2] = blood.getPicture();
    }

    /**
     * Gets the list of words that are associated with the
     * level and phoneme selected.
     * @param phoneme the phoneme the user wants to practice with
     * @param level level the user is playing at
     * @return list of words the user can choose to spell
     */
    private List<Word> getWords(Phoneme phoneme, int level) {
        // Make call to database class and have method in there where it retrieves the list of
        // words from the phonemes that we hard-coded in.
        return null;
    }
}
