package edu.gatech.spellastory.data;


import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Words {

    // Maps word to phoneme codes
    // Ex. swan -> [95,3,61]
    private Map<String, List<String>> words;

    public Words(Reader csvReader) {
        words = readWords(csvReader);
    }

    private Map<String, List<String>> readWords(Reader csvReader) {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String word = nextLine[0];
                String[] coded_phonemes = Arrays.copyOfRange(nextLine, 1, nextLine.length);
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

    public List<String> getPhonemeCodesForWord(String word) {
        return words.get(word);
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words.keySet());
    }

    public List<String> getWordsForPhonemeCode(String phonemeCode) {
        List<String> wordsContainingPhonemeCode = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : words.entrySet()) {
            String word = entry.getKey();
            List<String> phonemeCodes = entry.getValue();
            if (phonemeCodes.contains(phonemeCode)) {
                wordsContainingPhonemeCode.add(word);
            }
        }
        return wordsContainingPhonemeCode;
    }
}
