
package gui.tools;

import animation.Animation;
import static gui.pages.DungeonScreen.ANIMATOR;
import java.awt.Graphics2D;
import utils.Utils.Unfinished;

/**
 *
 * @author Adam Whittaker
 */
@Unfinished("Rework to make more similar to other input boxes and think about getValue()")
public abstract class Button extends InputBox{
    
    
    private static final int BUTTON_DURATION = 50;   
    private boolean selected = false; //for aesthetic purposes only.
    
    
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
    
    
    private void registerClickAnimation(){
        ANIMATOR.add(new Animation(){
            
            private int duration = BUTTON_DURATION;
            
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
