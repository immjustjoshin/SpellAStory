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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
