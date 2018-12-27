/*
--- Day 6: Chronal Coordinates ---

The device on your wrist beeps several times, and once again you feel like you're falling.

"Situation critical," the device announces. "Destination indeterminate. Chronal interference detected. Please specify new target coordinates."

The device then produces a list of coordinates (your puzzle input). Are they places it thinks are safe or dangerous? It recommends you check manual page 729. The Elves did not give you a manual.

If they're dangerous, maybe you can minimize the danger by finding the coordinate that gives the largest distance from the other points.

Using only the Manhattan distance, determine the area around each coordinate by counting the number of integer X,Y locations that are closest to that coordinate (and aren't tied in distance to any other coordinate).

Your goal is to find the size of the largest area that isn't infinite. For example, consider the following list of coordinates:

1, 1
1, 6
8, 3
3, 4
5, 5
8, 9

If we name these coordinates A through F, we can draw them on a grid, putting 0,0 at the top left:

..........
.A........
..........
........C.
...D......
.....E....
.B........
..........
..........
........F.

This view is partial - the actual grid extends infinitely in all directions. Using the Manhattan distance, each location's closest coordinate can be determined, shown here in lowercase:

aaaaa.cccc
aAaaa.cccc
aaaddecccc
aadddeccCc
..dDdeeccc
bb.deEeecc
bBb.eeee..
bbb.eeefff
bbb.eeffff
bbb.ffffFf

Locations shown as . are equally far from two or more coordinates, and so they don't count as being closest to any.

In this example, the areas of coordinates A, B, C, and F are infinite - while not shown here, their areas extend forever outside the visible grid. However, the areas of coordinates D and E are finite: D is closest to 9 locations, and E is closest to 17 (both including the coordinate's location itself). Therefore, in this example, the size of the largest area is 17.

What is the size of the largest area that isn't infinite?

--- Part Two ---

On the other hand, if the coordinates are safe, maybe the best you can do is try to find a region near as many coordinates as possible.

For example, suppose you want the sum of the Manhattan distance to all of the coordinates to be less than 32. For each location, add up the distances to all of the given coordinates; if the total of those distances is less than 32, that location is within the desired region. Using the same coordinates as above, the resulting region looks like this:

..........
.A........
..........
...###..C.
..#D###...
..###E#...
.B.###....
..........
..........
........F.

In particular, consider the highlighted location 4,3 located at the top middle of the region. Its calculation is as follows, where abs() is the absolute value function:

    Distance to coordinate A: abs(4-1) + abs(3-1) =  5
    Distance to coordinate B: abs(4-1) + abs(3-6) =  6
    Distance to coordinate C: abs(4-8) + abs(3-3) =  4
    Distance to coordinate D: abs(4-3) + abs(3-4) =  2
    Distance to coordinate E: abs(4-5) + abs(3-5) =  3
    Distance to coordinate F: abs(4-8) + abs(3-9) = 10
    Total distance: 5 + 6 + 4 + 2 + 3 + 10 = 30

Because the total distance to all coordinates (30) is less than 32, the location is within the region.

This region, which also includes coordinates D and E, has a total size of 16.

Your actual region will need to be much larger than this example, though, instead including all locations with a total distance of less than 10000.

What is the size of the region containing all locations which have a total distance to all given coordinates of less than 10000?


*/

package y2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import support.InputLoader;


class Area implements Comparable{
    static int count = 0;
    int id, x, y, size;

    public Area(int x, int y) {
        this.id = count++;
        this.x = x;
        this.y = y;
        this.size = 0;
    }
    
    @Override
    public String toString(){
        return String.format("%3d (%d,%d)",id,x,y);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public int compareTo(Object t) {
        Area a = (Area)t;
        if (this.size < a.size) return -1;
        if (this.size > a.size) return 1;
        return 0;
    }
    
    

}
    

public class Day06 {
    static ArrayList<Area> areas = new ArrayList<>();
    private static final int SIZE = 400;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    
    public static int distance(int x1, int y1, int x2, int y2){
        return Math.abs(y2-y1) + Math.abs(x2-x1);
    }
    
