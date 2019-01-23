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

    // Level 6 Phonemes (Vowel Pairs & Twins)
    private String[] ai1 = {"ai", "tail", "nail", "sail", "mail", "snail", "rain", "grain", "sailor", "afraid", "paint", "maize", "mermaid", "braids", "trailer", "daisy", "chain"};
	private String[] ai2 = {"ai", "air", "hair", "airplane", "chair", "dairy", "fairy"};
	private String[] au = {"au", "dinosaur", "daughter", "centaur", "cauldron", "naughty", "laundry"};
	private String[] aw = {"aw", "saw", "paw", "yawn", "draw", "hawks", "claws", "straw", "seesaw"};
	private String[] ay = {"ay", "jay", "hay", "ray", "say", "play", "gray", "spray", "crayon", "birthday"};
	private String[] ea1 = {"ea", "tea", "peas", "seal", "read", "beak", "jeans", "leaf", "beans", "steal", "sneak", "pleats", "eagle", "cleats", "beach", "beagle", "beaver", "teacher", "peanut", "ceareal", "oatmeal", "peacock"};
	private String[] ea2 = {"ea", "bear", /*this one doesn't seem right "pair", */"head", "bread", "sweat", "feather", "weather", "measure", "breakfast"};
	private String[] ee = {"ee", "bee", "jeep", "feet", "heel", "beets", "teeth", "cheek", "sleep", "sheep", "wheel", "sleeve", "queen", "sweet"};
	private String[] ei = {"ei", "veil", "eight", "weigh", "sleigh"};
	private String[] ew = {"ew", "chew", "blew", "flew", "screws", "cashews", "stewpot", "crewcut"};
	private String[] ey = {"ey", "key", "money", "donkey", "turkey", "hockey", "chimney", "trolley"};
	private String[] ie1 = {"ie", "babies", "cookies", "collie", "hoodie", "berries", "coastie", "diesel"};
	private String[] ie2 = {"ie", "tie", "pie", "quiet", "pliers", "pieman", "bowtie"};
	private String[] oa = {"oa", "goat", "boat", "coat", "soap", "toad", "goal", "foal", "float", "oatmeal", "toaster"};
	private String[] oe = {"oe", "toe", "doe", "hoe", "oboe", "potatoes"};
	private String[] oi = {"oi", "coin", "foil", "point", "voice", "noise", "poison", "toilet"};
	private String[] oo1 = {"oo", "wood", "cook", "foot", "look", "book", "football", "bigfoot"};
	private String[] oo2 = {"oo", "zoo", "moon", "tools", "boots", "moose", "goose", "hoofs", "spoon", "broom", "stool", "tooth", "raccoon", "balloon", "poodle", "school", "rooster", "bedroom"};
	private String[] ou = {"ou", "mouth", "house", "mouse", "blouse", "hound", "proud", "snout", "cloudy", "mountain"};
	private String[] ow1 = {"ow", "yellow", "tow", "mow", "bow", "show", "snow", "blow", "bowl", "elbow", "throw", "pillow", "window", "arrow"};
	private String[] ow2 = {"ow", "owl", "cow", "sow", "town", "gown", "clown", "trowel", "flower", "shower", "cowboy", "eyebrow"};
	private String[] oy = {"oy", "toy", "boy", "joy", "cowboy", "oyster"};
	private String[] ue = {"ue", "blue", "rescue", "statue", "tissue", "queue"};

	// Level 7 Phonemes (Vowel-Consonant Pairs)
	private String[] an = {"an", "man", "pan", "can", "ant", "fan", "hand", "van", /* "hand" duplicate, */"panda", "pansy", "pants", "plant", "gander", "bandage", "banjo", "piano", "sandbox", "dance", "animals", "grandpa", "grandma", "mechanic"};
	private String[] ar1 = {"ar", "car", "jar", "arm", "bars", "park", "part", "harp", "barn", "farm", "dark", "star", "chart", "scarf", "artist", "cardinal", "go-kart"};
	private String[] ar2 = {"ar", "carrot", "parrot", "canary", "marine", "parakeet", "barrier", "marionette", "housewares", "wheelbarrow"};
	private String[] er = {"er", "fern", "mother", "father", "roller", "boxer", "water", "tiger", "silver", "finger", "zipper", "dozer", "hamster", "bakery"};
	private String[] et1 = {"et", "pet", "net", "jet", "lettuce", "pretzel", "sunset", "machete"};
	private String[] et2 = {"et", "quiet", "carpet", "piglet", "hatchet", "bucket", "puppet", "basket", "pocket", "toilet", "ferret"/*, "basket", "toilet", "pocket", "quiet" all duplicates*/};
	private String[] igh = {"igh", "high", "light", "fight", "night", "fright", "thigh"};
	private String[] il1 = {"il", "wild", "child", "pilot"};
	private String[] il2 = {"il", "milk", "stilts", "lily", "silver", "pencil", "nostril", "military"};
	private String[] ill = {"ill", "hill", "pill", "mill", "drill", "silly", "pillow", "gorilla", "windmill", "caterpillar"};
	private String[] ion = {"ion", "fashion", "addition", "cushion", "construction", "percussion", "graduation"};
	private String[] ir = {"ir", "dirt", "girl", "bird", "shirt", "skirt", "giraffe", "circles", "squirrel", "birthday"};
	private String[] le = {"le", "juggle", "bubble", "puddle", "apple", "poodle", "beagle", "bugle", "table", "turtle", "ankle", "buckle", "cradle", "motorcycle", "bicycle", "tricycle"};
	private String[] or1 = {"or", "orange", "fork", "corn", "horns", "sports", "horse", "shorts", "morning", "tricorn", "tortoise", "uniform", "porcupine", "unicorn", "forehead"};
	private String[] or2 = {"or", "work", "worms", "gator", "sailor", "mirror", "doctor", "colors", "bored", "tractor", "excavator"};
	private String[] ur = {"ur", "purple", "burn", "surf", "purse", "nurse", "turtle", "burger", "nursery", "turnips", "burrito", "measure"};


    public static void main(String[] args) {
        Database db = new Database();
        System.out.println(db.lv5());
    }

    private Map<Phoneme, List<Word>> lv5() {
        String[][] phonemes = {bl, br, ch, cl, cr, dr, fl, fr, gl, gr, nd, ng, nk, pl, pr, qu, sc, sh, sk, sl, sm, sn, sp, st, str, sw, th1, th2, tr, tw, wh};
        return makeLevel(phonemes);
    }

	private Map<Phoneme, List<Word>> lv6() {
        String[][] phonemes = {ai1, ai2, au, aw, ay, ea1, ea2, ee, ei, ew, ey, ie1, ie2, oa, oe, oi, oo1, oo2, ou, ow1, ow2, oy, ue};
        return makeLevel(phonemes);
    }

	private Map<Phoneme, List<Word>> lv7() {
        String[][] phonemes = {an, ar1, ar2, er, et1, et2, igh, il1, il2, ill, ion, ir, le, or1, or2, qu, ur};
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
