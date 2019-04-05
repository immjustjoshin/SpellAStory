package edu.gatech.spellastory.data;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.stories.Story;
import edu.gatech.spellastory.domain.stories.StoryBlank;
import edu.gatech.spellastory.domain.stories.StoryLine;
import edu.gatech.spellastory.domain.stories.StoryToken;

public class Stories {

    private Map<String, Story> storyMap;

    public Stories(AssetManager assets, String... stories) throws IOException {
        storyMap = readStories(Arrays.asList(stories), assets);
    }

    private Map<String, Story> readStories(List<String> stories, AssetManager assets) throws IOException {
        Map<String, Story> storyMap = new HashMap<>();

        for (String storyName : stories) {
            InputStreamReader reader = new InputStreamReader(assets.open("stories/" + storyName + ".txt"));
            BufferedReader br = new BufferedReader(reader);

            Story story = new Story();
            String line;
            while ((line = br.readLine()) != null) {
                List<StoryToken> storyTokens = parseLine(line);
                story.add(storyTokens);
            }

            storyMap.put(storyName, story);
        }

        return storyMap;
    }

    private List<StoryToken> parseLine(String line) {
        List<StoryToken> storyTokens = new ArrayList<>();
        String[] stringTokens = line.split(" ");

        int i = 0;
        while (i < stringTokens.length) {
            String token = stringTokens[i];
            System.out.println(token);

            if (StoryLine.isAudio(token)) {
                i++;

                StringBuilder builder = new StringBuilder();
                while (i < stringTokens.length && isText(stringTokens[i])) {
                    builder.append(stringTokens[i++]);
                }
                String text = builder.toString();

                storyTokens.add(new StoryLine(token, text));
            } else if (StoryBlank.isBlank(token)) {
                // TODO
            }
        }

        return storyTokens;
    }

    private static boolean isText(String string) {
        return !StoryLine.isAudio(string) && !StoryBlank.isBlank(string);
    }

    public Story getStory(String story) {
        return storyMap.get(story);
    }
}
