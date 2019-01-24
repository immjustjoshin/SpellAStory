package edu.gatech.spellastory.domain;

public class Phoneme {

    private String code;
    private String spelling;

    public Phoneme(String code, String spelling) {
        this.code = code;
        this.spelling = spelling;
    }

    @Override
    public String toString() {
        return "Phoneme{" +
                "code='" + code + '\'' +
                ", spelling='" + spelling + '\'' +
                '}';
    }
}
