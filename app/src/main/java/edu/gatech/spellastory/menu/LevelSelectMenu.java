package edu.gatech.spellastory.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.phoneme_list.PhonemeListActivity;

public class LevelSelectMenu extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_select);

        Button next = findViewById(R.id.button_level5);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PhonemeListActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });
    }

}
