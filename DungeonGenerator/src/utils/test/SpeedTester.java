
package utils.test;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import static utils.Utils.PERFORMANCE_LOG;

/**
 *
 * @author Adam Whittaker
 */
public class SpeedTester{

    
    private final static int PRECISION = 4;
    
    private long now;
    private final LinkedList<SimpleEntry<Long, String>> records = new LinkedList<>();
    
    
    public void start(){
        now = System.currentTimeMillis();
    }
    
    public void test(String message){
        long time = System.currentTimeMillis()-now;
        records.add(new SimpleEntry<>(time, message));
        message = time + " millis: " + message;
        dualPrint(message);
        now = System.currentTimeMillis();
    }
    
    public void report(){
        long total = records.stream().mapToLong(entry -> entry.getKey()).sum();
        dualPrint("     ---- Speed Report ----");
        dualPrint("Total time: " + total + " millis");
        records.forEach((e) -> { 
            String time = ("" + (100D*e.getKey())/total);
            if(time.length()<PRECISION) time += "00000000";
            time = time.substring(0,PRECISION);
            dualPrint(e.getValue() + ": " + time + "%");
        });
    }
    
    private void dualPrint(String message){
        System.out.println(message);
        PERFORMANCE_LOG.println(false, message);
    }
    
}
