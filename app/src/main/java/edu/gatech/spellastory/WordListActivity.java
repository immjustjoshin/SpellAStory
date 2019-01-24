package edu.gatech.spellastory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;

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

//        if (getIntent() != null) {
//            phoneme = (Phoneme) getIntent().getExtras().getSerializable(EX_PHONEME);
//            level = (int) getIntent().getExtras().getSerializable(EX_LEVEL);
//            wordChoices = getWords(phoneme, level);
//        }

        // Temporary Pictures
        wordChoices = new ArrayList<>();
        Phoneme c = new Phoneme("c");
        Phoneme o = new Phoneme("o");
        Phoneme p = new Phoneme("p");
        List<Phoneme> cop1 = new ArrayList<>();
        cop1.add(c);
        cop1.add(o);
        cop1.add(p);
        Word cop = new Word(cop1);
        cop.setPicture(R.drawable.cop);
        wordChoices.add(cop);

        Phoneme d = new Phoneme("d");
        List<Phoneme> doc1 = new ArrayList<>();
        doc1.add(d);
        doc1.add(o);
        doc1.add(c);
        Word doc = new Word(doc1);
        doc.setPicture(R.drawable.doc);
        wordChoices.add(doc);

        Phoneme e = new Phoneme("e");
        Phoneme l = new Phoneme("l");
        Phoneme f = new Phoneme("f");
        List<Phoneme> elf1 = new ArrayList<>();
        elf1.add(e);
        elf1.add(l);
        elf1.add(f);
        Word elf = new Word(elf1);
        elf.setPicture(R.drawable.elf);
        wordChoices.add(elf);

        Phoneme g = new Phoneme("g");
        Phoneme a = new Phoneme("a");
        List<Phoneme> gal1 = new ArrayList<>();
        gal1.add(g);
        gal1.add(a);
        gal1.add(l);
        Word gal = new Word(gal1);
        gal.setPicture(R.drawable.gal);
        wordChoices.add(gal);

        Phoneme k = new Phoneme("k");
        Phoneme i = new Phoneme("i");
        List<Phoneme> kid1 = new ArrayList<>();
        kid1.add(k);
        kid1.add(i);
        kid1.add(d);
        Word kid = new Word(kid1);
        kid.setPicture(R.drawable.kid);
        wordChoices.add(kid);

        Phoneme r = new Phoneme("r");
        List<Phoneme> ref1 = new ArrayList<>();
        ref1.add(r);
        ref1.add(e);
        ref1.add(f);
        Word ref = new Word(ref1);
        ref.setPicture(R.drawable.ref);
        wordChoices.add(ref);
        wordImageID = new int[wordChoices.size()];

        int j = 0;
        for (Word word : wordChoices) {
            wordImageID[j] = word.getPicture();
            j++;
        }
        PictureListAdapter adapter = new PictureListAdapter(WordListActivity.this, wordImageID, wordChoices);
        pictureList.setAdapter(adapter);

        pictureList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = (Word) parent.getItemAtPosition(position);
                List<Phoneme> spelling = word.getSpelling();
                String finalSpelling = "";
                for (Phoneme p : spelling) {
                    finalSpelling = finalSpelling + p.getSpelling();
                }
                Intent intent = new Intent(WordListActivity.this, LevelActivity.class);
                intent.putExtra("word", finalSpelling);
                startActivity(intent);
            }
        });
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
