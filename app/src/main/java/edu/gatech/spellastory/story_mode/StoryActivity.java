package edu.gatech.spellastory.story_mode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import edu.gatech.spellastory.R;
import edu.gatech.spellastory.data.Database;
import edu.gatech.spellastory.domain.Category;
import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;
import edu.gatech.spellastory.domain.stories.Story;
import edu.gatech.spellastory.domain.stories.StoryBlank;
import edu.gatech.spellastory.domain.stories.StoryLine;
import edu.gatech.spellastory.domain.stories.StoryToken;

import static java.util.Arrays.asList;

public class StoryActivity extends AppCompatActivity {

    public static final String EX_WORD = "story";
    public static final int WORD_CODE = 1;
    public static final String EX_NAME = "name";
    public static final int NAME_CODE = 2;
    public static final int NONE_CODE = 0;
    private static final String FRIENDS = "friends";
    private static final String SPEECH_BUBBLE_IMG = "speech_bubble";
    private static final String BLANK = " ______ ";
    private ImageView storyTemplate, speechBubble;
    private TextView placeHolder;
    private List<StoryToken> tokens;
    private int counter = 0;
    private int start;
    private int end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        // Initial Set Up
        String storyTitle = getStoryTitle();
        storyTemplate = findViewById(R.id.iv_story_template);
        speechBubble = findViewById(R.id.iv_speech_bubble);
        placeHolder = findViewById(R.id.tv_placeholder);
        setStoryTemplateFor(storyTitle);

        beginStory();
    }

    private void beginStory() {
        try {
            Database db = new Database(getAssets());
            Story story = db.getStory(convertStoryTitle(getStoryTitle()));
            tokens = story.getTokens();
            checkNextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == WORD_CODE) {
            Word word = (Word) data.getSerializableExtra(EX_WORD);
            playWordAudio(word.getSpelling()).start();

            SpannableString text = (SpannableString) placeHolder.getText();
            ClickableSpan[] spans = text.getSpans(start, end, ClickableSpan.class);
            List<ClickableSpan> spanArray = asList(spans);

            SpannableStringBuilder newText = new SpannableStringBuilder();
            newText.append(text);
            newText.replace(start, end, " " + word.getSpelling() + " ");
            start = newText.length() - word.getSpelling().length();
            end = newText.length();

            // Clicks on same blank again to choose another word
//            ClickableSpan cs = new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//                    if (tokens.get(counter-1).isBlank()) {
//                        StoryBlank blank = (StoryBlank) tokens.get(counter-1);
//                        Intent intent = new Intent(getApplicationContext(), StoryPopUp.class);
//                        intent.putExtra(StoryPopUp.CATEGORIES, categoriesToString(blank.getCategories()));
//                        if (blank.getCategories().contains(FRIENDS)) {
//                            startActivityForResult(intent, NAME_CODE);
//                        } else {
//                            startActivityForResult(intent, WORD_CODE);
//                        }
//                    }
//                }
//            };
//
//            newText.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            placeHolder.setText(newText, TextView.BufferType.SPANNABLE);
            placeHolder.setMovementMethod(LinkMovementMethod.getInstance());

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkNextToken();
                }
            }, 1000);
