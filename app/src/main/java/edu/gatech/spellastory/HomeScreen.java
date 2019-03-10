package edu.gatech.spellastory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.gatech.spellastory.menu.LevelSelectMenu;
import edu.gatech.spellastory.word_bank_list.WordBankListActivity;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Button spellAWord = findViewById(R.id.spellaword);
        Button spellAStory = findViewById(R.id.spellastory);
        Button wordBank = findViewById(R.id.wordbank);


        // Spell A Word Mode
        spellAWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), LevelSelectMenu.class));
            }
        });

        // Word Bank
        wordBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), WordBankListActivity.class));
            }
        });


    }
}
