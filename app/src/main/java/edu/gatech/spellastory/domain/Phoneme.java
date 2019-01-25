package edu.gatech.spellastory.domain;

import java.io.Serializable;

public class Phoneme implements Serializable {

    private String code;
    private String spelling;

    public Phoneme(String code, String spelling) {
        this.code = code;
        this.spelling = spelling;
    }

    public String getCode() {
        return code;
    }

    public String getSpelling() {
        return spelling;
    }

    @Override
    public String toString() {
        return "Phoneme{" +
                "code='" + code + '\'' +
                ", spelling='" + spelling + '\'' +
                '}';
    }
}