//            for (ClickableSpan span : spanArray) {
//                text.getSpanStart(span);
//                text.getSpanEnd(span);
//            }
        } else if (resultCode == NAME_CODE){
            String name = data.getStringExtra(EX_NAME);
            playNameAudio(name).start();

            SpannableString text = (SpannableString) placeHolder.getText();
            ClickableSpan[] spans = text.getSpans(start, end, ClickableSpan.class);
            List<ClickableSpan> spanArray = asList(spans);

            SpannableStringBuilder newText = new SpannableStringBuilder();
            newText.append(text);
            newText.replace(start, end, " " + name + " ");
            start = newText.length() - name.length();
            end = newText.length();

            // Clicks on same blank again to choose another word
//            ClickableSpan cs = new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//                    if (tokens.get(counter-1).isBlank()) {
//                        StoryBlank blank = (StoryBlank) tokens.get(counter-1);
//                        Intent intent = new Intent(getApplicationContext(), StoryPopUp.class);
//                        intent.putExtra(StoryPopUp.CATEGORIES, categoriesToString(blank.getCategories()));
//                        if (blank.getCategories().contains(FRIENDS)) {
//                            startActivityForResult(intent, NAME_CODE);
//                        } else {
//                            startActivityForResult(intent, WORD_CODE);
//                        }                    }
//                }
//            };
//
//            newText.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            placeHolder.setText(newText, TextView.BufferType.SPANNABLE);
            placeHolder.setMovementMethod(LinkMovementMethod.getInstance());

            Handler h = new Handler();
            h.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkNextToken();
                }
            }, 1000);
        } else if (resultCode == NONE_CODE) {
            // Does nothing when no word was selected
            counter--;
            checkNextToken();
//            SpannableString text = (SpannableString) placeHolder.getText();
//            ClickableSpan[] spans = text.getSpans(start, end, ClickableSpan.class);
//            List<ClickableSpan> spanArray = asList(spans);
//
//            SpannableStringBuilder newText = new SpannableStringBuilder();
//            newText.append(text);
//
//            ClickableSpan cs = new ClickableSpan() {
//                @Override
//                public void onClick(View widget) {
//                    if (tokens.get(counter).isBlank()) {
//                        StoryBlank blank = (StoryBlank) tokens.get(counter);
//                        Intent intent = new Intent(getApplicationContext(), StoryPopUp.class);
//                        intent.putExtra(StoryPopUp.CATEGORIES, categoriesToString(blank.getCategories()));
//                        if (blank.getCategories().contains(FRIENDS)) {
//                            startActivityForResult(intent, NAME_CODE);
//                        } else {
//                            startActivityForResult(intent, WORD_CODE);
//                        }
//                    }
//                }
//            };
//
//            newText.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            placeHolder.setText(newText, TextView.BufferType.SPANNABLE);
//            placeHolder.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void checkNextToken() {
        if (counter < tokens.size()) {
            StoryToken token = tokens.get(counter);
            if (!token.isBlank()) {
                StoryLine line = (StoryLine) token;
                SpannableString text;
                SpannableStringBuilder newText;
                if (counter == 0) { // Code for beginning the story
                    String temp = line.getText();

                    newText = new SpannableStringBuilder();
                    newText.append(temp);
                    newText.append(BLANK);

                    start = newText.length() - BLANK.length();
                    end = newText.length();
                } else {
                    text = (SpannableString) placeHolder.getText();
                    ClickableSpan[] spans = text.getSpans(start, end, ClickableSpan.class);
                    List<ClickableSpan> spanArray = asList(spans);

                    newText = new SpannableStringBuilder();
                    newText.append(text);
                    newText.append(line.getText());
                    newText.append(BLANK);

                    start = newText.length() - BLANK.length();
                    end = newText.length();
                }
                counter++;

                ClickableSpan cs = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        if (tokens.get(counter).isBlank()) {
                            StoryBlank blank = (StoryBlank) tokens.get(counter);
                            Intent intent = new Intent(getApplicationContext(), StoryPopUp.class);
                            intent.putExtra(StoryPopUp.CATEGORIES, categoriesToString(blank.getCategories()));
                            counter++;
                            if (blank.getCategories().contains(FRIENDS)) {
                                startActivityForResult(intent, NAME_CODE);
                            } else {
                                startActivityForResult(intent, WORD_CODE);
                            }
                        }

                    }
                    // Override this method to change the color of the underline
//            @Override
//            public void updateDrawState(final TextPaint textPaint) {
//                textPaint.setColor(getResources().getColor(R.color.));
//                textPaint.setUnderlineText(true);
//            }
                };

                newText.setSpan(cs, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                placeHolder.setText(newText, TextView.BufferType.SPANNABLE);
                placeHolder.setMovementMethod(LinkMovementMethod.getInstance());
                setAudioFor(line.getAudioFile()).start();
            }
        }
    }


    private String getStoryTitle() {
        Intent intent = getIntent();
        return intent.getStringExtra(EX_WORD);
    }

    /**
     * Sets the template image and speech bubble for
     * the spell a story mode screen
     * @param story Title of story selected
     */
    private void setStoryTemplateFor(String story) {
        Drawable template = setPictureFor(story);
        Drawable bubble = setPictureFor(SPEECH_BUBBLE_IMG);
        placeHolder.setMovementMethod(new ScrollingMovementMethod());

        if (template != null) {
            storyTemplate.setImageDrawable(template);
        }
        if (bubble != null) {
            speechBubble.setImageDrawable(bubble);
        }
    }

    private Drawable setPictureFor(String image) {
        try {
            InputStream ims = getAssets().open("pictures/story_templates/" + image + ".png");
            return Drawable.createFromStream(ims, null);
        } catch (IOException e) {
            // Could not find picture for Story Template
            e.printStackTrace();
        }
        return null;
    }

    private MediaPlayer setAudioFor(String storyAudio) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/stories/" + convertStoryTitle(getStoryTitle()) + "/" + storyAudio + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associate with the word
            e.printStackTrace();
        }
        return mp;
    }

    private MediaPlayer playNameAudio(String name) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/names/" + name + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associate with the word
            e.printStackTrace();
        }
        return mp;
    }

    private MediaPlayer playWordAudio(String word) {
        MediaPlayer mp = new MediaPlayer();
        try {
            AssetFileDescriptor afd = getAssets().openFd("audio/words/" + word + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
        } catch (IOException e) {
            // Could not find audio file associate with the word
            e.printStackTrace();
        }
        return mp;
    }


    private String convertStoryTitle(String title) {
        return title.replaceAll(" ", "_").toLowerCase();
    }

    private ArrayList<String> categoriesToString(List<Category> list) {
        ArrayList<String> result = new ArrayList<>();
        int i = 0;
        for (Category c : list) {
            result.add(c.getName());
            i++;
        }
        return result;
    }
}
