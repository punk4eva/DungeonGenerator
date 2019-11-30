
package components.traps;

import components.decorations.WallDecoration;
import java.awt.Graphics2D;

/**
 *
 * @author Adam Whittaker
 */
public class WallTrap extends Trap implements WallDecoration{

    public WallTrap(String n, String desc, boolean rev){
        super(n, desc, rev);
    }

    @Override
    public void drawImage(Graphics2D g, int x, int y){
        if(!revealed) return;
        //@Unfinished
    }

}
