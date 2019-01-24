package edu.gatech.spellastory.data;

import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.Phoneme;

public class Phonemes {

    // Maps phoneme number to list of letter combos
    // Ex. 1 -> [a], 84 -> [s,ss]
    private static Map<String, List<String>> PHONEMES = readPhonemes();

    private static Map<String, List<String>> readPhonemes() {
        try {
            Map<String, List<String>> phonemes = new HashMap<>();
            CSVReader reader = new CSVReader(new FileReader("coded_phonemes.csv"));
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
}
