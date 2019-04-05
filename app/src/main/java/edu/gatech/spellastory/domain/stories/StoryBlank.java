package edu.gatech.spellastory.domain.stories;

import java.util.List;

import edu.gatech.spellastory.domain.Category;

public class StoryBlank implements StoryToken {

    private String identifier;
    private List<Category> categories;

    public StoryBlank(String identifier, List<Category> categories) {
        this.identifier = identifier;
        this.categories = categories;
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public static boolean isBlank(String string) {
        return string.startsWith("[") && string.endsWith("]");
    }

    @Override
    public boolean isBlank() {
        return true;
    }
}
