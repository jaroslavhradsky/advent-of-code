/*
--- Day 9: Marble Mania ---

You talk to the Elves while you wait for your navigation system to initialize. To pass the time, they introduce you to their favorite marble game.

The Elves play this game by taking turns arranging the marbles in a circle according to very particular rules. The marbles are numbered starting with 0 and increasing by 1 until every marble has a number.

First, the marble numbered 0 is placed in the circle. At this point, while it contains only a single marble, it is still a circle: the marble is both clockwise from itself and counter-clockwise from itself. This marble is designated the current marble.

Then, each Elf takes a turn placing the lowest-numbered remaining marble into the circle between the marbles that are 1 and 2 marbles clockwise of the current marble. (When the circle is large enough, this means that there is one marble between the marble that was just placed and the current marble.) The marble that was just placed then becomes the current marble.

However, if the marble that is about to be placed has a number which is a multiple of 23, something entirely different happens. First, the current player keeps the marble they would have placed, adding it to their score. In addition, the marble 7 marbles counter-clockwise from the current marble is removed from the circle and also added to the current player's score. The marble located immediately clockwise of the marble that was removed becomes the new current marble.

For example, suppose there are 9 players. After the marble with value 0 is placed in the middle, each player (shown in square brackets) takes a turn. The result of each of those turns would produce circles of marbles like this, where clockwise is to the right and the resulting current marble is in parentheses:

[-] (0)
[1]  0 (1)
[2]  0 (2) 1 
[3]  0  2  1 (3)
[4]  0 (4) 2  1  3 
[5]  0  4  2 (5) 1  3 
[6]  0  4  2  5  1 (6) 3 
[7]  0  4  2  5  1  6  3 (7)
[8]  0 (8) 4  2  5  1  6  3  7 
[9]  0  8  4 (9) 2  5  1  6  3  7 
[1]  0  8  4  9  2(10) 5  1  6  3  7 
[2]  0  8  4  9  2 10  5(11) 1  6  3  7 
[3]  0  8  4  9  2 10  5 11  1(12) 6  3  7 
[4]  0  8  4  9  2 10  5 11  1 12  6(13) 3  7 
[5]  0  8  4  9  2 10  5 11  1 12  6 13  3(14) 7 
[6]  0  8  4  9  2 10  5 11  1 12  6 13  3 14  7(15)
[7]  0(16) 8  4  9  2 10  5 11  1 12  6 13  3 14  7 15 
[8]  0 16  8(17) 4  9  2 10  5 11  1 12  6 13  3 14  7 15 
[9]  0 16  8 17  4(18) 9  2 10  5 11  1 12  6 13  3 14  7 15 
[1]  0 16  8 17  4 18  9(19) 2 10  5 11  1 12  6 13  3 14  7 15 
[2]  0 16  8 17  4 18  9 19  2(20)10  5 11  1 12  6 13  3 14  7 15 
[3]  0 16  8 17  4 18  9 19  2 20 10(21) 5 11  1 12  6 13  3 14  7 15 
[4]  0 16  8 17  4 18  9 19  2 20 10 21  5(22)11  1 12  6 13  3 14  7 15 
[5]  0 16  8 17  4 18(19) 2 20 10 21  5 22 11  1 12  6 13  3 14  7 15 
[6]  0 16  8 17  4 18 19  2(24)20 10 21  5 22 11  1 12  6 13  3 14  7 15 
[7]  0 16  8 17  4 18 19  2 24 20(25)10 21  5 22 11  1 12  6 13  3 14  7 15

The goal is to be the player with the highest score after the last marble is used up. Assuming the example above ends after the marble numbered 25, the winning score is 23+9=32 (because player 5 kept marble 23 and removed marble 9, while no other player got any points in this very short example game).

Here are a few more examples:

    10 players; last marble is worth 1618 points: high score is 8317
    13 players; last marble is worth 7999 points: high score is 146373
    17 players; last marble is worth 1104 points: high score is 2764
    21 players; last marble is worth 6111 points: high score is 54718
    30 players; last marble is worth 5807 points: high score is 37305

What is the winning Elf's score?

Your puzzle answer was 429287.
--- Part Two ---

Amused by the speed of your answer, the Elves are curious:

What would the new winning Elf's score be if the number of the last marble were 100 times larger?

Your puzzle answer was 3624387659.
*/
package y2018;

class Marble{
    static int count = 0;
    int id;
    Marble next, prev;
    
    public Marble(int id){
        this.id = id;
        count++;
    }
}

public class Day09 {
    static final int NUM_MARBLES = 72059*100;  //72059
    static final int NUM_PLAYER = 410; //410
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    static int lowMarble = 1;
    static Marble current = new Marble(0);
    static Marble start = current;
    static long[] score = new long[NUM_PLAYER];
    static int currPlayer;

    public static void printAll(){
        Marble curr = start;
        System.out.print("[" + currPlayer + "] ");
        for (int i = 0; i < Marble.count; i++) {
            if (curr.id == current.id) System.out.print(ANSI_RED);
            System.out.printf("%3s ",curr.id);
            curr = curr.next;
            System.out.print(ANSI_RESET);
        }
        System.out.println();
    }
    
    public static long maxScore(){
        long maxScore = 0;
        for (int i = 0; i < score.length; i++) {
            long currScore = score[i];
            if (currScore > maxScore) maxScore = currScore;
        }
        return maxScore;
    }
    
    
    public static void main(String[] args){
        current.next = current;
        current.prev = current;
        while (lowMarble <= NUM_MARBLES){
            currPlayer = current.id%NUM_PLAYER;
            
            
            if ((lowMarble % 23) == 0){ // odebiram a pricitam score
                score[currPlayer] += lowMarble;
                Marble curr = current;
                for (int i = 0; i < 7; i++) curr = curr.prev;
                score[currPlayer] += curr.id;
                
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                current = curr.next;
                Marble.count--;
            }
            else { // pridavam
                Marble newMarble = new Marble(lowMarble);
                newMarble.next = current.next.next;
                newMarble.prev = current.next;
                current.next.next.prev = newMarble;
                current.next.next = newMarble;
                current = newMarble;
            }
            
            lowMarble++;
            //printAll();
        }
        System.out.println("Winning score: " + maxScore());
       
    }
}
