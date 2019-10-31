
package components.traps;

import filterGeneration.ImageBuilder;
import java.awt.Color;
import java.awt.Graphics2D;


/**
 *
 * @author Adam Whittaker
 */
public abstract class Trap{

    public final String name;
    public final String description;
    
    public boolean revealed;
    public boolean triggered = false;
    protected Color color;
    
    public Trap(String n, String desc, boolean rev){
        name = n;
        description = desc;
        color = ImageBuilder.getRandomColor();
        revealed = rev;
    }
    
    public abstract void drawImage(Graphics2D g, int x, int y);
    
}
