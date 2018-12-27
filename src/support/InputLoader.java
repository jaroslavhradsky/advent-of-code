package support;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InputLoader {
    private ArrayList<String> list;
    
    public InputLoader(String inputFile){
        FileReader fr = null;
        try {
            list = new ArrayList<>();
            File input = new File(inputFile);
            fr = new FileReader(input);
            BufferedReader br = new BufferedReader(fr);
            String line;
            try {
                while((line = br.readLine()) != null) list.add(line);
            } catch (IOException ex) {
                Logger.getLogger(InputLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputLoader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {
                Logger.getLogger(InputLoader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public ArrayList<String> getList(){
        return list;
    }
    
    
    public StringBuilder getString(){
        StringBuilder sb = new StringBuilder();
        for (String string : list) sb.append(string);
        return sb;
    }
    
    
    public ArrayList<Integer> getIntList(){
        ArrayList<Integer> output = new ArrayList<>();
        for (String item : list) output.add(Integer.parseInt(item));
        return output;
    }
}
