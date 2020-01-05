
package animation;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Animation{

    
    protected int x, y;
    protected boolean done = false;
    
    
    public abstract void animate(Graphics2D g, int focusX, int focusY, int frames);
    
}
