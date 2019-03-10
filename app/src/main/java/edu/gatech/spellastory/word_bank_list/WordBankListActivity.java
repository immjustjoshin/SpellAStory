package edu.gatech.spellastory.word_bank_list;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.R;

public class WordBankListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_bank_list);

        initView();
    }

    private void initView() {
        RecyclerView wordsRecyclerView = findViewById(R.id.rv_words);
        WordBankListAdapter adapter = new WordBankListAdapter(getApplicationContext(), getCompletedWords());
        wordsRecyclerView.setAdapter(adapter);
        wordsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getCompletedWords() {
        List<String> wordsCompleted = new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("completedWords", MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();

        List<String> wordsSaved = new ArrayList<>(map.keySet());

        for (int i = 0; i < wordsSaved.size(); i++) {
            if (pref.getBoolean(wordsSaved.get(i), false)) {
                wordsCompleted.add(wordsSaved.get(i));
            }
        }
        return wordsCompleted;
    }
}