    public static int closest(int x, int y){
        int minDistance = Integer.MAX_VALUE;
        int minArea = -1;
        boolean multiMin = false;
        for (Area area : areas) {
            int currDistance = distance(x,y,area.x,area.y);
            if (currDistance < minDistance){
                minArea = area.id;
                minDistance = currDistance;
                multiMin = false;
            }
            else if (currDistance == minDistance) multiMin = true;
        }
        if (multiMin) return -1;
        return minArea;
    }
    
    public static void main(String[] args){
        ArrayList<String> input = new InputLoader("data/2018/day06.txt").getList();
        int[][] space = new int[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
            }
        }

        for (String s : input) {
            Area area = new Area(Integer.parseInt(s.split(", ")[0]), Integer.parseInt(s.split(", ")[1]));
            areas.add(area);
            space[area.x][area.y] = area.id;
        }
        
//        for (Area area: areas) {
//            System.out.println(area);
//        }
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int closest = closest(j,i);
                if (closest == -1) space[j][i] = 99;
                else space[j][i] = closest;
            }
        }
        
        //spocitej velikost
        int maxSize = 0;
        int maxArea = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int areaCode = space[j][i];
                //System.out.println(j + "," + i + ":" + areaCode);
                if (areaCode != 99){
                    int currSize = areas.get(areaCode).size++;
                    if (currSize > maxSize){
                        maxSize = currSize;
                        maxArea = areas.get(space[j][i]).id;
                    }
                }                
            }
        }
        
        TreeSet<Area> borders = new TreeSet<>();
        for (int i = 0; i < SIZE; i++) {
            if (space[0][i] != 99) borders.add(areas.get(space[0][i]));
        }
        for (int i = 0; i < SIZE; i++) {
            if (space[SIZE-1][i] != 99) borders.add(areas.get(space[SIZE-1][i]));
        }
        for (int i = 0; i < SIZE; i++) {
            if (space[i][0] != 99) borders.add(areas.get(space[i][0]));
        }
        for (int i = 0; i < SIZE; i++) {
            if (space[i][SIZE-1] != 99) borders.add(areas.get(space[i][SIZE-1]));
        }
        
        for (Area border : borders) {
            areas.remove(border);
        }

        
        Collections.sort(areas);
        for (Area area:areas) {
            System.out.println(area.id + ":" + area.size);
        }
        
        //cele to vykresli
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int code = space[j][i];
                if (code == areas.get(areas.size()-1).id) System.out.printf(ANSI_YELLOW);
                for (Area area : areas) 
                    if (j == area.x && i == area.y) System.out.print(ANSI_RED);
                System.out.printf("%2d",code);
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }
        

        
        
        
        // ---------- part 2 -------------
        ArrayList<Area> areas2 = new ArrayList<>();
        for (String s : input) {
            Area area = new Area(Integer.parseInt(s.split(", ")[0]), Integer.parseInt(s.split(", ")[1]));
            areas2.add(area);
            space[area.x][area.y] = area.id;
        }


        int[][] distances = new int[SIZE][SIZE];
        int count = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int dist = 0;
//                System.out.print("Bod (" + j + "," + i + "): ");
                for (Area area: areas2) {
                    dist += distance(j,i,area.x, area.y);
//                    System.out.print(dist + " + ");
                }
                distances[j][i] = dist;
//                System.out.println();
                if (dist < 10000)count++;
            }
        }
        System.out.println(count);
            
                //cele to vykresli
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int code = space[j][i];
                if (distances[j][i] < 10000) System.out.printf(ANSI_YELLOW);
                for (Area area : areas2) 
                    if (j == area.x && i == area.y) System.out.print(ANSI_RED);
                System.out.printf("%2d",code);
                System.out.print(ANSI_RESET);
            }
            System.out.println();
        }






//        ArrayList<Integer> numbers = new ArrayList<>();
//        for (String string : input) {
//            numbers.add(Integer.parseInt(string.split(", ")[0]));
//        }
//        Collections.sort(numbers, Collections.reverseOrder());
//        System.out.println(numbers.get(0));
        
    }

}
