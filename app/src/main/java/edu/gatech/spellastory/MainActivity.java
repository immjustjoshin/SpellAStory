package edu.gatech.spellastory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    // Layouts
    TextView title;
    Button oneWord, threeWord, myWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize layouts
        title = (TextView) findViewById(R.id.titleTextView);
        oneWord = (Button) findViewById(R.id.oneWordButton);
        threeWord = (Button) findViewById(R.id.threeWordButton);
        myWord = (Button) findViewById(R.id.myWordsButton);

        oneWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent oneWordIntent = new Intent(getApplicationContext(), OneWord.class);
                startActivity(oneWordIntent);
            }
        });

        threeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent threeWordIntent = new Intent(getApplicationContext(), ThreeWord.class);
                startActivity(threeWordIntent);
            }
        });

        myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myWordIntent = new Intent(getApplicationContext(), MyWords.class);
                startActivity(myWordIntent);
            }
        });
    }
}
