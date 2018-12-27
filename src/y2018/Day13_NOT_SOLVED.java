package y2018;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

class Cart implements Comparable{
    static int count = 0;
    int id;
    int x, y, turn;
    char dir;
    String directions;
    
    Cart(int x, int y, char dir){
        this.id = Cart.count++;
        this.turn = 0;
        this.x = x;
        this.y = y;
        this.dir = dir;
        updateDirections();
    }
    
    final void updateDirections(){
        switch (dir){
            case '>':
                directions = "^>v";
                break;
            case '<':
                directions = "v<^";
                break;
            case '^':
                directions = "<^>";
                break;
            case 'v':
                directions = ">v<";
                break;
        }        
    }

    @Override
    public int compareTo(Object t) {
        Cart cart = (Cart) t;
        if (this.y > cart.y) return 1;
        if (this.y < cart.y) return -1;
        if (this.x > cart.x) return 1;
        if (this.x < cart.x) return -1;
        return 0;
    }   
    
    @Override
    public String toString(){
        return this.x + "," + this.y + " " + this.dir;
    }
}

public class Day13_NOT_SOLVED {
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, AWTException{
        int dimx = 150;
        int dimy = 150;
        char[][] area = new char[dimx][dimy];
        BufferedReader br = new BufferedReader(new FileReader("data/2018/day13.txt"));
        String directions = "<>v^";
        ArrayList<Cart> carts = new ArrayList<>();
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";        

        // read input
        String line;
        int row = 0;
        while ((line = br.readLine()) != null){
            for (int col = 0; col < line.length(); col++) {
                if (directions.contains(line.substring(col,col+1))) {
                    Cart newCart = new Cart(col, row, line.charAt(col));
                    carts.add(newCart);
                    if (newCart.dir == '<' || newCart.dir == '>') area[col][row] = '-';
                    else area[col][row] = '|';
                }
                else area[col][row] = line.charAt(col);
            }
            row++;
        }

        int tick = 1;
        boolean booom = false;
//        while (!booom) { // part1
        while (carts.size() > 1) {
//            // display area
//
//
//            //TimeUnit.SECONDS.sleep(1);
//            Robot pressbot = new Robot();
//            pressbot.keyPress(17); // Holds CTRL key.
//            pressbot.keyPress(76); // Holds L key.
//            pressbot.keyRelease(17); // Releases CTRL key.
//            pressbot.keyRelease(76); // Releases L key.            
//
//            char[][] areaWithCarts = new char[dimx][dimy];
//            for (int i = 0; i < dimy; i++) for (int j = 0; j < dimx; j++) areaWithCarts[j][i] = area[j][i];
//            for (Cart cart : carts) areaWithCarts[cart.x][cart.y] = cart.dir;
//            for (int i = 0; i < dimy; i++) {
//                for (int j = 0; j < dimx; j++) {
//                    if (area[j][i] == 'X') System.out.print(ANSI_RED);
//                    else if (directions.contains(Character.toString(areaWithCarts[j][i]))) System.out.print(ANSI_GREEN);
//                    System.out.print(areaWithCarts[j][i] + ANSI_RESET);
//                }
//                System.out.println();
//            }

            //tick
            Collections.sort(carts);
            for (Cart cart : carts) {
                int nextX = cart.x, nextY = cart.y;
                switch (cart.dir){
                    case '>':
                        nextX++;
                        break;
                    case '<':
                        nextX--;
                        break;
                    case 'v':
                        nextY++;
                        break;
                    case '^':
                        nextY--;
                        break;
                }

                char newDir = ' ';
                switch (area[nextX][nextY]){
                    case '/':
                        if (cart.dir == '>') newDir = '^';
                        if (cart.dir == '<') newDir = 'v';
                        if (cart.dir == '^') newDir = '>';
                        if (cart.dir == 'v') newDir = '<';
                        break;
                    case '\\':
                        if (cart.dir == '>') newDir = 'v';
                        if (cart.dir == '<') newDir = '^';
                        if (cart.dir == '^') newDir = '<';
                        if (cart.dir == 'v') newDir = '>';
                        break;
                    case '+':
                        newDir = cart.directions.charAt(cart.turn);
                        cart.turn = (cart.turn+1)%3;
                        break;
                    default:
                        newDir = cart.dir;
                }
                cart.dir = newDir;
                cart.updateDirections();
                cart.x = nextX;
                cart.y = nextY;
            }
            
            //detect collistion
            ArrayList<Cart> toRemove = new ArrayList<>();
            for (int i = 0; i < carts.size(); i++) {
                for (int j = i+1; j < carts.size(); j++) {
                    if (carts.get(i).compareTo(carts.get(j)) == 0) {
                        System.out.println("Tick " + tick);
                        System.out.println("Buuuum (" +carts.get(i).x + "," + carts.get(i).y + ")");
                        System.out.println(carts.get(i) + " crashed with " + carts.get(j));
                        toRemove.add(carts.get(i));
                        toRemove.add(carts.get(j));
                for (Cart cart : carts) System.out.println(cart);

                    }
                }
            }
            //remove crashed carts
            if (!toRemove.isEmpty()) {
                //System.out.println(toRemove.size() + " carts crashed");
                for (Cart cart : toRemove) {
                    carts.remove(cart);
                  //  System.out.println("Odebiram " + cart);
                }
                System.out.println("We have " + carts.size() + " carts");
                System.out.println();
                
            }
            
            tick++;
        }
        
            // display area
            char[][] areaWithCarts = new char[dimx][dimy];
            for (int i = 0; i < dimy; i++) for (int j = 0; j < dimx; j++) areaWithCarts[j][i] = area[j][i];
            for (Cart cart : carts) areaWithCarts[cart.x][cart.y] = cart.dir;
            for (int i = 0; i < dimy; i++) {
                for (int j = 0; j < dimx; j++) {
                    if (area[j][i] == 'X') System.out.print(ANSI_RED);
                    else if (directions.contains(Character.toString(areaWithCarts[j][i]))) System.out.print(ANSI_GREEN);
                    System.out.print(areaWithCarts[j][i] + ANSI_RESET);
                }
                System.out.println();
            }
        
        
        System.out.println("Last cart position (" +carts.get(0).x + "," + carts.get(0).y + ") " + carts.get(0).dir + carts.get(0).turn);
        System.out.println(area[carts.get(0).x][carts.get(0).y]);
        
        
        
        
        //display carts
        //for (Cart cart : carts) System.out.printf("(%d,%d) %s %s %n",cart.x, cart.y, cart.here, cart.dir);
        
    }

}


