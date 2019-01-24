package edu.gatech.spellastory.data;

import java.util.List;

import edu.gatech.spellastory.domain.Word;

public class PhonemeWordsPair {

    private String phonemeCode;
    private List<Word> words;

    public PhonemeWordsPair(String phonemeCode, List<Word> words) {
        this.phonemeCode = phonemeCode;
        this.words = words;
    }

    public String getPhonemeCode() {
        return phonemeCode;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "PhonemeWordsPair{" +
                "phonemeCode='" + phonemeCode + '\'' +
                ", words=" + words +
                '}';
    }
}
