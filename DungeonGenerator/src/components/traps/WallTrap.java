
package components.traps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A trap on the wall.
 * @author Adam Whittaker
 */
public class WallTrap extends Trap{

    
    private static final long serialVersionUID = 72894243L;

    
    /**
     * Creates an instance by forwarding the arguments.
     * @param n
     * @param desc
     * @param rev
     */
    public WallTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
    }
    

    @Override
    public void accept(BufferedImage t){
        if(!revealed) return;
        //@Unfinished
    }

    @Override
    public void drawHiddenAspects(Graphics2D g, int x, int y){
        //@Unfinished
    }

}
