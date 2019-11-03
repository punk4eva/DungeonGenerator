
package components.traps;

import components.decorations.Decoration;
import filterGeneration.ImageBuilder;
import java.awt.Color;


/**
 *
 * @author Adam Whittaker
 */
public abstract class Trap extends Decoration{
    
    
    public boolean revealed;
    public boolean triggered = false;
    protected Color color;
    
    public Trap(String n, String desc, boolean rev){
        super(n, desc);
        color = ImageBuilder.getRandomColor();
        revealed = rev;
    }
    
}
