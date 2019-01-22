package edu.gatech.spellastory.domain;

public class Phoneme {

    private String audio;
    private String spelling;
    private String color;

    public Phoneme(String spelling) {
        this.spelling = spelling;
    }

    @Override
    public String toString() {
        return "Phoneme{" +
                "audio='" + audio + '\'' +
                ", spelling='" + spelling + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
