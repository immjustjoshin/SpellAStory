package edu.gatech.spellastory.domain.stories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Story {

    private List<StoryToken> tokens;

    public Story() {
        this.tokens = new ArrayList<>();
    }

    private Story(List<StoryToken> tokens) {
        this.tokens = tokens;
    }

    public Story add(StoryToken... tokens) {
        return add(Arrays.asList(tokens));
    }

    public Story add(List<StoryToken> tokens) {
        this.tokens.addAll(tokens);
        return this;
    }
}
