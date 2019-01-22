package edu.gatech.spellastory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class Database {

    private String[] bl = {"bl", "black", "blind", "blood", "blow", "blade", "blaze", "blouse", "bluejay", "blanket", "tablet"};
    private String[] br = {"br", "brown", "broom", "brass", "braids", "break", "bread", "branch", "bride", "bring", "brainy", "brush", "brass", "brick", "broken", "bridge", "breakfast", "zebra"};

    public static void main(String[] args) {
        Database db = new Database();
        System.out.println(db.lv5());
    }

    private Map<Phoneme, List<Word>> lv5() {
        String[][] phonemes = {bl, br};
        return makeLevel(phonemes);
    }

    private Map<Phoneme, List<Word>> makeLevel(String[][] phonemes) {
        Map<Phoneme, List<Word>> level = new HashMap<>();

        for (String[] phoneme : phonemes) {
            String spelling = phoneme[0];

            String[] words = Arrays.copyOfRange(phoneme, 1, phoneme.length);

            List<Word> wordList = new ArrayList<>();
            for (String word : words) {
                wordList.add(new Word(wordToPhonemes(word, spelling)));
            }

            level.put(new Phoneme(spelling), wordList);
        }

        return level;
    }

    private List<Phoneme> wordToPhonemes(String word, String phoneme) {
        List<String> phonemeStrings = splitByPhoneme(word, phoneme);
        List<Phoneme> phonemes = new ArrayList<>();
        for (String phonemeString : phonemeStrings) {
            phonemes.add(new Phoneme(phonemeString));
        }
        return phonemes;
    }

    private List<String> splitByPhoneme(String word, String phoneme) {
        List<String> split = new ArrayList<>();
        int i = 0;
        while (i < word.length()) {
            if (i + phoneme.length() <= word.length()
                    && word.substring(i, i + phoneme.length()).equals(phoneme)) {
                split.add(phoneme);
                i += phoneme.length();
            } else {
                split.add(String.valueOf(word.charAt(i)));
                i += 1;
            }
        }
        return split;
    }

}
