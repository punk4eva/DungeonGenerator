
package components.traps;

import components.decorations.WallDecoration;
import java.awt.Graphics2D;

/**
 * A trap on the wall.
 * @author Adam Whittaker
 */
public class WallTrap extends Trap implements WallDecoration{

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
    public void drawImage(Graphics2D g, int x, int y, boolean drawHidden){
        if(!revealed && !drawHidden) return;
        //@Unfinished
    }

}
