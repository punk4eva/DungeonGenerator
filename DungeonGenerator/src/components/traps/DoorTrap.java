
package components.traps;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class DoorTrap extends Trap{

    public DoorTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
    }

    @Override
    public void drawImage(Graphics2D g, int x, int y){
        if(!revealed) return;
        throw new UnsupportedOperationException("@Unfinished");
    }

}