/*
https://mk-hill.github.io/TrialAndError/cart-visualizer/

Collision log

    ^ crashed into v at 124,90 on tick 422 <<<<<<<
    15 carts left
    v crashed into ^ at 57,119 on tick 457
    13 carts left
    > crashed into < at 131,74 on tick 471 <<<<<<<<<
    11 carts left
    > crashed into < at 62,36 on tick 492
    9 carts left
    ^ crashed into ^ at 54,40 on tick 1023 <<<<<<<<<
    7 carts left
    v crashed into ^ at 138,115 on tick 1324
    5 carts left
    > crashed into < at 37,81 on tick 2464
    3 carts left
    v crashed into < at 43,109 on tick 10748
    Last cart!


 moje
Tick 422
Buuuum (124,90)
124,90 < crashed with 124,90 v
We have 15 carts

Tick 471
Buuuum (131,74)
131,74 v crashed with 131,74 <
We have 13 carts

Tick 1023
Buuuum (54,40)
54,40 ^ crashed with 54,40 ^
We have 11 carts

Tick 1409
Buuuum (87,80)
87,80 ^ crashed with 87,80 ^
We have 9 carts

Tick 2771
Buuuum (56,40)
56,40 ^ crashed with 56,40 v
We have 7 carts

Tick 6129
Buuuum (95,105)
95,105 > crashed with 95,105 <
We have 5 carts

Tick 7992
Buuuum (77,27)
77,27 > crashed with 77,27 v
We have 3 carts

Tick 31072
Buuuum (110,109)
110,109 ^ crashed with 110,109 v
We have 1 carts


*/