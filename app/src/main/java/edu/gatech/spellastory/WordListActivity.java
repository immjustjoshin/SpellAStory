package edu.gatech.spellastory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class WordListActivity extends AppCompatActivity {


    private static final String EX_PHONEME = "phoneme";
    private static final String EX_LEVEL = "level";
    private Phoneme phoneme;
    private int level;
    private List<Word> wordChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        if (getIntent() != null) {
            phoneme = (Phoneme) getIntent().getExtras().getSerializable(EX_PHONEME);
            level = (int) getIntent().getExtras().getSerializable(EX_LEVEL);
            wordChoices = getWords(phoneme, level);
        }
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
