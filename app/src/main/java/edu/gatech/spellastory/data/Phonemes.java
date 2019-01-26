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

import edu.gatech.spellastory.domain.Phoneme;

public class Phonemes {

    // Maps phoneme number to list of letter combos
    // Ex. 1 -> [a], 84 -> [s,ss]
    private Map<String, List<String>> phonemes;

    public Phonemes(Reader csvReader) {
        phonemes = readPhonemes(csvReader);
    }

    private Map<String, List<String>> readPhonemes(Reader csvReader) {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(csvReader);
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String code = nextLine[0];
                String[] letters = Arrays.copyOfRange(nextLine, 1, nextLine.length);
                phonemes.put(code, Arrays.asList(letters));
            }
            return phonemes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    List<String> getPhonemeSpellings(String codedPhoneme) {
        if (codedPhoneme.equals("0")) {
            return new ArrayList<>(); // 0 is a silent phoneme
        }
        return phonemes.get(codedPhoneme);
    }

    public List<Phoneme> getAllPhonemes() {
        List<Phoneme> allSpellings = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : phonemes.entrySet()) {
            for (String spelling : entry.getValue()) {
                allSpellings.add(new Phoneme(entry.getKey(), spelling));
            }
        }
        return allSpellings;
    }
}
