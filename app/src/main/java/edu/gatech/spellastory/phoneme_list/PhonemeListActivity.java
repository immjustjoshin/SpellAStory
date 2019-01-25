package edu.gatech.spellastory.phoneme_list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import edu.gatech.spellastory.GameActivity;
import edu.gatech.spellastory.R;
import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.data.PhonemeWordsPair;
import edu.gatech.spellastory.domain.Word;

public class PhonemeListActivity extends AppCompatActivity implements PhonemeListAdapter.WordClickListener {

    private static final String TAG = PhonemeListActivity.class.getSimpleName();
    private static final int LEVEL = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneme_list);

        initView();
    }

    private void initView() {
        RecyclerView phonemesRecyclerView = findViewById(R.id.rv_phonemes);
        PhonemeListAdapter adapter = new PhonemeListAdapter(getLevelData());
        adapter.setWordClickListener(this);
        phonemesRecyclerView.setAdapter(adapter);
        phonemesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<PhonemeWordsPair> getLevelData() {
        try {
            Database db = new Database(getAssets());
            return db.getWordsForLevel(LEVEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onWordClick(Word word) {
        startGameActivityForWord(word);
    }

    private void startGameActivityForWord(Word word) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(GameActivity.EX_WORD, word);
        startActivity(intent);
    }
}
