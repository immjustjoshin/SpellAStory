package edu.gatech.spellastory.domain.stories;

public class StoryLine implements StoryToken {

    private String audioFile;
    private String text;

    public StoryLine(String audioFile, String text) {
        this.audioFile = audioFile;
        this.text = text;
    }

    public String getAudioFile() {
        return audioFile;
    }

    public String getText() {
        return text;
    }

    public static boolean isAudio(String string) {
        return string.startsWith("{") && string.endsWith("}");
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}
