package edu.gatech.spellastory.domain;

import java.io.Serializable;
import java.util.List;

public class Word implements Serializable {

    private String spelling;
    private List<Phoneme> phonemes;

    public Word(String spelling, List<Phoneme> phonemes) {
        this.spelling = spelling;
        this.phonemes = phonemes;
    }

    public String getSpelling() {
        return spelling;
    }

    public List<Phoneme> getPhonemes() {
        return phonemes;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spelling='" + spelling + '\'' +
                ", phonemes=" + phonemes +
                '}';
    }
}
