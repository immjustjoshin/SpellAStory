package edu.gatech.spellastory.domain;

import java.util.List;

public class Word {

    private String spelling;
    private List<Phoneme> phonemes;

    public Word(String spelling, List<Phoneme> phonemes) {
        this.spelling = spelling;
        this.phonemes = phonemes;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spelling='" + spelling + '\'' +
                ", phonemes=" + phonemes +
                '}';
    }
}
