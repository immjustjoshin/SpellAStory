package edu.gatech.spellastory.data;


import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Words {

    // Maps word to phoneme codes
    // Ex. swan -> [95,3,61]
    private Map<String, List<String>> words;

    // Map each category to a corresponding list of words
    private Map<String, List<String>> categoryMap;

    Words(Reader csvReader) {
        categoryMap = new HashMap<>();
        words = readWords(csvReader);
    }

    private Map<String, List<String>> readWords(Reader csvReader) {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String word = nextLine[0];
                String category = nextLine[1];

                // Add each word to a list that a category maps to
                List<String> temp = categoryMap.get(category);
                if (temp == null) temp = new ArrayList<>();
                temp.add(word);
                categoryMap.put(category, temp);

                String[] coded_phonemes = Arrays.copyOfRange(nextLine, 2, nextLine.length);
                phonemes.put(word, Arrays.asList(coded_phonemes));
            }
            return phonemes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<String> getPhonemeCodesForWord(String word) {
        return words.get(word);
    }

    List<String> getAllWords() {
        return new ArrayList<>(words.keySet());
    }

    List<String> getWordsForCategory(String category) {
        return categoryMap.get(category);
    }

    boolean wordContains(String word, String phonemeCode) {
        return words.containsKey(word)
                && Objects.requireNonNull(words.get(word)).contains(phonemeCode);
    }

}
