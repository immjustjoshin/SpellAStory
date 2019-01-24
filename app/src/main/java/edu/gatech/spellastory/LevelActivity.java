package edu.gatech.spellastory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.gatech.spellastory.domain.Word;

public class LevelActivity extends AppCompatActivity {

    private static final String EX_WORD = "word";
    private Word word;
    TextView wordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        Intent intent = getIntent();
        String word = intent.getStringExtra("word");

        wordTextView = (TextView) findViewById(R.id.wordTextView);
        wordTextView.setText(word);
        //word = (Word) getIntent().getExtras().getSerializable(EX_WORD);
    }
}
