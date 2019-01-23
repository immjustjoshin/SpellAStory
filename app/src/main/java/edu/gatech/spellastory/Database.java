package edu.gatech.spellastory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.spellastory.domain.Phoneme;
import edu.gatech.spellastory.domain.Word;

public class Database {

    // Level 5 Phonemes (Consonant Blends)
    private String[] bl = {"bl", "black", "blind", "blood", "blow", "blade", "blaze", "blouse", "bluejay", "blanket", "tablet"};
    private String[] br = {"br", "brown", "broom", "brass", "braids", "break", "bread", "branch", "bride", "bring", "brainy", "brush", "brass", "brick", "broken", "bridge", "breakfast", "zebra"};
    private String[] ch = {"ch", "chin", "chop", "child", "bench", "catch", "chimp", "pitch"};
    private String[] cl = {"cl", "clam", "clean", "club", "claws", "cleats", "clean", "climb", "clock", "clothes", "clown", "clover", "clippers", "cloudy", "recliner", "cyclops"};
    private String[] cr = {"cr", "crane", "cry", "crab", "crop", "cradle", "cracker", "cranky", "crayons", "crutches", "crewcut"};
    private String[] dr = {"dr", "dry", "dress", "drive", "drake", "draw", "drink", "drill", "drop", "dragon", "hydrant", "laundry", "cauldron"};
    private String[] fl = {"fl", "fly", "flat", "flag", "float", "flame", "flute", "flower", "flippers", "flatbed", "flamingo", "flipflips"};
    private String[] fr = {"fr", "fro", "frog", "fright", "friend", "fridge", "fruit", "freeze", "fringe", "afraid", "frisbee"};
    private String[] gl = {"gl", "glad", "glass", "glue", "glove", "globe", "piglet", "sunglasses"};
    private String[] gr = {"gr", "green", "gray", "grass", "grain", "grapes", "groom", "angry", "hungry", "grandma", "grandpa", "grizzly", "grocery"};
    private String[] nd = {"nd", "find", "wind", "hand", "stand", "panda", "candy", "gander", "grandpa", "grandma", "sandals"};
    private String[] ng = {"ng", "king", "sing", "ring", "wing", "swing", "hiding", "walking", "taking", "duckling", "fangs", "finger", "angry", "hungry"};
    private String[] nk = {"nk", "monkey", "ankle", "skunk", "trunk", "donkey", "bunkbed", "cranky"};
    private String[] pl = {"pl", "play", "plane", "plug", "plum", "plus", "pleats", "pliers", "plumber", "plant", "snowplow", "fireplace", "playground"};
    private String[] pr = {"pr", "pretty", "prize", "prune", "proud", "pretzel", "present", "protect", "propose", "apricot"};
    private String[] qu = {"qu", "queen", "squares", "quiet", "questin", "squirrel", "racquet", "quill"};
    private String[] sc = {"sc", "school", "scale", "scarf", "scold", "telescope", "landscape", "scratch", "scream", "screws"};
    private String[] sh = {"sh", "fish", "dish", "wash", "bush", "shin", "sheep", "shell", "shorts", "shirt", "shovel"};
    private String[] sk = {"sk", "ski", "skip", "sky", "mask", "skirt", "skunk", "skate", "desk", "basket", "whiskers", "tusks"};
    private String[] sl = {"sl", "slip", "slow", "sleep", "sled", "slide", "slice", "sleeve", "slither", "sledge", "slippers"};
    private String[] sm = {"sm", "small", "smart", "smell", "smile", "smooth", "smash"};
    private String[] sn = {"sn", "snow", "snack", "snail", "snake", "snap", "sneak", "snore", "snout"};
    private String[] sp = {"sp", "spill", "spoon", "spots", "spade", "speak", "spine", "wasp", "spider", "sports", "spaniel", "sparrows"};
    private String[] st = {"st", "star", "step", "stop", "cast", "fast", "nest", "stand", "steal", "stool", "storm", "study", "stilts", "sticks", "stump", "stinger", "statue", "pistol", "toaster", "lobster", "rooster", "hamster"};
    private String[] str = {"str", "string", "straw", "stripes", "stroller", "nostril", "ostrich", "astronaut", "strawberries"};
    private String[] sw = {"sw", "swan", "swim", "swing", "sweat", "sweep", "sweet", "swallow", "sweeper", "sweatshirt"};
    private String[] th1 = {"th", "mother", "father", "brother", "zither", "feather", "weather", "clothes"};
    private String[] th2 = {"th", "path", "moth", "thumb", "teeth", "think", "throw", "mouth", "throat", "throne", "bathtub", "birthday"};
    private String[] tr = {"tr", "treat", "tree", "truck", "tricky", "troll", "tractor", "tricorn", "tricycle", "triangle", "trailer", "trowel", "trumpet", "trolley"};
    private String[] tw = {"tw", "twin", "twinkle", "twelve", "twenty", "twist", "twilight"};
    private String[] wh = {"wh", "white", "wheel", "whale", "whistle", "whisper", "whiskers"};


    public static void main(String[] args) {
        Database db = new Database();
        System.out.println(db.lv5());
    }

    private Map<Phoneme, List<Word>> lv5() {
        String[][] phonemes = {bl, br, ch, cl, cr, dr, fl, fr, gl, gr, nd, ng, nk, pl, pr, qu, sc, sh, sk, sl, sm, sn, sp, st, str, sw, th1, th2, tr, tw, wh};
        return makeLevel(phonemes);
    }

    private Map<Phoneme, List<Word>> makeLevel(String[][] phonemes) {
        Map<Phoneme, List<Word>> level = new HashMap<>();

        for (String[] phoneme : phonemes) {
            String spelling = phoneme[0];

            String[] words = Arrays.copyOfRange(phoneme, 1, phoneme.length);

            List<Word> wordList = new ArrayList<>();
            for (String word : words) {
                wordList.add(new Word(wordToPhonemes(word, spelling)));
            }

            level.put(new Phoneme(spelling), wordList);
        }

        return level;
    }

    private List<Phoneme> wordToPhonemes(String word, String phoneme) {
        List<String> phonemeStrings = splitByPhoneme(word, phoneme);
        List<Phoneme> phonemes = new ArrayList<>();
        for (String phonemeString : phonemeStrings) {
            phonemes.add(new Phoneme(phonemeString));
        }
        return phonemes;
    }

    private List<String> splitByPhoneme(String word, String phoneme) {
        List<String> split = new ArrayList<>();
        int i = 0;
        while (i < word.length()) {
            if (i + phoneme.length() <= word.length()
                    && word.substring(i, i + phoneme.length()).equals(phoneme)) {
                split.add(phoneme);
                i += phoneme.length();
            } else {
                split.add(String.valueOf(word.charAt(i)));
                i += 1;
            }
        }
        return split;
    }

}
