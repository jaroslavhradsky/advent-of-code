package y2018;

import java.util.ArrayList;

public class Day14 {
    public static void main(String[] args){
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";        

        ArrayList<Integer> recipes = new ArrayList<>();
        
        recipes.add(3);
        recipes.add(7);
        int a = 0;
        int b = 1;
        int curr;
                
        int recipeCount = 65260100; //original input is 652601
        for (int round = 1; round <= recipeCount; round++) {
            // add new recipes
            curr = recipes.get(a) + recipes.get(b);
            if (curr < 10) recipes.add(curr);
            else {
                recipes.add(curr/10);
                recipes.add(curr%10);
            }

            //compute new indexes
            a = (a + 1 + recipes.get(a))%recipes.size();
            b = (b + 1 + recipes.get(b))%recipes.size();

//            //print recipes
//            for (int i=0; i < recipes.size(); i++) {
//                if (i==a) System.out.print(ANSI_RED);
//                if (i==b) System.out.print(ANSI_GREEN);
//                System.out.print(recipes.get(i));
//                System.out.print(ANSI_RESET);
//            }
//            System.out.println();
        }
        
        String score = "";
        for (int i = recipeCount; i < recipeCount+10; i++) {
            score += recipes.get(i);
        }
        System.out.println("The score after " + recipeCount + " is " + score);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < recipes.size(); i++) sb.append(recipes.get(i));
        System.out.println(sb.toString().indexOf("652601"));
        
    }

}
