package edu.gatech.spellastory.domain;

import java.util.List;

public class Word {

    private List<Phoneme> spelling;
    private String picture;
    private boolean completed;

    public Word(List<Phoneme> spelling) {
        this.spelling = spelling;
    }

    @Override
    public String toString() {
        return "Word{" +
                "spelling=" + spelling +
                ", picture='" + picture + '\'' +
                ", completed=" + completed +
                '}';
    }

}
