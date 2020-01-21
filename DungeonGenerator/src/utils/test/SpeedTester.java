
package utils.test;

import static utils.Utils.PERFORMANCE_LOG;

/**
 *
 * @author Adam Whittaker
 */
public class SpeedTester{

    
    private long now;
    
    
    public void start(){
        now = System.currentTimeMillis();
    }
    
    public void test(String message){
        message = (now-System.currentTimeMillis()) + " millis: " + message;
        System.out.println(message);
        PERFORMANCE_LOG.println(false, message);
        now = System.currentTimeMillis();
    }
    
}
