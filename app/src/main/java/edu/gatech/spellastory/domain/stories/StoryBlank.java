package edu.gatech.spellastory.domain.stories;

import java.util.List;

public class StoryBlank implements StoryToken {

    private String identifier;
    private List<Integer> categories;

    public StoryBlank(String identifier, List<Integer> categories) {
        this.identifier = identifier;
        this.categories = categories;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Integer> getCategories() {
        return categories;
    }

    public static boolean isBlank(String string) {
        return string.startsWith("[") && string.endsWith("]");
    }

}
