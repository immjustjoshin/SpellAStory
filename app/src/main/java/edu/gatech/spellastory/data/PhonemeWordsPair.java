package edu.gatech.spellastory.data;

import java.util.List;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class PhonemeWordsPair {

    private Phoneme phoneme;
    private List<Word> words;

    public PhonemeWordsPair(Phoneme phoneme, List<Word> words) {
        this.phoneme = phoneme;
        this.words = words;
    }

    public Phoneme getPhoneme() {
        return phoneme;
    }

    public List<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "PhonemeWordsPair{" +
                "phoneme='" + phoneme + '\'' +
                ", words=" + words +
                '}';
    }
}
