package y2018;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeSet;

public class Day16 {
    public static HashMap<Integer, TreeSet<Integer>> opMap = new HashMap<>();
    
    
    public static int[] operation(int op, int[] reg, int a, int b, int c){
        int[] r = new int[4];
        System.arraycopy(reg, 0, r, 0, reg.length);
        switch (op){
            case 0: //addr (add register) stores into register C the result of adding register A and register B.
                r[c] = r[a] + r[b];
                //System.out.print("addr ");
                break;
            case 1: //addi (add immediate) stores into register C the result of adding register A and value B.
                r[c] = r[a] + b;
                //System.out.print("addi ");
                break;
            case 2: //mulr (multiply register) stores into register C the result of multiplying register A and register B.
                r[c] = r[a] * r[b];
                //System.out.print("mulr ");
                break;
            case 3: //muli (multiply immediate) stores into register C the result of multiplying register A and value B.
                r[c] = r[a] * b;
                //System.out.print("muli ");
                break;
            case 4: //banr (bitwise AND register) stores into register C the result of the bitwise AND of register A and register B.
                r[c] = r[a] & r[b];
                //System.out.print("banr ");
                break; 
            case 5: //bani (bitwise AND immediate) stores into register C the result of the bitwise AND of register A and value B.
                r[c] = r[a] & b;
                //System.out.print("banr ");
                break;
            case 6: //borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
                r[c] = r[a] | r[b];
                //System.out.print("borr ");
                break; 
            case 7: //bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
                r[c] = r[a] | b;
                //System.out.print("bori ");
                break;
            case 8: //setr (set register) copies the contents of register A into register C. (Input B is ignored.)
                r[c] = r[a];
                //System.out.print("setr ");
                break;
            case 9: //seti (set immediate) stores value A into register C. (Input B is ignored.)
                r[c] = a;
                //System.out.print("seti ");
                break;
            case 10: //gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
                if (a > r[b]) r[c] = 1;
                else r[c] = 0;
                //System.out.print("gtir ");
                break;
            case 11: //gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
                if (r[a] > b) r[c] = 1;
                else r[c] = 0;
                //System.out.print("gtri ");
                break;
            case 12: //gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
                if (r[a] > r[b]) r[c] = 1;
                r[c] = 0;
                //System.out.print("gttr ");
                break;
            case 13: //eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
                if (a == r[b]) r[c] = 1;
                else r[c] = 0;
                //System.out.print("eqir ");
                break;
            case 14: //eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
                if (r[a] == b) r[c] = 1;
                else r[c] = 0;
                //System.out.print("eqri ");
                break;
            case 15: //eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
                if (r[a] == r[b]) r[c] = 1;
                else r[c] = 0;
                //System.out.print("eqrr ");
                break;
            default: System.out.println("unknown operation");
        }
        return r;
    }
    
    public static int matchCount(int[] before, int[] instruction, int[] after){
        int count = 0;
        TreeSet<Integer> matching = new TreeSet<>();
        for (int i = 0; i < 16; i++) {
            int[] afterInstruction = operation(i,before,instruction[1],instruction[2],instruction[3]);
            if (Arrays.equals(after, afterInstruction)) {
                count++;
                matching.add(i);
            }
        }
        if (opMap.get(instruction[0]).isEmpty()) opMap.put(instruction[0], matching);
        else opMap.get(instruction[0]).retainAll(matching);
        return count;
    }
    
    public static int[] stringToArray(String string){
        int[] result = new int[4];
        String[] aString = string.substring(string.indexOf("[")+1, string.indexOf("]")).split(", ");
        for (int i = 0; i < result.length; i++) result[i] = Integer.parseInt(aString[i]);
        return result;
    }

    public static int[] instToArray(String string){
        int[] result = new int[4];
        String[] aString = string.split(" ");
        for (int i = 0; i < result.length; i++) result[i] = Integer.parseInt(aString[i]);
        return result;
    }

    

    public static void main(String[] args) throws FileNotFoundException, IOException{
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_GREEN = "\u001B[32m";    

        BufferedReader br = new BufferedReader(new FileReader("data/2018/day16a.txt"));
        for (int i = 0; i < 16; i++) opMap.put(i, new TreeSet<>()); //initialize opMap
               
        int count = 0;
        do {
            int[] before = stringToArray(br.readLine());
            int[] instruction = instToArray(br.readLine());
            int[] after = stringToArray(br.readLine());
            if (matchCount(before, instruction, after) >= 3) count++;
        } while (br.readLine() != null);
        System.out.println("Number of samples that behave like three or more opcodes: " + count);

        for (Integer key : opMap.keySet()) System.out.println(key + " -> " + opMap.get(key));
        
        
        HashMap<Integer,Integer> finalMap = new HashMap<>();
        for (int k = 0; k < 10; k++) {
            for (Integer i : opMap.keySet()) {
                opMap.get(i).removeAll(finalMap.values());
                if (opMap.get(i).size() == 1) finalMap.put(i,opMap.get(i).first());
            }
        }
            
        System.out.println();
        System.out.println("Final map");
        for (Integer op : finalMap.keySet()) System.out.println(op + " -> " + finalMap.get(op));
        br.close();
        
        System.out.println();
        System.out.println("Processing code");
        int[] reg = {0,0,0,0};
        br = new BufferedReader(new FileReader("data/2018/day16b.txt"));
        String line;
        int i = 1;
        while ((line = br.readLine()) != null){
            System.out.printf("%3d: %-20s => ", i++, Arrays.toString(reg));
            int[] op = instToArray(line);
            System.out.printf("%2d,%2d => ",op[0], finalMap.get(op[0]));
            reg = operation(finalMap.get(op[0]), reg, op[1], op[2], op[3]);
            System.out.printf("%20s %n",Arrays.toString(reg));
        }
        

        br.close();

//        int[] before = {2,1,0,3};
//        int[] instruction = {5,1,0,3};
//        int[] after = {2,1,0,0};
//
//        System.out.println(Arrays.toString(before) + " -> " + Arrays.toString(after));
//        for (int i = 0; i < 16; i++) {
//            int[] afterInstruction = operation(i,before,instruction[1],instruction[2],instruction[3]);
//            if (Arrays.equals(after, afterInstruction)) System.out.print(ANSI_GREEN + i + " *");
//            System.out.println(Arrays.toString(afterInstruction));
//            System.out.print(ANSI_RESET);
//        }
        
    }

}
