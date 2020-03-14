
package components.decorations;

import java.awt.Graphics2D;

/**
 * An entity that has hidden elements to it.
 * @author Adam Whittaker
 */
public interface SneakyDecoration{

    /**
     * Draws the hidden elements of the entity to the given graphics.
     * @param g The Graphics.
     * @param x The pixel x.
     * @param y The pixel y.
     */
    public abstract void drawHiddenAspects(Graphics2D g, int x, int y);
    
}
