/*
--- Day 5: Doesn't He Have Intern-Elves For This? ---

Santa needs help figuring out which strings in his text file are naughty or nice.

A nice string is one with all of the following properties:

    It contains at least three vowels (aeiou only), like aei, xazegov, or aeiouaeiouaeiou.
    It contains at least one letter that appears twice in a row, like xx, abcdde (dd), or aabbccdd (aa, bb, cc, or dd).
    It does not contain the strings ab, cd, pq, or xy, even if they are part of one of the other requirements.

For example:

    ugknbfddgicrmopn is nice because it has at least three vowels (u...i...o...), a double letter (...dd...), and none of the disallowed substrings.
    aaa is nice because it has at least three vowels and a double letter, even though the letters used by different rules overlap.
    jchzalrnumimnmhp is naughty because it has no double letter.
    haegwjzuvuyypxyu is naughty because it contains the string xy.
    dvszwmarrgswjxmb is naughty because it contains only one vowel.

How many strings are nice?

--- Part Two ---

Realizing the error of his ways, Santa has switched to a better model of determining whether a string is naughty or nice. None of the old rules apply, as they are all clearly ridiculous.

Now, a nice string is one with all of the following properties:

    It contains a pair of any two letters that appears at least twice in the string without overlapping, like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
    It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.

For example:

    qjhvhtzxzqqjkmpb is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
    xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
    uurcxstgmygtbstg is naughty because it has a pair (tg) but no repeat with a single letter between them.
    ieodomkazucvgmuy is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.

How many strings are nice under these new rules?


*/
package y2015;

import java.util.ArrayList;
import java.util.Arrays;
import support.InputLoader;

public class Day05 {
    public static void main(String[] args){
        InputLoader il = new InputLoader("data/2015/day05.txt");
        ArrayList<String> words = il.getList();
        int count = 0;

        // --------- part 1 ---------------
        String wovels = "aeiou";
        String[] naughtyWords = {"ab","cd","pq","xy"};
        
        for (String word : words) {
            int wovCount = 0;
            boolean nice = false;
            boolean naughty = false;
            for (int i = 0; i < word.length(); i++) {
                if (wovels.contains(Character.toString(word.charAt(i)))) wovCount++;
                if (i<word.length()-1 && word.charAt(i) == word.charAt(i+1)) nice = true;
                if (i<word.length()-1 && Arrays.asList(naughtyWords).contains(word.substring(i, i+2))) naughty = true;
            }
            if (wovCount>=3 && nice && !naughty)count++;            
        }        
        System.out.println("Number of nice words is " + count); // 258

        // --------- part 2 ---------------
        count = 0;
        for (String word : words) {
            boolean nice1 = false;
            boolean nice2 = false;
            for (int i = 1; i < word.length()-1; i++) if (word.charAt(i-1) == word.charAt(i+1)) nice1 = true;
            for (int i = 0; i < word.length()-1; i++) {
                String pair = word.substring(i,i+2);
                if (word.indexOf(pair, i+2) != -1) nice2 = true;
            }
            if (nice1 && nice2) count++;
        }
        System.out.println("Number of nice words is " + count); // 53
    }
}