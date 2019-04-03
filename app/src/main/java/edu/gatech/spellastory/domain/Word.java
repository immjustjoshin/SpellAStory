package edu.gatech.spellastory.domain;

import java.io.Serializable;
import java.util.List;

public class Word implements Serializable {

    private String spelling;
    private List<Phoneme> phonemes;
    private boolean complete;

    public Word(String spelling, List<Phoneme> phonemes, boolean complete) {
        this.spelling = spelling;
        this.phonemes = phonemes;
        this.complete = complete;
    }

    public String getSpelling() {
        return spelling;
    }

    public List<Phoneme> getPhonemes() {
        return phonemes;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spelling='" + spelling + '\'' +
                ", phonemes=" + phonemes +
                '}';
    }
}
