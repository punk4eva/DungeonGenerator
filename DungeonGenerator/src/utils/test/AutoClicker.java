
package utils.test;

import static gui.core.Window.VIEWER;
import gui.tools.InputBox;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import static utils.Utils.PERFORMANCE_LOG;

/**
 * A robot that can click somewhere really rapidly.
 * @author Adam Whittaker
 */
public class AutoClicker extends Robot{
    
    
    /**
     * Creates an instance.
     * @throws AWTException
     */
    public AutoClicker() throws AWTException{
        super();
    }

    
    /**
     * Executes the given code the given number of times with the given delay
     * between executions.
     * @param executible The code.
     * @param times The number of times to run it.
     * @param milliDelay The millisecond delay between running the code.
     */
    public void execute(Runnable executible, int times, int milliDelay){
        PERFORMANCE_LOG.dualPrint("Robot starting...");
        this.setAutoDelay(milliDelay);
        for(;times>0;times--) executible.run();
        PERFORMANCE_LOG.dualPrint("Robot finished...");
    }
    
    /**
     * Rapidly clicks the given input box.
     * @param input The button.
     * @param times The number of times to click it.
     * @param milliDelay The delay between clicks in milliseconds.
     */
    public void spamButton(InputBox input, int times, int milliDelay){
        execute(() -> {
            this.mouseMove(input.getX() + input.getWidth()/2, 
                    input.getY() + input.getHeight()/2 + VIEWER.getTitleBarSize());
            this.mousePress(MouseEvent.BUTTON1_DOWN_MASK);
            this.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK);
        }, times, milliDelay);
    }
    
}
