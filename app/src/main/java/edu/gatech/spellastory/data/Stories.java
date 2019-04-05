package edu.gatech.spellastory.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.stories.Story;
import edu.gatech.spellastory.domain.stories.StoryBlank;
import edu.gatech.spellastory.domain.stories.StoryLine;
import edu.gatech.spellastory.domain.stories.StoryToken;

public class Stories {

    private Map<String, Story> storyMap;

    private Map<String, Story> readStories(FileReader fileReader) throws IOException {
        Map<String, Story> storyMap = new HashMap<>();
        BufferedReader br = new BufferedReader(fileReader);

        Story story = new Story();
        String line;
        while ((line = br.readLine()) != null) {
            List<StoryToken> storyTokens = parseLine(line);
            story.add(storyTokens);
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
        return null;
    }
}
