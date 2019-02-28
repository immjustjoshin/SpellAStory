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

    Words(Reader csvReader) {
        words = readWords(csvReader);
    }

    private Map<String, List<String>> readWords(Reader csvReader) {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String word = nextLine[0];
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

    boolean wordContains(String word, String phonemeCode) {
        return words.containsKey(word)
                && Objects.requireNonNull(words.get(word)).contains(phonemeCode);
    }

}
