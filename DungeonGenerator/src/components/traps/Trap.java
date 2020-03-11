
package components.traps;

import components.decorations.ComplexDecoration;
import components.decorations.SneakyDecoration;
import java.awt.Color;
import textureGeneration.ImageBuilder;


/**
 * A decoration meant to damage or ensnare entities.
 * @author Adam Whittaker
 */
public abstract class Trap extends ComplexDecoration implements SneakyDecoration{

    
    private static final long serialVersionUID = 423432L;
    
    
    /**
     * revealed: Whether the trap has been detected by the player or is hidden.
     * triggered: Whether the trap has been sprung or is otherwise inactive.
     * color: The color of the trap.
     */
    public boolean revealed;
    public boolean triggered = false;
    protected Color color;
    
    
    /**
     * Creates a new instance.
     * @param n The name of the trap.
     * @param desc The description.
     * @param rev Whether the trap is revealed.
     */
    public Trap(String n, String desc, boolean rev){
        super(n, desc, false);
        color = ImageBuilder.getRandomColor();
        revealed = rev;
    }
    
}
