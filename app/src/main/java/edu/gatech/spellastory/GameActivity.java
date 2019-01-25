package edu.gatech.spellastory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import edu.gatech.spellastory.domain.Word;

public class GameActivity extends AppCompatActivity {

    public static final String EX_WORD = "word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        Word word = (Word) intent.getSerializableExtra(EX_WORD);

        TextView wordTextView = findViewById(R.id.wordTextView);
        wordTextView.setText(word.getSpelling());
    }
}
