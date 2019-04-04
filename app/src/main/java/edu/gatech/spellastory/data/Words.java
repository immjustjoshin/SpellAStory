package edu.gatech.spellastory.data;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import com.google.gson.Gson;

import edu.gatech.spellastory.domain.Word;

class Words extends AppCompatActivity {

    // Maps word to phoneme codes
    // Ex. swan -> [95,3,61]
    private Map<String, List<String>> words;

    // Map each category to a corresponding list of words
    private Map<String, List<String>> categoryMap;

    Words(Reader csvReader) {
        categoryMap = new HashMap<>();
        words = readWords(csvReader);
    }

    private Map<String, List<String>> readWords(Reader csvReader) {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String word = nextLine[0];
                String category = nextLine[1];

                // Add each word to a list that a category maps to
                List<String> temp = categoryMap.get(category);
                if (temp == null) temp = new ArrayList<>();
                temp.add(word);
                categoryMap.put(category, temp);

                String[] coded_phonemes = Arrays.copyOfRange(nextLine, 2, nextLine.length);
                phonemes.put(word, Arrays.asList(coded_phonemes));
            }
            return phonemes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<String> getPhonemeCodesForWord(String word) {
        return words.get(word);
    }

    List<String> getAllWords() {
        return new ArrayList<>(words.keySet());
    }

    // return a list of **up to** k random solved words for a category, may not be k solved words available
    List<String> getSolvedWordsFromCategory(int k, String category) {
        List<Word> completed = getCompletedWords();
        Collections.shuffle(completed); // randomize list

        List<String> out = new ArrayList<>();

        List<String> categoryWords = categoryMap.get(category);
        if (categoryWords == null) return out;
        int i = 0;
        for (Word word : completed) {
            if (i >= k) return out; // retrieve at most k words
            String temp = word.getSpelling();
            if (categoryWords.contains(temp)) {
                out.add(temp);
                i++;
            }
        }
        return out;
    }

    // return a list of **up to** k random unsolved words for a category, may not be k unsolved words available
    List<String> getUnsolvedWordsFromCategory(int k, String category) {
        List<Word> completed = getCompletedWords();
        List<String> out = new ArrayList<>();

        List<String> completedString = new ArrayList<>();
        for (Word temp : completed) {
            completedString.add(temp.getSpelling());
        }

        List<String> categoryWords = categoryMap.get(category);
        if (categoryWords == null) return out;
        Collections.shuffle(categoryWords);

        int i = 0;
        for (String word : categoryWords) {
            if (i >= k) return out; // retrieve at most "amount" words
            if (!completedString.contains(word)) {
                out.add(word);
                i++;
            }
        }

        return out;
    }

    boolean wordContains(String word, String phonemeCode) {
        return words.containsKey(word)
                && Objects.requireNonNull(words.get(word)).contains(phonemeCode);
    }

    /* Took function from WordBankListActivity.java, probably don't need to copy everything over
       but I just put getWordsFromCategory() in this class for the time being */
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
