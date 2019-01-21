package edu.gatech.spellastory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.gatech.spellastory.domain.Word;

public class LevelActivity extends AppCompatActivity {

    private static final String EX_WORD = "word";
    private Word word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        word = (Word) getIntent().getExtras().getSerializable(EX_WORD);
    }
}
