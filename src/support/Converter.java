package support;

import java.util.ArrayList;

public class Converter {
    public static String time(long nanotime){
        if (nanotime < 1000000) return String.format("%d ns", nanotime);
        if (nanotime < 1000000000) return String.format("%d ms", Math.round(nanotime/1000000.0));
        int sec = (int)Math.round(nanotime/1000000000.0);
        if (sec < 60) return String.format("%d s", sec);
        if (sec < 3600) return String.format("%d m, %d s", sec/60, sec%60);
        return String.format("%d h, %d m, %d s", sec/3600, (sec%3600)/60, sec%60);
    }
    
    
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        long bigNumber = 1000000;
        
        for (int i = 1; i <= 7; i++) {
            System.out.print(bigNumber + ": ");
            long start = System.nanoTime();
            for (long j = 0; j < bigNumber; j++) {
            }
            bigNumber *= 10;
            long end = System.nanoTime();
            System.out.println(time((end-start)));
        }
    }
    
}
