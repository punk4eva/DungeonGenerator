
package gui.tools;

import animation.Animation;
import static gui.pages.DungeonScreen.ANIMATOR;
import java.awt.Graphics2D;
import utils.Utils.Unfinished;

/**
 * A simple button implementation.
 * @author Adam Whittaker
 */
@Unfinished("Rework to make more similar to other input boxes and think about getValue()")
public abstract class Button extends InputBox{
    
    
    /**
     * BUTTON_CLICK_DURATION: The duration that the button remains in the "clicked"
     * animation state.
     * selected: Whether the button is clicked.
     */
    private static final int BUTTON_CLICK_DURATION = 50;   
    protected boolean selected = false;
    
    
    public Button(int _x, int _y, int w, int h){
        super(_x, _y, w, h);
    }
    
    
    /**
     * Renders the background of this Button.
     * @param g
     */
    public void paintButtonBox(Graphics2D g){
        g.setColor(UIPanel.BUTTON_COLOR);
        g.fill3DRect(x, y, width, height, !selected);
    }
    
    /**
     * Checks if the mouse coordinates would have clicked the button, and runs
     * the click function if it was.
     * @param mx the mouse x
     * @param my the mouse y
     */
    public void testClick(int mx, int my){
        if(withinBounds(mx, my)){
            selected = true;
            registerClickAnimation();
            click(mx, my);
        }
    }
    
    
    /**
     * Adds the click animation to the animator.
     */
    private void registerClickAnimation(){
        ANIMATOR.add(new Animation(){
            
            private int duration = BUTTON_CLICK_DURATION;
            
            @Override
            public void animate(Graphics2D g, int focusX, int focusY, int frames){
                duration -= frames;
                if(duration<=0){
                    selected = false;
                    done = true;
                }
            }
            
        });
    }
    
}
