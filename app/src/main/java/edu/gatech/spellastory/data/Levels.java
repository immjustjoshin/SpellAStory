package edu.gatech.spellastory.data;

import java.util.Arrays;
import java.util.List;

public class Levels {

    private static final List<String> LV5 = Arrays.asList("14", "15", "18", "19", "20", "21", "23", "37", "38", "41", "42", "62", "63", "64", "80", "81", "82", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "97", "98", "99", "100", "108");
    private static final List<String> LV6 = Arrays.asList("5", "6", "10", "11", "12", "27", "28", "29", "30", "34", "35", "47", "48", "68", "69", "70", "71", "72", "75", "76", "77", "78", "104");
    private static final List<String> LV7 = Arrays.asList("7", "8", "9", "31", "32", "33", "49", "51", "52", "53", "54", "59", "73", "74", "82", "105");

    public static List<String> getLevel(int levelNum) {
        switch (levelNum) {
            case 5:
                return LV5;
            case 6:
                return LV6;
            case 7:
                return LV7;
            default:
                throw new IllegalArgumentException(levelNum + " is not a valid level");
        }
    }
}
