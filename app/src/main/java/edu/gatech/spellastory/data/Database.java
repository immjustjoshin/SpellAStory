package edu.gatech.spellastory.data;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class Database {

    private static final String TAG = Database.class.getSimpleName();

    private Phonemes phonemesDb;
    private Words wordsDb;

    public Database(AssetManager assets) throws IOException {
        InputStreamReader phonemesReader = new InputStreamReader(assets.open("coded_phonemes.csv"));
        this.phonemesDb = new Phonemes(phonemesReader);

        InputStreamReader wordsReader = new InputStreamReader(assets.open("coded_words.csv"));
        this.wordsDb = new Words(wordsReader);
    }

    public Database(Phonemes phonemesDb, Words wordsDb) {
        this.phonemesDb = phonemesDb;
        this.wordsDb = wordsDb;
    }

    public Word getWord(String word) {
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
                for (String phonemeSpelling : phonemeSpellings) {
                    if (word.startsWith(phonemeSpelling)) {
                        Phoneme phoneme = new Phoneme(codedPhoneme, phonemeSpelling);
                        phonemeObjs.add(phoneme);
                        word = word.substring(phonemeSpelling.length());
                        foundSpelling = true;
                    }
                }
                if (!foundSpelling) {
                    throw new IllegalArgumentException(originalWord + " does not contain " + phonemeSpellings);
                }
            }
        }
        if (word.length() > 0) {
            throw new IllegalArgumentException("Did not encode letters " + word + " for word " + originalWord);
        }
        return new Word(originalWord, phonemeObjs);
    }

    public List<PhonemeWordsPair> getWordsForLevel(int level) {
        List<PhonemeWordsPair> wordsForLevel = new ArrayList<>();
        List<String> levelPhonemeCodes = Levels.getLevel(level);
        for (String phonemeCode : levelPhonemeCodes) {
            List<Word> wordsWithPhonemeCode = convertWordStringsToWordObjs(wordsDb.getWordsForPhonemeCode(phonemeCode));
            if (!wordsWithPhonemeCode.isEmpty()) {
                wordsForLevel.add(new PhonemeWordsPair(phonemeCode, wordsWithPhonemeCode));
            }
        }
        return wordsForLevel;
    }

    private List<Word> convertWordStringsToWordObjs(List<String> wordStrings) {
        List<Word> wordObjs = new ArrayList<>();
        for (String wordString : wordStrings) {
            wordObjs.add(getWord(wordString));
        }
        return wordObjs;
    }
}
