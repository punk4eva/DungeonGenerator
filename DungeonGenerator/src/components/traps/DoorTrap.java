
package components.traps;

import java.awt.Graphics2D;

/**
 * A trap that is activated when opening doors.
 * @author Adam Whittaker
 */
public class DoorTrap extends Trap{

    /**
     * Creates an instance by forwarding the arguments.
     * @param n
     * @param desc
     * @param rev
     */
    public DoorTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
    }
    
    
    @Override
    public void drawImage(Graphics2D g, int x, int y, boolean drawHidden){
        if(!revealed) return;
        throw new UnsupportedOperationException("@Unfinished");
    }

}
