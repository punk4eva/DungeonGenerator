
package components.decorations;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public interface SneakyDecoration extends Decoration{

    
    public abstract void drawHiddenAspects(Graphics2D g, int x, int y);
    
}
