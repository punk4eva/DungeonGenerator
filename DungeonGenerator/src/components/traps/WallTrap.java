
package components.traps;

import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class WallTrap extends Trap{

    public WallTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
    }

    @Override
    public void drawImage(Graphics2D g, int x, int y){
        throw new UnsupportedOperationException("@Unfinished");
    }

}