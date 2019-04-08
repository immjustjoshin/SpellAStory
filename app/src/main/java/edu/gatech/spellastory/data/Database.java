package edu.gatech.spellastory.data;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;
import edu.gatech.spellastory.domain.stories.Story;

public class Database {

    private static final String TAG = Database.class.getSimpleName();

    private Phonemes phonemesDb;
    private Categories categoriesDb;
    private Words wordsDb;
    private Stories storiesDb;

    public Database(AssetManager assets) throws IOException {
        InputStreamReader phonemesReader = new InputStreamReader
                (assets.open("coded_phonemes.csv"));
        phonemesDb = new Phonemes(phonemesReader);

        InputStreamReader wordsReader = new InputStreamReader
                (assets.open("coded_words.csv"));
        wordsDb = new Words(wordsReader);

        InputStreamReader categoriesReader = new InputStreamReader
                (assets.open("coded_categories.csv"));
        categoriesDb = new Categories(categoriesReader);

        storiesDb = new Stories(categoriesDb, assets, "the_special_invention");
    }

    public List<PhonemeWordsPair> getWordsForLevel(int level) {
        List<PhonemeWordsPair> wordsForLevel = new ArrayList<>();
        List<String> levelPhonemeCodes = Levels.getLevel(level);
        for (String phonemeCode : levelPhonemeCodes) {
            Phoneme phoneme = new Phoneme
                    (phonemeCode, phonemesDb.getPhonemeSpellings(phonemeCode).get(0));
            List<Word> wordsWithPhonemeCode = getWordsForPhonemeCode(phonemeCode);
            if (!wordsWithPhonemeCode.isEmpty()) {
                wordsForLevel.add(new PhonemeWordsPair(phoneme, wordsWithPhonemeCode));
            }
        }
        return wordsForLevel;
    }

    private List<Word> getWordsForPhonemeCode(String phonemeCode) {
        List<Word> wordsContainingPhonemeCode = new ArrayList<>();
        for (String word : wordsDb.getAllWords()) {
            if (wordsDb.wordContains(word, phonemeCode)) {
                wordsContainingPhonemeCode.add(getWord(word));
            }
        }
        return wordsContainingPhonemeCode;
    }

    private Word getWord(String word) {
        String originalWord = word;
        List<String> codedPhonemes = wordsDb.getPhonemeCodesForWord(word);
        List<Phoneme> phonemeObjs = new ArrayList<>();
        for (String codedPhoneme : codedPhonemes) {
            if (codedPhoneme.equals("0")) {
                String silentLetter = String.valueOf(word.charAt(0));
                Phoneme phoneme = new Phoneme(codedPhoneme, silentLetter);
                phonemeObjs.add(phoneme);
                word = word.substring(1);
            } else {
                List<String> phonemeSpellings = phonemesDb.getPhonemeSpellings(codedPhoneme);
                boolean foundSpelling = false;
                if (phonemeSpellings == null || phonemeSpellings.isEmpty()) {
                    throw new IllegalArgumentException("No spellings for phoneme " + codedPhoneme);
                }
                for (String phonemeSpelling : phonemeSpellings) {
                    if (word.startsWith(phonemeSpelling)) {
                        Phoneme phoneme = new Phoneme(codedPhoneme, phonemeSpelling);
                        phonemeObjs.add(phoneme);
                        word = word.substring(phonemeSpelling.length());
                        foundSpelling = true;
                    }
                }
                if (!foundSpelling) {
                    throw new IllegalArgumentException
                            (originalWord + " does not contain " + phonemeSpellings);
                }
            }
        }
        if (word.length() > 0) {
            throw new IllegalArgumentException
                    ("Did not encode letters " + word + " for word " + originalWord);
        }
        return new Word(originalWord, phonemeObjs, false);
    }

    public List<Phoneme> getAllPhonemes() {
        return phonemesDb.getAllPhonemes();
    }

    public Story getStory(String name) {
        return storiesDb.getStory(name);
    }

    public List<Word> getSolvedWordsFromCategory(int k, String category, Context context) {
        return wordsDb.getSolvedWordsFromCategory(k, category, context);
    }
}
