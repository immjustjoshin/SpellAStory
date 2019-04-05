package edu.gatech.spellastory.data;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.Category;

public class Categories {

    // Maps category # to category name
    private Map<Integer, String> categoryMap;

    public Categories(Reader reader) throws IOException {
        categoryMap = readCategories(reader);
    }

    private Map<Integer, String> readCategories(Reader reader) throws IOException {
        Map<Integer, String> categories = new HashMap<>();
        CSVReader r = new CSVReader(reader);
        String[] nextLine;
        while ((nextLine = r.readNext()) != null) {
            int num = Integer.parseInt(nextLine[0]);
            String name = nextLine[1];
            categoryMap.put(num, name);
        }
        return categories;
    }

    public Category getCategory(int code) {
        String name = categoryMap.get(code);
        return new Category(code, name);
    }

    public List<Category> getCategories(String... codes) {
        int[] intCodes = new int[codes.length];
        for (int i = 0; i < codes.length; i++) {
            intCodes[i] = Integer.parseInt(codes[i]);
        }

        return getCategories(intCodes);
    }

    public List<Category> getCategories(int... codes) {
        List<Category> categories = new ArrayList<>(codes.length);
        for (int code : codes) {
            categories.add(getCategory(code));
        }
        return categories;
    }
}
