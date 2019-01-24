package edu.gatech.spellastory.data;

import java.util.Arrays;
import java.util.List;

public class Levels {

    private static final List<String> LV5 = Arrays.asList("14", "15", "18", "19", "20", "21", "23",
            "37", "38", "41", "42", "62", "63", "64", "80", "81", "82", "86", "87", "88", "89",
            "90", "91", "92", "93", "94", "95", "97", "98", "99", "100", "108");

    public static List<String> getLevel(int levelNum) {
        if (levelNum == 5) {
            return LV5;
        }
        throw new IllegalArgumentException(levelNum + " is not a valid level");
    }
}

/*
bl 14
br 15 zebra, bridge
ch 18
ck 19 lick
cl 20 clown
cr 21 cry
dr 23
fl 37 flute, fly
fr 38 fridge, fringe, afraid
gl 41 glass, glove, juggler
gr 42
nd 62
ng 63 sing, king
nk 64
pl 80 plum
pr 81
qu 82 queen, squares, quiet
sc 86
sh 87 fish, she
sk 88 skis, sky
sl 89 sleepy, slide
sm 90
sn 91
sp 92
st 93
str 94
sw 95 swan
th-1_voiced 97 moth
th-2_unvoiced 98
tr 99
tw 100
wh 108 whale
 */