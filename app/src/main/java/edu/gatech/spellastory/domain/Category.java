package edu.gatech.spellastory.domain;

public class Category {

    private int code;
    private String name;

    public Category(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

}
