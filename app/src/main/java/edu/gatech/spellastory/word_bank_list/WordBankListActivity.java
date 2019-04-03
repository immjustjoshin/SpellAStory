package edu.gatech.spellastory.word_bank_list;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.domain.Word;

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

    private List<Word> getCompletedWords() {
        List<Word> wordsCompleted = new ArrayList<>();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("completedWords", MODE_PRIVATE);
        Map<String, ?> map = pref.getAll();
        List<String> keys = new ArrayList<>(map.keySet());
        Gson gson = new Gson();
        for (int i = 0; i < keys.size(); i++) {
            String json = pref.getString(keys.get(i), "");
            Word word = gson.fromJson(json, Word.class);
            if (word.isComplete()) {
                wordsCompleted.add(word);
            }
        }
        return wordsCompleted;
    }
}
