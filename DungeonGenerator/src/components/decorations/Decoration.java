
package components.decorations;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public abstract class Decoration{
    
    
    public final String name;
    public final String description;
    
    
    public Decoration(String na, String desc){
        name = na;
        description = desc;
    }
    
    
    public abstract void drawImage(Graphics2D g, int x, int y);

}
