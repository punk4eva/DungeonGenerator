
package utils.test;

import static gui.core.Window.VIEWER;
import gui.tools.InputBox;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import static utils.Utils.PERFORMANCE_LOG;

/**
 *
 * @author Adam Whittaker
 */
public class AutoClicker extends Robot{
    
    
    public AutoClicker() throws AWTException{
        super();
    }

    
    public void execute(Runnable executible, int times, int milliDelay){
        PERFORMANCE_LOG.dualPrint("Robot starting...");
        this.setAutoDelay(milliDelay);
        for(;times>0;times--) executible.run();
        PERFORMANCE_LOG.dualPrint("Robot finished...");
    }
    
    public void spamButton(InputBox input, int times, int milliDelay){
        execute(() -> {
            this.mouseMove(input.getX() + input.getWidth()/2, 
                    input.getY() + input.getHeight()/2 + VIEWER.getTitleBarSize());
            this.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            this.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
        }, times, milliDelay);
    }
    
    /*private int getToolBarSize(){
        
    }*/
    
}
