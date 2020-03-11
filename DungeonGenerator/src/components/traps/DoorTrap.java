
package components.traps;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A trap that is activated when opening doors.
 * @author Adam Whittaker
 */
public class DoorTrap extends Trap{

    
    private static final long serialVersionUID = 7634895993623L;

    
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
    public void drawHiddenAspects(Graphics2D g, int x, int y){
        throw new UnsupportedOperationException("@Unfinished");
    }

    @Override
    public void accept(BufferedImage t){
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
