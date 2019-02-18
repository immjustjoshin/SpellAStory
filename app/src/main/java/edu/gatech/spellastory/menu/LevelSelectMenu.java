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

        Button lv5 = findViewById(R.id.button_level5);
        Button lv6 = findViewById(R.id.button_level6);
        Button lv7 = findViewById(R.id.button_level7);
        Button lv8 = findViewById(R.id.button_level8);

        setLevelButtonClickListener(lv5, 5);
        setLevelButtonClickListener(lv6, 6);
        setLevelButtonClickListener(lv7, 7);
        setLevelButtonClickListener(lv8, 8);
    }

    private void setLevelButtonClickListener(Button button, final int level) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), PhonemeListActivity.class);
                i.putExtra(PhonemeListActivity.EX_LEVEL, level);
                startActivity(i);
            }
        });
    }

}
