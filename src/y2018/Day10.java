/*
--- Day 10: The Stars Align ---

It's no use; your navigation system simply isn't capable of providing walking directions in the arctic circle, and certainly not in 1018.

The Elves suggest an alternative. In times like these, North Pole rescue operations will arrange points of light in the sky to guide missing Elves back to base. Unfortunately, the message is easy to miss: the points move slowly enough that it takes hours to align them, but have so much momentum that they only stay aligned for a second. If you blink at the wrong time, it might be hours before another message appears.

You can see these points of light floating in the distance, and record their position in the sky and their velocity, the relative change in position per second (your puzzle input). The coordinates are all given from your perspective; given enough time, those positions and velocities will move the points into a cohesive message!

Rather than wait, you decide to fast-forward the process and calculate what the points will eventually spell.

For example, suppose you note the following points:

position=< 9,  1> velocity=< 0,  2>
position=< 7,  0> velocity=<-1,  0>
position=< 3, -2> velocity=<-1,  1>
position=< 6, 10> velocity=<-2, -1>
position=< 2, -4> velocity=< 2,  2>
position=<-6, 10> velocity=< 2, -2>
position=< 1,  8> velocity=< 1, -1>
position=< 1,  7> velocity=< 1,  0>
position=<-3, 11> velocity=< 1, -2>
position=< 7,  6> velocity=<-1, -1>
position=<-2,  3> velocity=< 1,  0>
position=<-4,  3> velocity=< 2,  0>
position=<10, -3> velocity=<-1,  1>
position=< 5, 11> velocity=< 1, -2>
position=< 4,  7> velocity=< 0, -1>
position=< 8, -2> velocity=< 0,  1>
position=<15,  0> velocity=<-2,  0>
position=< 1,  6> velocity=< 1,  0>
position=< 8,  9> velocity=< 0, -1>
position=< 3,  3> velocity=<-1,  1>
position=< 0,  5> velocity=< 0, -1>
position=<-2,  2> velocity=< 2,  0>
position=< 5, -2> velocity=< 1,  2>
position=< 1,  4> velocity=< 2,  1>
position=<-2,  7> velocity=< 2, -2>
position=< 3,  6> velocity=<-1, -1>
position=< 5,  0> velocity=< 1,  0>
position=<-6,  0> velocity=< 2,  0>
position=< 5,  9> velocity=< 1, -2>
position=<14,  7> velocity=<-2,  0>
position=<-3,  6> velocity=< 2, -1>

Each line represents one point. Positions are given as <X, Y> pairs: X represents how far left (negative) or right (positive) the point appears, while Y represents how far up (negative) or down (positive) the point appears.

At 0 seconds, each point has the position given. Each second, each point's velocity is added to its position. So, a point with velocity <1, -2> is moving to the right, but is moving upward twice as quickly. If this point's initial position were <3, 9>, after 3 seconds, its position would become <6, 3>.

Over time, the points listed above would move like this:

Initially:
........#.............
................#.....
.........#.#..#.......
......................
#..........#.#.......#
...............#......
....#.................
..#.#....#............
.......#..............
......#...............
...#...#.#...#........
....#..#..#.........#.
.......#..............
...........#..#.......
#...........#.........
...#.......#..........

After 1 second:
......................
......................
..........#....#......
........#.....#.......
..#.........#......#..
......................
......#...............
....##.........#......
......#.#.............
.....##.##..#.........
........#.#...........
........#...#.....#...
..#...........#.......
....#.....#.#.........
......................
......................

After 2 seconds:
......................
......................
......................
..............#.......
....#..#...####..#....
......................
........#....#........
......#.#.............
.......#...#..........
.......#..#..#.#......
....#....#.#..........
.....#...#...##.#.....
........#.............
......................
......................
......................

After 3 seconds:
......................
......................
......................
......................
......#...#..###......
......#...#...#.......
......#...#...#.......
......#####...#.......
......#...#...#.......
......#...#...#.......
......#...#...#.......
......#...#..###......
......................
......................
......................
......................

After 4 seconds:
......................
......................
......................
............#.........
........##...#.#......
......#.....#..#......
.....#..##.##.#.......
.......##.#....#......
...........#....#.....
..............#.......
....#......#...#......
.....#.....##.........
...............#......
...............#......
......................
......................

After 3 seconds, the message appeared briefly: HI. Of course, your message will be much longer and will take many more seconds to appear.

What message will eventually appear in the sky?

Your puzzle answer was ZRABXXJC.
--- Part Two ---

Good thing you didn't have to wait, because that would have taken a long time - much longer than the 3 seconds in the example above.

Impressed by your sub-hour communication capabilities, the Elves are curious: exactly how many seconds would they have needed to wait for that message to appear?

Your puzzle answer was 10710.
*/
package y2018;

import java.util.ArrayList;
import support.InputLoader;


class Light{
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    int x,y, vx, vy;
    
    Light(int x, int y, int vx, int vy){
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }
    
    @Override
    public String toString(){
        return "(" + x + "," + y + ")";// + "     <" + vx + "," + vy + ">";
    }
}


public class Day10 {
        static final int DIM = 100;
        static char[][] sky = new char[2*DIM][2*DIM];

        
        
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void main(String[] args){
//        for (int i = 0; i < dim; i++) {
//            for (int j = 0; j < dim; j++) {
//                sky[j][i] = '.';
//            }
//        }

        ArrayList<Light> lights = new ArrayList<>();
        
        ArrayList<String> input = new InputLoader("data/2018/day10.txt").getList();
        for (String string : input) {
            String[] split = string.replaceAll(" ", "").replace("position=<", "").replace("velocity=<", ",").replaceAll(">", "").split(",");
            Light l = new Light(Integer.parseInt(split[0]),Integer.parseInt(split[1]),Integer.parseInt(split[2]),Integer.parseInt(split[3]));
//            l.x += DIM;
//            l.y += DIM;
            lights.add(l);
//            System.out.println(l);
//            sky[l.x/MOD][l.y/MOD] = '#';

        }
        
        for (int k = 1; k < 20000; k++) {
            for (int i = 0; i < DIM*2; i++) {
                for (int j = 0; j < DIM*2; j++) {
                    sky[j][i] = '.';
                }
            }
            

            for (Light l: lights) {
//                System.out.printf("%10s + (%d,%d) ->",toString(),vx,vy);
//                sky[l.x][l.y] = ' ';
                l.x += l.vx;
                l.y += l.vy;
                if (l.x > DIM && l.y>DIM && l.x<3*DIM && l.y<3*DIM) {
                    sky[l.x-DIM][l.y-DIM] = '#';
                    //System.out.println("je tam " + k);
                }
//                System.out.println(toString());
    }
            if (k>10690 && k<10720){ //10710
            // zobraz oblohu
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + k);
                         
            for (int i = 0; i < DIM*2; i++) {
                for (int j = 0; j < DIM*2; j++) {
                    System.out.printf("%2s",(sky[j][i]));
                }
                System.out.println();
            }
            System.out.println();
        }
              
        }
    }
        
}
