package edu.gatech.spellastory.domain.stories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Story {

    private List<StoryToken> tokens;
    private List<StoryBlank> blanks;

    public Story() {
        this.tokens = new ArrayList<>();
    }

    private Story(List<StoryToken> tokens) {
        this.tokens = tokens;
    }

    public void add(StoryToken token) {
        if (token.isBlank()) {
            addBlank((StoryBlank) token);
        } else {
            addLine((StoryLine) token);
        }
    }

    private void addLine(StoryLine line) {
        tokens.add(line);
    }

    private void addBlank(StoryBlank blank) {
        tokens.add(blank);
        blanks.add(blank);
    }

    public void addAll(Collection<StoryToken> tokens) {
        for (StoryToken token : tokens) {
            add(token);
        }
    }

    public List<StoryToken> getTokens() {
        return tokens;
    }
}
