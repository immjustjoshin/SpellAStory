package edu.gatech.spellastory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.data.PhonemeWordsPair;

public class PhonemeListActivity extends AppCompatActivity {

    private static final String TAG = PhonemeListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneme_list);

        readPhonemes();
    }

    private void readPhonemes() {
        try {
            Database db = new Database(getAssets());
            List<PhonemeWordsPair> lv5Words = db.getWordsForLevel(5);
            Log.d(TAG, lv5Words.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
