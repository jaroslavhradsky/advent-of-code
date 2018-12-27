/*
--- Day 3: No Matter How You Slice It ---

The Elves managed to locate the chimney-squeeze prototype fabric for Santa's suit (thanks to someone who helpfully wrote its box IDs on the wall of the warehouse in the middle of the night). Unfortunately, anomalies are still affecting them - nobody can even agree on how to cut the fabric.

The whole piece of fabric they're working on is a very large square - at least 1000 inches on each side.

Each Elf has made a claim about which area of fabric would be ideal for Santa's suit. All claims have an ID and consist of a single rectangle with edges parallel to the edges of the fabric. Each claim's rectangle is defined as follows:

    The number of inches between the left edge of the fabric and the left edge of the rectangle.
    The number of inches between the top edge of the fabric and the top edge of the rectangle.
    The width of the rectangle in inches.
    The height of the rectangle in inches.

A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies a rectangle 3 inches from the left edge, 2 inches from the top edge, 5 inches wide, and 4 inches tall. Visually, it claims the square inches of fabric represented by # (and ignores the square inches of fabric represented by .) in the diagram below:

...........
...........
...#####...
...#####...
...#####...
...#####...
...........
...........
...........

The problem is that many of the claims overlap, causing two or more claims to cover part of the same areas. For example, consider the following claims:

#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2

Visually, these claim the following areas:

........
...2222.
...2222.
.11XX22.
.11XX22.
.111133.
.111133.
........

The four square inches marked with X are claimed by both 1 and 2. (Claim 3, while adjacent to the others, does not overlap either of them.)

If the Elves all proceed with their own plans, none of them will have enough fabric. How many square inches of fabric are within two or more claims?

Your puzzle answer was 110383.
--- Part Two ---

Amidst the chaos, you notice that exactly one claim doesn't overlap by even a single square inch of fabric with any other claim. If you can somehow draw attention to it, maybe the Elves will be able to make Santa's suit after all!

For example, in the claims above, only claim 3 is intact after all claims are made.

What is the ID of the only claim that doesn't overlap?

Your puzzle answer was 129.
*/

package y2018;
import support.InputLoader;
import java.util.ArrayList;

public class Day03 {

    static ArrayList<String> input = new ArrayList<>();
    static int[][] fabric = new int[1000][1000];
    static int count;

    public static void main(String[] args){
        InputLoader il = new InputLoader("data/2018/day03.txt");
        input = il.getList();

        // -------------------- Part 1 -------------------- Your puzzle answer was 110383.
        for (String line : input) {
            int x,y,w,h;
            
            int ix = line.indexOf("x");
            int is = line.lastIndexOf(" ");
            int ia = line.indexOf("@");
            int ic = line.indexOf(",");
                      
            h = Integer.parseInt(line.substring(ix+1));
            w = Integer.parseInt(line.substring(is+1,ix));
            x = Integer.parseInt(line.substring(ia+2,ic));
            y = Integer.parseInt(line.substring(ic+1,is-1));
            
            for (int i = x; i < x+w; i++) {
                for (int j = y; j < y+h; j++) fabric[i][j]++;
            }
        }

        for (int i = 0; i < fabric.length; i++) {
            for (int j = 0; j < fabric.length; j++) {
                if (fabric[i][j] >= 2) count++;
            }
        }
        System.out.println("Count of inches that overlap: " + count);

        // -------------------- Part 2 -------------------- Your puzzle answer was 129.
        for (String line : input) {
            int x,y,w,h;
            
            int ix = line.indexOf("x");
            int is = line.lastIndexOf(" ");
            int ia = line.indexOf("@");
            int ic = line.indexOf(",");
            String claim = line.substring(1,line.indexOf(" "));
                      
            h = Integer.parseInt(line.substring(ix+1));
            w = Integer.parseInt(line.substring(is+1,ix));
            x = Integer.parseInt(line.substring(ia+2,ic));
            y = Integer.parseInt(line.substring(ic+1,is-1));
            
            boolean overlap = false;
            for (int i = x; i < x+w; i++) 
                for (int j = y; j < y+h; j++)
                    if (fabric[i][j] >= 2) overlap = true;
            
            if (!overlap) System.out.println("Not overlapiing claim: " + claim);            
        }        
    }
}