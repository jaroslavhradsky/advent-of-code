package y2018;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Day17 {
    static final int DIMX = 1000;
    static final int DIMY = 2000;
    static char[][] ground = new char[DIMX][DIMY];
public static final String ANSI_RESET = "\u001B[0m";
public static final String ANSI_BLACK = "\u001B[30m";
public static final String ANSI_RED = "\u001B[31m";
public static final String ANSI_GREEN = "\u001B[32m";
public static final String ANSI_YELLOW = "\u001B[33m";
public static final String ANSI_BLUE = "\u001B[34m";
public static final String ANSI_PURPLE = "\u001B[35m";
public static final String ANSI_CYAN = "\u001B[36m";
public static final String ANSI_WHITE = "\u001B[37m";    
public static final int LIMIT = 25;

    public static void drawClay(String coord){
        int min = Integer.parseInt(coord.substring(coord.lastIndexOf("=")+1,coord.indexOf(".")));
        int max = Integer.parseInt(coord.substring(coord.lastIndexOf(".")+1));
        int val = Integer.parseInt(coord.substring(coord.indexOf("=")+1,coord.indexOf(",")));
        
        if (coord.charAt(0) == 'x') for (int i = min; i <= max; i++) ground[val][i] = '#';
        else for (int i = min; i <= max; i++) ground[i][val] = '#';
    }
    
    static void tecDolu(int x, int y){
        while (ground[x][y+1] == '.' && y < LIMIT) {
            ground[x][y+1] = '|';
            y++;
        }
        if (y<LIMIT){
            if (ground[x-1][y+1] == '.') tecDolu(x-1, y-1);
            if (ground[x+1][y+1] == '.') tecDolu(x+1, y-1);        
            naplnNadobu(x,y);        
        }
    }
    
    static boolean tecDoLeva(int x, int y){
        char ch = '~';
        if (ground[x+1][y] == '|') ch = '|';        
        while ((ground[x-2][y+1] == '#' || ground[x-2][y+1] == '~') && ground[x-1][y] != '#') ground[x--][y] = ch;
        if (ground[x-1][y] == '#'){
            ground[x][y] = ch;
            return false;
        }
        else {
            ground[x][y] = '|';
            ground[x-1][y] = '|';
            ground[x-2][y] = '|';
            if (ground[x-2][y+1] != '|') tecDolu(x-2,y);
            return true;            
        }
    }
    
    static boolean tecDoPrava(int x, int y){
        char ch = '~';
        if (ground[x-1][y] == '|') ch = '|';
        while ((ground[x+2][y+1] == '#' || ground[x+2][y+1] == '~') && ground[x+1][y] != '#') ground[x++][y] = ch;
        if (ground[x+1][y] == '#'){
            ground[x][y] = ch;
            return false;
        }
        else {
            ground[x][y] = '|';
            ground[x+1][y] = '|';
            ground[x+2][y] = '|';
            if (ground[x+2][y+1] != '|') tecDolu(x+2,y);
            return true;            
        }
    }
    
    static void naplnNadobu(int x, int y){
        boolean left = tecDoLeva(x,y);
        boolean right = tecDoPrava(x,y);
        if (left || right){
            
        }
        else naplnNadobu(x,y-1);
    }
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        // initialize ground matrix
        for (int i = 0; i < DIMY; i++) {
            for (int j = 0; j < DIMX; j++) ground[j][i] = '.';
        }
        int x = 450;
        int y = 0;
        ground[x][y] = '+';
        
        //load clay coordinates and put clay into ground matrix
        BufferedReader br = new BufferedReader(new FileReader("data/2018/day17.txt"));
        String line;
        while ((line = br.readLine()) != null) drawClay(line);

        
        tecDolu(x,y);
//        naplnNadobu(x,39);
        
        
        
        
        
        //display ground
        for (int i = 0; i < LIMIT+10; i++) {
            for (int j = 350; j < 650; j++) {
                char ch = ground[j][i];
                switch (ch){
                    case '.':
                        System.out.print(ANSI_BLUE);
                        break;
                    case '#':
                        System.out.print(ANSI_RED);
                        break;
                    case '|':
                    case '~':
                        System.out.print(ANSI_YELLOW);
                        break;
                }
                System.out.print(ch + ANSI_RESET);
            }
            System.out.println();
        }
        
        
    }

}
