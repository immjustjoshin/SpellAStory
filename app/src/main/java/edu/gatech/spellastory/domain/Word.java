package edu.gatech.spellastory.domain;

import java.util.List;

public class Word {

    private List<Phoneme> spelling;
    private int picture;
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

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public List<Phoneme> getSpelling() {
        return spelling;
    }

    public void setSpelling(List<Phoneme> spelling) {
        this.spelling = spelling;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
