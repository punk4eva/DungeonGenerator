
package animation;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Animation{

    protected int x, y;
    
    public Animation(int _x, int _y){
        x = _x;
        y = _y;
    }
    
    public abstract void animate(Graphics2D g, int focusX, int focusY, int frames);
    
}
