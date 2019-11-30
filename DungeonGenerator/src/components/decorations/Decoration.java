
package components.decorations;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Decoration{
    
    
    public final String name;
    public final String description;
    public final boolean aboveWater;
    
    
    public Decoration(String na, String desc, boolean above){
        name = na;
        description = desc;
        aboveWater = above;
    }
    
    
    public abstract void drawImage(Graphics2D g, int x, int y);

}
