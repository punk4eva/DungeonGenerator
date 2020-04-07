
package gui.tools;

import java.awt.Graphics2D;

/**
 * An input box with an associated value, e.g: boolean or int.
 * @author Adam Whittaker
 */
public interface ValueInputBox{
    
    
    /**
     * Retrieves this box's value.
     * @return
     */
    public abstract Object getValue();
    
    /**
     * The box responds to a click on the given coordinates.
     * @param mx The mouse x.
     * @param my The mouse y.
     */
    public abstract void click(int mx, int my);
    
    /**
     * Paints the input box.
     * @param g The graphics.
     */
    public abstract void paint(Graphics2D g);
    
    /**
     * Retrieves the box's y coordinate.
     * @return
     */
    public abstract int getY();
    
}
