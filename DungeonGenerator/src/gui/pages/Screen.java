
package gui.pages;

import java.awt.Graphics2D;

/**
 * A screen that can be painted to the window.
 * @author Adam Whittaker
 */
public interface Screen{
    
    /**
     * Paints the Screen on the given Graphics object.
     * @param g The graphics.
     * @param frames The number of frames that have passed since the last render
     * tick.
     */
    public void paint(Graphics2D g, int frames);
    
}
