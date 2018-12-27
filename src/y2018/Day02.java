/*
--- Day 2: Inventory Management System ---

You stop falling through time, catch your breath, and check the screen on the device. "Destination reached. Current Year: 1518. Current Location: North Pole Utility Closet 83N10." You made it! Now, to find those anomalies.

Outside the utility closet, you hear footsteps and a voice. "...I'm not sure either. But now that so many people have chimneys, maybe he could sneak in that way?" Another voice responds, "Actually, we've been working on a new kind of suit that would let him fit through tight spaces like that. But, I heard that a few days ago, they lost the prototype fabric, the design plans, everything! Nobody on the team can even seem to remember important details of the project!"

"Wouldn't they have had enough fabric to fill several boxes in the warehouse? They'd be stored together, so the box IDs should be similar. Too bad it would take forever to search the warehouse for two similar box IDs..." They walk too far away to hear any more.

Late at night, you sneak to the warehouse - who knows what kinds of paradoxes you could cause if you were discovered - and use your fancy wrist device to quickly scan every box and produce a list of the likely candidates (your puzzle input).

To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID containing exactly two of any letter and then separately counting those with exactly three of any letter. You can multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.

For example, if you see the following box IDs:

    abcdef contains no letters that appear exactly two or three times.
    bababc contains two a and three b, so it counts for both.
    abbcde contains two b, but no letter appears exactly three times.
    abcccd contains three c, but no letter appears exactly two times.
    aabcdd contains two a and two d, but it only counts once.
    abcdee contains two e.
    ababab contains three a and three b, but it only counts once.

Of these box IDs, four of them contain a letter which appears exactly twice, and three of them contain a letter which appears exactly three times. Multiplying these together produces a checksum of 4 * 3 = 12.

What is the checksum for your list of box IDs?

Your puzzle answer was 7470.
--- Part Two ---

Confident that your list of box IDs is complete, you're ready to find the boxes full of prototype fabric.

The boxes will have IDs which differ by exactly one character at the same position in both strings. For example, given the following box IDs:

abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz

The IDs abcde and axcye are close, but they differ by two characters (the second and fourth). However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.

What letters are common between the two correct box IDs? (In the example above, this is found by removing the differing character from either ID, producing fgij.)

Your puzzle answer was kqzxdenujwcstybmgvyiofrrd.
*/

package y2018;
import support.InputLoader;
import java.util.ArrayList;
import java.util.HashMap;

// Helper class to process ID
class ID{
    private String id;
    private HashMap<Character,Integer> map = new HashMap<>();
    private boolean twins = false, triplets = false;
    private int diffIndex = -1;
    
    public ID(String id){
        this.id = id;
        for (int i = 0; i < id.length(); i++) {
            char key = id.charAt(i);
            if (map.containsKey(key)) map.put(key, map.get(key)+1);
            else map.put(key, 1);
        }
        if (map.containsValue(2)) twins = true;
        if (map.containsValue(3)) triplets = true;
    }

    public boolean hasTwins() {
        return twins;
    }

    public boolean hasTriplets() {
        return triplets;
    }
    
    public int getDiffIndex(){
        return diffIndex;
    }
    
    public boolean isClose(String compareWord){
        int diff = 0;
        int i;
        for (i = 0; i < id.length(); i++) {
            if (id.charAt(i) != compareWord.charAt(i)) {
                diffIndex = i;
                diff++;            
            }
        }
        if (diff == 1) return true;
        return false;
    }
    
    public String diffLetterRemoved(){
        StringBuilder sb = new StringBuilder(id);
        return sb.deleteCharAt(diffIndex).toString();
    }    
}

public class Day02 {
    static int twoTimes, threeTimes;
    
    public static void main(String[] args){
        InputLoader il = new InputLoader("data/2018/day02.txt");
        ArrayList<String> ids = il.getList();
        
        // -------------------- Part 1 -------------------- Your puzzle answer was 7470.
        for (String id : ids) {
            ID newID = new ID(id);
            if (newID.hasTwins()) twoTimes++;
            if (newID.hasTriplets()) threeTimes++;
        }
        System.out.println("Checksum: " + twoTimes*threeTimes);
        
        // -------------------- Part 2 -------------------- Your puzzle answer was kqzxdenujwcstybmgvyiofrrd.
        for (int i = 0; i < ids.size(); i++) 
            for (int j = i+1; j < ids.size(); j++) {
                ID newID = new ID(ids.get(i));
                if (newID.isClose(ids.get(j))) System.out.println("Common letters: " + newID.diffLetterRemoved());            
            }
    }
}

